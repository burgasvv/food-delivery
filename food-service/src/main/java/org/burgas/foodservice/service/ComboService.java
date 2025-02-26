package org.burgas.foodservice.service;

import org.burgas.foodservice.dto.ComboRequest;
import org.burgas.foodservice.dto.ComboResponse;
import org.burgas.foodservice.exception.ComboNotFoundException;
import org.burgas.foodservice.exception.FoodNotFoundException;
import org.burgas.foodservice.mapper.ComboMapper;
import org.burgas.foodservice.repository.ComboRepository;
import org.burgas.foodservice.repository.MediaRepositoryFoodRepository;
import org.burgas.mediaservice.entity.Media;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.burgas.foodservice.entity.ComboMessage.*;
import static org.burgas.foodservice.entity.FoodMessage.FOOD_NOT_FOUND;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ComboService {

    private static final Logger log = LoggerFactory.getLogger(ComboService.class);
    private final ComboRepository comboRepository;
    private final ComboMapper comboMapper;
    private final MediaRepositoryFoodRepository mediaRepositoryFoodRepository;

    public ComboService(
            ComboRepository comboRepository, ComboMapper comboMapper,
            MediaRepositoryFoodRepository mediaRepositoryFoodRepository
    ) {
        this.comboRepository = comboRepository;
        this.comboMapper = comboMapper;
        this.mediaRepositoryFoodRepository = mediaRepositoryFoodRepository;
    }

    public List<ComboResponse> findAll() {
        return comboRepository.findAll()
                .parallelStream()
                .map(comboMapper::toComboResponse)
                .toList();
    }

    public ComboResponse findById(Long comboId) {
        return comboRepository.findById(comboId)
                .map(comboMapper::toComboResponse)
                .orElseGet(ComboResponse::new);
    }

    public ComboResponse findByName(String name) {
        return comboRepository.findComboByName(name)
                .map(comboMapper::toComboResponse)
                .orElseGet(ComboResponse::new);
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public Long createOrUpdate(ComboRequest comboRequest) {
        return comboRepository.save(comboMapper.toCombo(comboRequest)).getId();
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteById(Long comboId) {
        return comboRepository.findById(comboId)
                .map(
                        combo -> {
                            comboRepository.deleteById(combo.getId());
                            return String.format(COMBO_DELETED.getMessage(), combo.getId());
                        }
                )
                .orElseThrow(
                        () -> new ComboNotFoundException(
                                String.format(COMBO_NOT_FOUND.getMessage(), comboId)
                        )
                );
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String uploadComboImage(Long comboId, Long previousMediaId, MultipartFile multipartFile) {
        return comboRepository.findById(comboId)
                .map(
                        food -> {

                            if (previousMediaId != null) {
                                Media media = mediaRepositoryFoodRepository.findById(previousMediaId)
                                        .orElse(null);

                                if (media != null && media.getId().equals(food.getMediaId())) {
                                    food.setMediaId(null);
                                    comboRepository.save(food);
                                    mediaRepositoryFoodRepository.deleteById(previousMediaId);
                                    log.info(PREVIEW_COMBO_IMAGE_DELETED.getMessage());
                                }
                            }

                            try {
                                Media media = mediaRepositoryFoodRepository.save(
                                        Media.builder()
                                                .name(multipartFile.getOriginalFilename())
                                                .contentType(multipartFile.getContentType())
                                                .data(multipartFile.getBytes())
                                                .build()
                                );
                                food.setMediaId(media.getId());
                                comboRepository.save(food);
                                log.info(IMAGE_FOR_COMBO_UPLOADED.getMessage());

                                return IMAGE_FOR_COMBO_UPLOADED.getMessage();

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                )
                .orElseThrow(
                        () -> new FoodNotFoundException(String.format(FOOD_NOT_FOUND.getMessage(), comboId))
                );
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteFoodImage(Long foodId) {
        return comboRepository.findById(foodId)
                .map(
                        food -> {
                            Long mediaId = food.getMediaId();
                            food.setMediaId(null);
                            comboRepository.save(food);

                            mediaRepositoryFoodRepository.deleteById(mediaId);
                            return IMAGE_FOR_COMBO_DELETED.getMessage();
                        }
                )
                .orElseThrow(
                        () -> new ComboNotFoundException(COMBO_NOT_FOUND.getMessage())
                );
    }
}
