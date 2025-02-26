package org.burgas.commitservice.mapper;

import jakarta.servlet.http.Cookie;
import org.burgas.commitservice.dto.ChooseRequest;
import org.burgas.commitservice.dto.ChooseResponse;
import org.burgas.commitservice.entity.Choose;
import org.burgas.commitservice.entity.ChooseIngredient;
import org.burgas.commitservice.entity.Commit;
import org.burgas.commitservice.handler.RestClientHandlerCommitService;
import org.burgas.commitservice.repository.*;
import org.burgas.databaseserver.entity.Token;
import org.burgas.foodservice.dto.ComboResponse;
import org.burgas.foodservice.dto.FoodResponse;
import org.burgas.foodservice.entity.*;
import org.burgas.identityservice.dto.IdentityPrincipal;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ChooseMapper {

    private final ChooseRepository chooseRepository;
    private final RestClientHandlerCommitService restClientHandlerCommitService;
    private final FoodCapacityRepositoryCommitRepository foodCapacityRepositoryCommitRepository;
    private final FoodSizeRepositoryCommitRepository foodSizeRepositoryCommitRepository;
    private final ChooseIngredientRepository chooseIngredientRepository;
    private final IngredientRepositoryCommitRepository ingredientRepositoryCommitRepository;
    private final SizeRepositoryCommitRepository sizeRepositoryCommitRepository;
    private final CapacityRepositoryCommitRepository capacityRepositoryCommitRepository;
    private final CommitRepository commitRepository;
    private final TokenRepositoryCommitRepository tokenRepositoryCommitRepository;
    private final CategoryRepositoryCommitRepository categoryRepositoryCommitRepository;

    public ChooseMapper(
            ChooseRepository chooseRepository,
            RestClientHandlerCommitService restClientHandlerCommitService,
            FoodCapacityRepositoryCommitRepository foodCapacityRepositoryCommitRepository,
            FoodSizeRepositoryCommitRepository foodSizeRepositoryCommitRepository,
            ChooseIngredientRepository chooseIngredientRepository,
            IngredientRepositoryCommitRepository ingredientRepositoryCommitRepository,
            SizeRepositoryCommitRepository sizeRepositoryCommitRepository,
            CapacityRepositoryCommitRepository capacityRepositoryCommitRepository, CommitRepository commitRepository,
            TokenRepositoryCommitRepository tokenRepositoryCommitRepository,
            CategoryRepositoryCommitRepository categoryRepositoryCommitRepository
    ) {
        this.chooseRepository = chooseRepository;
        this.restClientHandlerCommitService = restClientHandlerCommitService;
        this.foodCapacityRepositoryCommitRepository = foodCapacityRepositoryCommitRepository;
        this.foodSizeRepositoryCommitRepository = foodSizeRepositoryCommitRepository;
        this.chooseIngredientRepository = chooseIngredientRepository;
        this.ingredientRepositoryCommitRepository = ingredientRepositoryCommitRepository;
        this.sizeRepositoryCommitRepository = sizeRepositoryCommitRepository;
        this.capacityRepositoryCommitRepository = capacityRepositoryCommitRepository;
        this.commitRepository = commitRepository;
        this.tokenRepositoryCommitRepository = tokenRepositoryCommitRepository;
        this.categoryRepositoryCommitRepository = categoryRepositoryCommitRepository;
    }

    private <T> T getData(T first, @SuppressWarnings("SameParameterValue") T second) {
        return first == null ? second : first;
    }

    public Choose toChoose(ChooseRequest chooseRequest, String authentication, Cookie cookie) {
        Long id;
        Long chooseId = getData(chooseRequest.getId(), 0L);

        if (chooseRequest.getFoodId() != null && chooseRequest.getComboId() != null) {
            throw new RuntimeException("You can choose food or combo not both");
        }

        if (chooseRequest.getFoodId() == null && chooseRequest.getComboId() == null) {
            throw new RuntimeException("You need to choose food or combo");
        }

        if (chooseRequest.getFoodId() != null && chooseRequest.getFood()) {
            chooseRequest.setComboId(null);
            id = getData(chooseRequest.getFoodId(), 0L);
            FoodResponse foodResponse = restClientHandlerCommitService.getFoodById(id).getBody();

            return chooseRepository.findById(chooseId)
                    .map(
                            choose -> {
                                FoodCapacity capacity = foodCapacityRepositoryCommitRepository.findFoodCapacityByCapacityIdAndFoodId(
                                        chooseRequest.getCapacityId(), Objects.requireNonNull(foodResponse).getId()
                                )
                                        .orElseGet(FoodCapacity::new);

                                FoodSize foodSize = foodSizeRepositoryCommitRepository.findFoodSizeByFoodIdAndSizeId(
                                        foodResponse.getId(), chooseRequest.getSizeId()
                                )
                                        .orElseGet(FoodSize::new);

                                Commit commit = commitRepository.findById(chooseRequest.getCommitId()).orElseGet(Commit::new);

                                choose = Choose.builder()
                                        .id(choose.getId())
                                        .categoryId(foodResponse.getCategory().getId())
                                        .name(foodResponse.getName())
                                        .mediaId(foodResponse.getMediaId())
                                        .description(foodResponse.getDescription())
                                        .capacityId(capacity.getCapacityId())
                                        .sizeId(foodSize.getSizeId())
                                        .amount(chooseRequest.getAmount())
                                        .isFood(chooseRequest.getFood())
                                        .price(chooseRequest.getAmount() * (
                                                foodResponse.getPrice() + getData(foodSize.getPrice(), 0L) + getData(capacity.getPrice(), 0L))
                                        )
                                        .build();

                                if (commit != null && !commit.getClosed()) {
                                    choose.setCommitId(commit.getId());
                                }

                                choose = chooseRepository.save(choose);
                                chooseIngredientRepository.deleteChooseIngredientsByChooseId(choose.getId());

                                return getChooseIngredients(chooseRequest, choose, new ArrayList<>());
                            }
                    )
                    .orElseGet(
                            () -> {
                                FoodCapacity capacity = foodCapacityRepositoryCommitRepository.findFoodCapacityByCapacityIdAndFoodId(
                                        chooseRequest.getCapacityId(), Objects.requireNonNull(foodResponse).getId()
                                )
                                        .orElseGet(FoodCapacity::new);

                                FoodSize foodSize = foodSizeRepositoryCommitRepository.findFoodSizeByFoodIdAndSizeId(
                                        foodResponse.getId(), chooseRequest.getSizeId()
                                )
                                        .orElseGet(FoodSize::new);

                                if (chooseRequest.getCommitId() != null) {

                                    List<Choose> chooses = chooseRepository.findChoosesByCommitId(chooseRequest.getCommitId());
                                    Choose choose = Choose.builder()
                                            .categoryId(foodResponse.getCategory().getId())
                                            .name(foodResponse.getName())
                                            .description(foodResponse.getDescription())
                                            .mediaId(foodResponse.getMediaId())
                                            .capacityId(capacity.getCapacityId())
                                            .sizeId(foodSize.getSizeId())
                                            .isFood(chooseRequest.getFood())
                                            .amount(chooseRequest.getAmount())
                                            .price(chooseRequest.getAmount() *
                                                   (foodResponse.getPrice() + getData(capacity.getPrice(), 0L) + getData(capacity.getPrice(), 0L))
                                            )
                                            .build();

                                    List<Ingredient> ingredients = new ArrayList<>();

                                    int round = 0;
                                    for (Choose getChoose : chooses) {
                                        if (
                                                (getChoose.getName().equals(foodResponse.getName()) &&
                                                        getData(getChoose.getSizeId(), 0L).equals(chooseRequest.getSizeId()))
                                                ||
                                                (getChoose.getName().equals(foodResponse.getName()) &&
                                                        getData(getChoose.getCapacityId(), 0L).equals(chooseRequest.getCapacityId()))
                                                ||
                                                getChoose.getName().equals(foodResponse.getName())
                                        ) {

                                            choose.setId(getChoose.getId());

                                            List<ChooseIngredient> chooseIngredients = chooseIngredientRepository
                                                    .findChooseIngredientsByChooseId(getChoose.getId());

                                            chooseIngredients.forEach(
                                                    chooseIngredient -> {
                                                        Ingredient ingredient = ingredientRepositoryCommitRepository
                                                                .findById(chooseIngredient.getIngredientId())
                                                                .orElseGet(Ingredient::new);

                                                        ingredients.add(ingredient);
                                                    }
                                            );

                                            choose.setAmount(choose.getAmount() + getChoose.getAmount());
                                            choose.setPrice(choose.getPrice() + getChoose.getPrice());

                                            choose = getChooseIngredients(chooseRequest, choose, ingredients);
                                            round++;

                                            break;
                                        }
                                    }

                                    if (round == 0){
                                        choose = chooseRepository.save(choose);
                                        choose = getChooseIngredients(chooseRequest, choose, ingredients);
                                    }

                                    return getCommitChoose(chooseRequest, authentication, choose, cookie);

                                } else {

                                    Choose choose = Choose.builder()
                                            .categoryId(foodResponse.getCategory().getId())
                                            .name(foodResponse.getName())
                                            .description(foodResponse.getDescription())
                                            .mediaId(foodResponse.getMediaId())
                                            .capacityId(capacity.getCapacityId())
                                            .sizeId(foodSize.getSizeId())
                                            .amount(chooseRequest.getAmount())
                                            .isFood(chooseRequest.getFood())
                                            .price(chooseRequest.getAmount() *
                                                   (foodResponse.getPrice() + getData(foodSize.getPrice(), 0L) + getData(capacity.getPrice(), 0L))
                                            )
                                            .build();

                                    choose = chooseRepository.save(choose);
                                    choose = getChooseIngredients(chooseRequest, choose, new ArrayList<>());

                                    return getCommitChoose(chooseRequest, authentication, choose, cookie);
                                }

                            }
                    );
        }

        if (chooseRequest.getComboId() != null && !chooseRequest.getFood()) {
            chooseRequest.setFoodId(null);
            id = getData(chooseRequest.getComboId(), 0L);
            ComboResponse comboResponse = restClientHandlerCommitService.getComboById(id).getBody();

            return chooseRepository.findById(chooseId)
                    .map(
                            choose -> {
                                choose = Choose.builder()
                                        .id(choose.getId())
                                        .categoryId(Objects.requireNonNull(comboResponse).getCategory().getId())
                                        .commitId(commitRepository.findById(chooseRequest.getCommitId()).orElseGet(Commit::new).getId())
                                        .name(comboResponse.getName())
                                        .description(comboResponse.getDescription())
                                        .mediaId(comboResponse.getMediaId())
                                        .amount(getData(chooseRequest.getAmount(), choose.getAmount()))
                                        .isFood(getData(chooseRequest.getFood(), choose.getFood()))
                                        .price(chooseRequest.getAmount() * comboResponse.getPrice())
                                        .build();

                                Commit commit = commitRepository.findById(chooseRequest.getCommitId()).orElseGet(Commit::new);
                                if (commit != null && !commit.getClosed()) {
                                    choose.setCommitId(commit.getId());
                                }

                                return choose;
                            }
                    )
                    .orElseGet(
                            () -> {

                                if (chooseRequest.getCommitId() != null) {
                                    List<Choose> chooses = chooseRepository.findChoosesByCommitId(chooseRequest.getCommitId());

                                    Choose choose = Choose.builder()
                                            .categoryId(Objects.requireNonNull(comboResponse).getCategory().getId())
                                            .commitId(commitRepository.findById(chooseRequest.getCommitId()).orElseGet(Commit::new).getId())
                                            .name(comboResponse.getName())
                                            .description(comboResponse.getDescription())
                                            .mediaId(comboResponse.getMediaId())
                                            .isFood(chooseRequest.getFood())
                                            .amount(chooseRequest.getAmount())
                                            .price(chooseRequest.getAmount() * comboResponse.getPrice())
                                            .build();

                                    for (Choose getChoose : chooses) {

                                        if (getChoose.getName().equals(comboResponse.getName())) {
                                            choose.setId(getChoose.getId());
                                            choose.setAmount(choose.getAmount() + getChoose.getAmount());
                                            choose.setPrice(choose.getPrice() + getChoose.getPrice());

                                            break;
                                        }
                                    }

                                    return getCommitChoose(chooseRequest, authentication, choose, cookie);

                                } else {
                                    Choose choose = Choose.builder()
                                            .categoryId(Objects.requireNonNull(comboResponse).getCategory().getId())
                                            .commitId(commitRepository.findById(chooseRequest.getCommitId()).orElseGet(Commit::new).getId())
                                            .name(comboResponse.getName())
                                            .description(comboResponse.getDescription())
                                            .mediaId(comboResponse.getMediaId())
                                            .amount(chooseRequest.getAmount())
                                            .isFood(chooseRequest.getFood())
                                            .price(chooseRequest.getAmount() * comboResponse.getPrice())
                                            .build();

                                    return getCommitChoose(chooseRequest, authentication, choose, cookie);
                                }
                            }
                    );
        }

        throw new UnsupportedOperationException();
    }

    private @NotNull Choose getCommitChoose(
            ChooseRequest chooseRequest, String authentication, Choose choose, Cookie cookie
    ) {
        Commit commit = commitRepository.findById(getData(chooseRequest.getCommitId(), 0L)).orElse(null);

        if (commit != null && !commit.getClosed()) {

            IdentityPrincipal identityPrincipal = restClientHandlerCommitService
                    .getIdentityPrincipal(authentication).getBody();

            if (Objects.requireNonNull(identityPrincipal).getAuthenticated()) {
                commit.setIdentityId(identityPrincipal.getId());
            }

            choose.setCommitId(commit.getId());
            chooseRepository.save(choose);

            List<Choose> choosesByCommitId = chooseRepository.findChoosesByCommitId(commit.getId());
            commit.setPrice(
                    choosesByCommitId.stream()
                            .map(Choose::getPrice)
                            .reduce(Long::sum)
                            .orElse(0L)
            );

            commitRepository.save(commit);

        } else if (commit != null && commit.getClosed()){
            throw new RuntimeException("Заказ уже закрыт и не принят к обслуживанию");

        } else {

            commit = Commit.builder()
                    .closed(false)
                    .price(choose.getPrice())
                    .build();

            IdentityPrincipal identityPrincipal = restClientHandlerCommitService
                    .getIdentityPrincipal(authentication).getBody();

            if (Objects.requireNonNull(identityPrincipal).getAuthenticated()) {
                commit.setIdentityId(identityPrincipal.getId());
                Token token = tokenRepositoryCommitRepository.save(
                        Token.builder().name(cookie.getName()).value(cookie.getValue()).build()
                );
                commit.setTokenId(token.getId());

            } else {
                Token token = tokenRepositoryCommitRepository.save(
                        Token.builder().name(cookie.getName()).value(cookie.getValue()).build()
                );
                commit.setTokenId(token.getId());
            }

            Commit saved = commitRepository.save(commit);
            choose.setCommitId(saved.getId());
        }

        return chooseRepository.save(choose);
    }

    private @NotNull Choose getChooseIngredients(ChooseRequest chooseRequest, Choose choose, List<Ingredient> ingredients) {
        chooseRequest.getIngredients().forEach(
                aLong -> {
                    chooseIngredientRepository.save(
                            ChooseIngredient.builder()
                                    .chooseId(choose.getId())
                                    .ingredientId(aLong)
                                    .build()
                    );
                    Ingredient ingredient = ingredientRepositoryCommitRepository.findById(aLong)
                            .orElseGet(Ingredient::new);
                    ingredients.add(ingredient);
                }
        );

        ingredients.forEach(
                ingredient ->
                        choose.setPrice(choose.getPrice() + ingredient.getPrice())
        );

        return chooseRepository.save(choose);
    }

    public ChooseResponse toChooseResponse(Choose choose) {
        if (choose.getCapacityId() == null) {
            choose.setCapacityId(0L);
        }

        if (choose.getSizeId() == null) {
            choose.setSizeId(0L);
        }

        if (choose.getFood()) {
            List<ChooseIngredient> chooseIngredients = chooseIngredientRepository.findChooseIngredientsByChooseId(choose.getId());
            FoodResponse foodResponse = restClientHandlerCommitService.getFoodByName(choose.getName()).getBody();
            List<Ingredient> ingredients = new ArrayList<>();
            chooseIngredients.forEach(
                    chooseIngredient -> {
                        Ingredient ingredient = ingredientRepositoryCommitRepository
                                .findById(chooseIngredient.getIngredientId()).orElseGet(Ingredient::new);
                        ingredients.add(ingredient);
                    }
            );
            return ChooseResponse.builder()
                    .id(choose.getId())
                    .category(Objects.requireNonNull(foodResponse).getCategory())
                    .name(choose.getName())
                    .description(choose.getDescription())
                    .size(sizeRepositoryCommitRepository.findById(choose.getSizeId()).orElseGet(Size::new))
                    .capacity(capacityRepositoryCommitRepository.findById(choose.getCapacityId()).orElseGet(Capacity::new))
                    .price(choose.getPrice())
                    .amount(choose.getAmount())
                    .isFood(choose.getFood())
                    .ingredients(ingredients)
                    .commitId(choose.getCommitId())
                    .mediaId(choose.getMediaId())
                    .build();

        } else {

            return ChooseResponse.builder()
                    .id(choose.getId())
                    .category(categoryRepositoryCommitRepository.findById(choose.getCategoryId()).orElseGet(Category::new))
                    .name(choose.getName())
                    .description(choose.getDescription())
                    .price(choose.getPrice())
                    .amount(choose.getAmount())
                    .isFood(choose.getFood())
                    .commitId(choose.getCommitId())
                    .mediaId(choose.getMediaId())
                    .build();
        }
    }
}
