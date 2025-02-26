package org.burgas.commitservice;

import org.burgas.commitservice.entity.Choose;
import org.burgas.commitservice.entity.ChooseIngredient;
import org.burgas.commitservice.entity.ChooseIngredientPK;
import org.burgas.databaseserver.entity.Token;
import org.burgas.foodservice.entity.FoodCapacity;
import org.burgas.foodservice.entity.FoodCapacityPK;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestClient;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(
        basePackageClasses = {
                FoodCapacity.class, FoodCapacityPK.class, Choose.class,
                ChooseIngredient.class, ChooseIngredientPK.class, Token.class
        }
)
public class CommitServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommitServiceApplication.class, args);
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }
}
