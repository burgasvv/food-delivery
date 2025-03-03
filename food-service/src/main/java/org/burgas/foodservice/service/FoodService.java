package org.burgas.foodservice.service;

import org.burgas.foodservice.dto.FoodRequest;
import org.burgas.foodservice.dto.FoodResponse;
import org.burgas.foodservice.dto.Media;
import org.burgas.foodservice.exception.FoodNotFoundException;
import org.burgas.foodservice.handler.RestClientHandler;
import org.burgas.foodservice.mapper.FoodMapper;
import org.burgas.foodservice.repository.FoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.burgas.foodservice.entity.FoodMessage.*;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class FoodService {

    private static final Logger log = LoggerFactory.getLogger(FoodService.class);
    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;
    private final RestClientHandler restClientHandler;

    public FoodService(
            FoodRepository foodRepository, FoodMapper foodMapper, RestClientHandler restClientHandler
    ) {
        this.foodRepository = foodRepository;
        this.foodMapper = foodMapper;
        this.restClientHandler = restClientHandler;
    }

    public List<FoodResponse> findAll() {
        return foodRepository.findAll()
                .parallelStream()
                .map(foodMapper::toFoodResponse)
                .toList();
    }

    public FoodResponse findById(Long foodId) {
        return foodRepository.findById(foodId)
                .map(foodMapper::toFoodResponse)
                .orElseGet(FoodResponse::new);
    }

    public FoodResponse findByName(String name) {
        return foodRepository.findFoodByName(name)
                .map(foodMapper::toFoodResponse)
                .orElseGet(FoodResponse::new);
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public Long createOrUpdate(FoodRequest foodRequest) {
        return foodRepository.save(foodMapper.toFood(foodRequest)).getId();
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteById(Long foodId) {
        return foodRepository.findById(foodId)
                .map(
                        food -> {
                            foodRepository.deleteById(food.getId());
                            return String.format(FOOD_DELETED.getMessage(), food.getId());
                        }
                )
                .orElseThrow(
                        () -> new FoodNotFoundException(
                                String.format(FOOD_NOT_FOUND.getMessage(), foodId))
                );
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String uploadFoodImage(Long foodId, Long previousMediaId, MultipartFile multipartFile) {
        return foodRepository.findById(foodId)
                .map(
                        food -> {

                            if (previousMediaId != null) {
                                Media media = restClientHandler.getMediaById(previousMediaId).getBody();

                                if (media != null && media.getId().equals(food.getMediaId())) {
                                    food.setMediaId(null);
                                    foodRepository.save(food);
                                    foodRepository.deleteMediaInFood(previousMediaId);
                                    log.info(PREVIEW_FOOD_IMAGE_DELETED.getMessage());
                                }
                            }

                            try {
                                Integer mediaId = foodRepository.insertIntoMediaWithMultipartFile(
                                        multipartFile.getOriginalFilename(),
                                        multipartFile.getContentType(),
                                        multipartFile.getBytes()
                                );
                                food.setMediaId(Long.valueOf(mediaId));
                                foodRepository.save(food);

                                return IMAGE_FOR_FOOD_UPLOADED.getMessage();

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                )
                .orElseThrow(
                        () -> new FoodNotFoundException(String.format(FOOD_NOT_FOUND.getMessage(), foodId))
                );
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteFoodImage(Long foodId) {
        return foodRepository.findById(foodId)
                .map(
                        food -> {
                            Long mediaId = food.getMediaId();
                            food.setMediaId(null);
                            foodRepository.save(food);
                            foodRepository.deleteMediaInFood(mediaId);
                            return IMAGE_FOR_FOOD_DELETED.getMessage();
                        }
                )
                .orElseThrow(
                        () -> new FoodNotFoundException(FOOD_NOT_FOUND.getMessage())
                );
    }
}
