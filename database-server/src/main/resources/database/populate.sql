
-- database-server

create table if not exists token (
    id bigint generated by default as identity unique not null ,
    name varchar not null ,
    value varchar not null
);

-- media-service

create table if not exists media (
    id bigint generated by default as identity unique not null ,
    name varchar ,
    content_type varchar ,
    data bytea not null
);

-- identity-service

create table if not exists authority (
    id bigint generated by default as identity unique not null ,
    name varchar not null unique
);

create table if not exists identity (
    id bigint generated by default as identity unique not null ,
    username varchar not null unique ,
    password varchar not null unique ,
    email varchar not null unique ,
    registered_at timestamp not null ,
    authority_id bigint references authority(id)
        on delete set null on update cascade ,
    enabled boolean not null
);

-- department-service

create table if not exists address (
    id bigint generated by default as identity unique not null ,
    city varchar not null ,
    street varchar not null ,
    house varchar not null ,
    apartment varchar
);

create table if not exists department (
    id bigint generated by default as identity unique not null ,
    address_id bigint unique references address(id) on delete set null on update cascade ,
    opens_at time not null ,
    closes_at time not null ,
    opened boolean not null
);

-- employee-service

create table if not exists employee (
    id bigint generated by default as identity unique not null ,
    firstname varchar not null ,
    lastname varchar not null ,
    patronymic varchar not null ,
    department_id bigint references department(id)
        on delete set null on update cascade ,
    identity_id bigint unique references identity(id)
        on delete cascade on update cascade ,
    media_id bigint unique references media(id)
        on delete set null on update cascade
);

-- food-service

create table if not exists category (
    id bigint generated by default as identity unique not null ,
    name varchar not null unique
);

create table if not exists ingredient (
    id bigint generated by default as identity unique not null ,
    name varchar not null unique ,
    price bigint not null check ( price >= 0 )
);

create table if not exists size (
    id bigint generated by default as identity unique not null ,
    size bigint not null unique
);

create table if not exists capacity (
    id bigint generated by default as identity unique not null ,
    liters float not null
);

create table if not exists food (
    id bigint generated by default as identity unique not null ,
    category_id bigint references category(id) on delete cascade on update cascade ,
    name varchar ,
    description text ,
    price bigint check ( price >= 0 ) ,
    media_id bigint unique references media(id) on delete set null on update cascade
);

create table if not exists combo (
    id bigint generated by default as identity unique not null ,
    category_id bigint references category(id) on delete cascade on update cascade ,
    name varchar ,
    description text ,
    price bigint check ( price >= 0 ) ,
    media_id bigint unique references media(id) on delete set null on update cascade
);

create table if not exists food_size (
    food_id bigserial references food(id)
        on delete cascade on update cascade ,
    size_id bigserial references size(id)
        on delete cascade on update cascade ,
    price bigint check ( price >= 0 ) not null ,
    primary key (food_id, size_id)
);

create table if not exists food_ingredient (
    food_id bigint references food(id)
        on delete cascade on update cascade ,
    ingredient_id bigint references ingredient(id)
        on delete cascade on update cascade ,
    primary key (food_id, ingredient_id)
);

create table if not exists food_capacity (
    food_id bigint references food(id)
        on delete cascade on update cascade ,
    capacity_id bigint references capacity(id)
        on delete cascade on update cascade ,
    price bigint check ( price >= 0 ) not null ,
    primary key (food_id, capacity_id)
);

create table if not exists combo_food (
    combo_id bigint references combo(id)
        on delete cascade on update cascade ,
    food_id bigint references food(id)
        on delete cascade on update cascade ,
    amount bigint check ( amount > 0 ) not null ,
    primary key (combo_id, food_id)
);

-- commit-service

create table if not exists commit (
    id bigint generated by default as identity unique not null ,
    identity_id bigint references identity(id) on delete set null on update cascade ,
    token_id bigint references token(id) on delete set null on update cascade ,
    price bigint ,
    closed boolean
);

