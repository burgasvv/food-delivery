package org.burgas.foodservice;

import org.burgas.foodservice.entity.Capacity;
import org.burgas.mediaservice.entity.Media;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(
        basePackageClasses = {
                Media.class, Capacity.class
        }
)
public class FoodServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodServiceApplication.class, args);
    }

}
