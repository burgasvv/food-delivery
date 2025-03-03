package org.burgas.commitservice.mapper;

import jakarta.servlet.http.Cookie;
import org.burgas.commitservice.dto.*;
import org.burgas.commitservice.entity.Choose;
import org.burgas.commitservice.entity.ChooseIngredient;
import org.burgas.commitservice.entity.Commit;
import org.burgas.commitservice.handler.RestClientHandler;
import org.burgas.commitservice.repository.ChooseIngredientRepository;
import org.burgas.commitservice.repository.ChooseRepository;
import org.burgas.commitservice.repository.CommitRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ChooseMapper {

    private final ChooseRepository chooseRepository;
    private final RestClientHandler restClientHandler;
    private final ChooseIngredientRepository chooseIngredientRepository;
    private final CommitRepository commitRepository;

    public ChooseMapper(
            ChooseRepository chooseRepository,
            RestClientHandler restClientHandler,
            ChooseIngredientRepository chooseIngredientRepository,
            CommitRepository commitRepository
    ) {
        this.chooseRepository = chooseRepository;
        this.restClientHandler = restClientHandler;
        this.chooseIngredientRepository = chooseIngredientRepository;
        this.commitRepository = commitRepository;
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
            FoodResponse foodResponse = restClientHandler.getFoodById(id).getBody();

            return chooseRepository.findById(chooseId)
                    .map(
                            choose -> {
                                FoodCapacity capacity = restClientHandler.getFoodCapacity(
                                        chooseRequest.getCapacityId(), Objects.requireNonNull(foodResponse).getId()
                                )
                                        .getBody();

                                FoodSize foodSize = restClientHandler.getFoodSize(
                                        chooseRequest.getSizeId(), foodResponse.getId()
                                )
                                        .getBody();

                                Commit commit = commitRepository.findById(chooseRequest.getCommitId()).orElseGet(Commit::new);

                                choose = Choose.builder()
                                        .id(choose.getId())
                                        .categoryId(foodResponse.getCategory().getId())
                                        .name(foodResponse.getName())
                                        .mediaId(foodResponse.getMediaId())
                                        .description(foodResponse.getDescription())
                                        .capacityId(capacity != null ? capacity.getCapacityId() : null)
                                        .sizeId(foodSize != null ? foodSize.getSizeId() : null)
                                        .amount(chooseRequest.getAmount())
                                        .isFood(chooseRequest.getFood())
                                        .price(chooseRequest.getAmount() * (
                                                foodResponse.getPrice() + getData(foodSize != null ? foodSize.getPrice() : null, 0L)
                                                + getData(capacity != null ? capacity.getPrice() : null, 0L))
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
                                FoodCapacity capacity = restClientHandler.getFoodCapacity(
                                                chooseRequest.getCapacityId(), Objects.requireNonNull(foodResponse).getId()
                                        )
                                        .getBody();

                                FoodSize foodSize = restClientHandler.getFoodSize(
                                                chooseRequest.getSizeId(), foodResponse.getId()
                                        )
                                        .getBody();

                                if (chooseRequest.getCommitId() != null) {

                                    List<Choose> chooses = chooseRepository.findChoosesByCommitId(chooseRequest.getCommitId());
                                    Choose choose = Choose.builder()
                                            .categoryId(foodResponse.getCategory().getId())
                                            .name(foodResponse.getName())
                                            .description(foodResponse.getDescription())
                                            .mediaId(foodResponse.getMediaId())
                                            .capacityId(capacity != null ? capacity.getCapacityId() : null)
                                            .sizeId(foodSize != null ? foodSize.getSizeId() : null)
                                            .isFood(chooseRequest.getFood())
                                            .amount(chooseRequest.getAmount())
                                            .price(chooseRequest.getAmount() *
                                                   (foodResponse.getPrice() + getData(capacity != null ? capacity.getPrice() : null, 0L)
                                                    + getData(capacity != null ? capacity.getPrice() : null, 0L))
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
                                                        Ingredient ingredient = restClientHandler
                                                                .getIngredientById(chooseIngredient.getIngredientId())
                                                                        .getBody();

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
                                            .capacityId(capacity != null ? capacity.getCapacityId() : null)
                                            .sizeId(foodSize != null ? foodSize.getSizeId() : null)
                                            .amount(chooseRequest.getAmount())
                                            .isFood(chooseRequest.getFood())
                                            .price(chooseRequest.getAmount() *
                                                   (foodResponse.getPrice() + getData(foodSize != null ? foodSize.getPrice() : null, 0L)
                                                    + getData(capacity != null ? capacity.getPrice() : null, 0L))
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
            ComboResponse comboResponse = restClientHandler.getComboById(id).getBody();

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

            IdentityPrincipal identityPrincipal = restClientHandler
                    .getPrincipal(authentication).getBody();

            //noinspection DataFlowIssue
            if (identityPrincipal.getAuthenticated())
                commit.setIdentityId(identityPrincipal.getId());

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

            IdentityPrincipal identityPrincipal = restClientHandler
                    .getPrincipal(authentication).getBody();

            //noinspection DataFlowIssue
            if (identityPrincipal.getAuthenticated())
                commit.setIdentityId(identityPrincipal.getId());

            Integer tokenId = commitRepository.insertIntoTokenFromCommit(cookie.getName(), cookie.getValue());
            commit.setTokenId(Long.valueOf(tokenId));

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
                    Ingredient ingredient = restClientHandler.getIngredientById(aLong)
                                    .getBody();
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
            FoodResponse foodResponse = restClientHandler.getFoodByName(choose.getName()).getBody();
            List<Ingredient> ingredients = new ArrayList<>();
            chooseIngredients.forEach(
                    chooseIngredient -> {
                        Ingredient ingredient = restClientHandler
                                .getIngredientById(chooseIngredient.getIngredientId()).getBody();
                        ingredients.add(ingredient);
                    }
            );
            return ChooseResponse.builder()
                    .id(choose.getId())
                    .category(foodResponse != null ? foodResponse.getCategory() : null)
                    .name(choose.getName())
                    .description(choose.getDescription())
                    .size(restClientHandler.getSizeById(choose.getSizeId()).getBody())
                    .capacity(restClientHandler.getCapacityById(choose.getCapacityId()).getBody())
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
                    .category(restClientHandler.getCategoryById(choose.getCategoryId()).getBody())
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