create table if not exists choose (
    id bigint generated by default as identity unique not null ,
    category_id bigint ,
    name varchar ,
    description text ,
    capacity_id bigint ,
    size_id bigint ,
    amount bigint check ( amount >= 0 ) not null ,
    price bigint check ( price >= 0 ) ,
    is_food boolean ,
    media_id bigint references media(id) on delete set null on update cascade ,
    commit_id bigint references commit(id) on delete cascade on update cascade
);

create table if not exists choose_ingredient (
    choose_id bigserial references choose(id) on delete set null on update cascade ,
    ingredient_id bigserial references ingredient(id) on delete set null on update cascade ,
    primary key (choose_id, ingredient_id)
);

-- POPULATIONS

-- identity-service

insert into authority(name) values ('ROLE_ADMIN');
insert into authority(name) values ('ROLE_EMPLOYEE');
insert into authority(name) values ('ROLE_USER');

insert into identity(username, password, email, registered_at, authority_id, enabled)
values ('admin','$2a$10$GX3SFb0a.lL1svNe94XZOuUbWDRSCz3KY3E0cF2kTpvKKEd7PhqKm',
        'admin@gmail.com','2024-06-15 16:45',1, true);
insert into identity(username, password, email, registered_at, authority_id, enabled)
values ('employee','$2a$10$BZaNdYigSg5gx00qwHQmZO4Dy9rPe.fW592cDvRy3RA15XEanNOHW',
        'employee@gmail.com','2024-08-20 18:25',2, true);
insert into identity(username, password, email, registered_at, authority_id, enabled)
values ('user','$2a$10$R.ROJ0PWyQy79YbViulae.CqQonUHRbAGUWw4NiGh03S1g9sgPpbO',
        'user@gmail.com','2025-01-18 19:15',3, true);

-- department-service

insert into address(city, street, house, apartment)
values ('Новосибирск','Академическая','23','3');
insert into address(city, street, house, apartment)
values ('Новосибирск','Фрунзе','56а','12');
insert into address(city, street, house, apartment)
values ('Новосибирск','Военная','15/2','2');

insert into department(address_id, opens_at, closes_at, opened)
values (1,'08:00','21:00',false);
insert into department(address_id, opens_at, closes_at, opened)
values (2,'09:00','22:00',false);
insert into department(address_id, opens_at, closes_at, opened)
values (3,'08:00','20:00',false);

-- employee-service

insert into employee(firstname, lastname, patronymic, department_id, identity_id)
values ('Геннадий','Крылов','Аркадьевич',1,2);

-- food-service

insert into category(name) values ('Пицца');
insert into category(name) values ('Закуски');
insert into category(name) values ('Напитки');
insert into category(name) values ('Десерты');
insert into category(name) values ('Соусы');
insert into category(name) values ('Горячее');
insert into category(name) values ('Комбо');

insert into ingredient(name, price) values ('Сыр моцарелла', 70);
insert into ingredient(name, price) values ('Сыр с голубой плесенью', 80);
insert into ingredient(name, price) values ('Сыр чеддер', 100);
insert into ingredient(name, price) values ('Бекон', 150);
insert into ingredient(name, price) values ('Говядина', 120);
insert into ingredient(name, price) values ('Томаты', 50);
insert into ingredient(name, price) values ('Маринованные огурцы', 60);
insert into ingredient(name, price) values ('Лук', 40);
insert into ingredient(name, price) values ('Перец халапеньо', 90);
insert into ingredient(name, price) values ('Шампиньоны', 110);
insert into ingredient(name, price) values ('Сладкий перец', 110);
insert into ingredient(name, price) values ('Креветки', 180);
insert into ingredient(name, price) values ('Мягкий молодой сыр', 120);
insert into ingredient(name, price) values ('Чеснок', 50);
insert into ingredient(name, price) values ('Пепперони', 90);
insert into ingredient(name, price) values ('Оливки', 100);

insert into size(size) values (25);
insert into size(size) values (30);
insert into size(size) values (35);
insert into size(size) values (40);

insert into capacity(liters) values (0.33);
insert into capacity(liters) values (0.5);
insert into capacity(liters) values (0.75);
insert into capacity(liters) values (1);
insert into capacity(liters) values (1.5);
insert into capacity(liters) values (2.0);

insert into food(category_id, name, description, price)
values (1, 'Пепперони',
        'Американская классика с пикантной пепперони, Моцареллой и фирменным томатным соусом', 599);

insert into food(category_id, name, description, price)
values (1, 'Супер Папа',
        'Хит "Папа Джонс" с пикантной пепперони, ветчиной, ароматной свининой, фирменным томатным соусом, ' ||
        'Моцареллой, шампиньонами, луком, маслинами и сладким перцем. С пикантной остринкой', 629);

insert into food(category_id, name, description, price)
values (1, 'Ветчина и грибы',
        'Нежная пицца с соусом "Чесночный Рэнч", Моцареллой, шампиньонами, ароматной ветчиной и чесноком', 599);

insert into food(category_id, name, description, price)
values (1, 'Двойная Пепперони',
        'Томатный соус, сыр моцарелла, двойная порция пепперони', 629);

insert into food(category_id, name, description, price)
values (1, 'Томатная с креветками',
        'Томатная с креветками: яркий вкус креветок, фирменного томатного соуса, ' ||
        'сыра моцарелла, свежих томатов и мягкого молодого сыра', 729);

insert into food(category_id, name, description, price)
values (1, 'Чеддер Мексикан',
        'Яркий сыр Чеддер, Моцарелла, сочная говядина, сладкий перец и грибы, хрустящий лук, ' ||
        'томаты и острый перчик с соусом 1000 островов', 629);

insert into food(category_id, name, description, price)
values (2, 'Рогалики с сыром',
        'Рогалики из нежного теста с чесночным соусом, сыром моцарелла. Сервируются с соусом на выбор', 329);

insert into food(category_id, name, description, price)
values (2, 'Сырные палочки',
        'Любимая закуска с Моцареллой! Сервируется соусами: "Особый чесночный" и Томатный', 549);

insert into food(category_id, name, description, price)
values (2, 'Сырные палочки с ветчиной и грибами',
        'Любимая закуска с Моцареллой, ароматной ветчиной, шампиньонами. Сервируется соусами: "Особый чесночный" и Томатный', 579);

insert into food(category_id, name, description, price)
values (2, 'Пападиас Ветчина и грибы',
        'Хрустящий сэндвич с ветчиной, шампиньонами, сыром Моцарелла и соусом Рэнч', 399);

insert into food(category_id, name, description, price)
values (3, 'Evervess Cola без сахара',null, 179);

insert into food(category_id, name, description, price)
values (3, 'Evervess Cola с сахаром',null, 179);

insert into food(category_id, name, description, price)
values (3, 'Сок "J7" Апельсиновый',null, 309);

insert into food(category_id, name, description, price)
values (3, 'Сок "J7" Вишневый',null, 309);

insert into food(category_id, name, description, price)
values (3, 'Сок "J7" Яблочный',null, 309);

insert into food(category_id, name, description, price)
values (4, 'Донат Клубничный','Ярко-розовый пончик без начинки, покрытый ароматной клубничной глазурью', 139);

insert into food(category_id, name, description, price)
values (4, 'Донат Шоколадный','Донат со вкусом шоколада Свежий ароматный донат , ' ||
                              'покрытый шоколадной глазурью. Классический вкус шоколада подарит вам ощущение уюта и комфорта. ', 139);

insert into food(category_id, name, description, price)
values (5, 'Сырный',null, 59);
insert into food(category_id, name, description, price)
values (5, 'Томатный',null, 59);
insert into food(category_id, name, description, price)
values (5, 'Барбекю',null, 59);

insert into food(category_id, name, description, price)
values (6, 'Картофель с ветчиной и грибами',
        'Запеченные дольки картофеля с ветчиной, шампиньонами, сыром Моцарелла и соусом Чесночный Рэнч', 329);

insert into food(category_id, name, description, price)
values (6, 'Картофель по-баварски',
        'Запеченные дольки картофеля с альпийскими колбасками, маринованными огурчиками, сыром, луком и соусом барбекю', 329);

insert into food_size(food_id, size_id, price) values (1,1,0);
insert into food_size(food_id, size_id, price) values (1,2,250);
insert into food_size(food_id, size_id, price) values (1,3,500);
insert into food_size(food_id, size_id, price) values (1,4,800);

insert into food_size(food_id, size_id, price) values (2,1,0);
insert into food_size(food_id, size_id, price) values (2,2,250);
insert into food_size(food_id, size_id, price) values (2,3,500);
insert into food_size(food_id, size_id, price) values (2,4,800);

insert into food_size(food_id, size_id, price) values (3,1,0);
insert into food_size(food_id, size_id, price) values (3,2,250);
insert into food_size(food_id, size_id, price) values (3,3,500);
insert into food_size(food_id, size_id, price) values (3,4,800);

insert into food_size(food_id, size_id, price) values (4,1,0);
insert into food_size(food_id, size_id, price) values (4,2,250);
insert into food_size(food_id, size_id, price) values (4,3,500);
insert into food_size(food_id, size_id, price) values (4,4,800);

insert into food_size(food_id, size_id, price) values (5,1,0);
insert into food_size(food_id, size_id, price) values (5,2,250);
insert into food_size(food_id, size_id, price) values (5,3,500);
insert into food_size(food_id, size_id, price) values (5,4,800);

insert into food_size(food_id, size_id, price) values (6,1,0);
insert into food_size(food_id, size_id, price) values (6,2,250);
insert into food_size(food_id, size_id, price) values (6,3,500);
insert into food_size(food_id, size_id, price) values (6,4,800);

insert into food_ingredient(food_id, ingredient_id) values (1, 1);
insert into food_ingredient(food_id, ingredient_id) values (1, 3);
insert into food_ingredient(food_id, ingredient_id) values (1, 5);

insert into food_ingredient(food_id, ingredient_id) values (2, 10);
insert into food_ingredient(food_id, ingredient_id) values (2, 15);

insert into food_ingredient(food_id, ingredient_id) values (3, 9);
insert into food_ingredient(food_id, ingredient_id) values (3, 6);
insert into food_ingredient(food_id, ingredient_id) values (3, 5);
insert into food_ingredient(food_id, ingredient_id) values (3, 3);
insert into food_ingredient(food_id, ingredient_id) values (3, 11);

insert into food_ingredient(food_id, ingredient_id) values (4, 1);
insert into food_ingredient(food_id, ingredient_id) values (4, 2);
insert into food_ingredient(food_id, ingredient_id) values (4, 4);
insert into food_ingredient(food_id, ingredient_id) values (4, 7);

insert into food_ingredient(food_id, ingredient_id) values (5, 6);
insert into food_ingredient(food_id, ingredient_id) values (5, 16);
insert into food_ingredient(food_id, ingredient_id) values (5, 14);
insert into food_ingredient(food_id, ingredient_id) values (5, 13);

insert into food_ingredient(food_id, ingredient_id) values (6, 12);
insert into food_ingredient(food_id, ingredient_id) values (6, 15);
insert into food_ingredient(food_id, ingredient_id) values (6, 3);
insert into food_ingredient(food_id, ingredient_id) values (6, 9);

insert into food_capacity(food_id, capacity_id, price) values (11, 2, 50);
insert into food_capacity(food_id, capacity_id, price) values (11, 4, 100);
insert into food_capacity(food_id, capacity_id, price) values (12, 2, 50);
insert into food_capacity(food_id, capacity_id, price) values (12, 4, 100);
insert into food_capacity(food_id, capacity_id, price) values (13, 4, 100);
insert into food_capacity(food_id, capacity_id, price) values (14, 4, 100);
insert into food_capacity(food_id, capacity_id, price) values (15, 4, 100);

insert into combo(category_id, name, description, price)
values (7,'Сет мужественный', 'Супер папа(23 см), Сырные палочки с ветчиной и грибами, 2х Evervess Cola с сахаром',1666);
insert into combo_food(combo_id, food_id, amount) values (1, 2, 1);
insert into combo_food(combo_id, food_id, amount) values (1, 9, 1);
insert into combo_food(combo_id, food_id, amount) values (1, 10, 2);