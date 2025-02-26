package org.burgas.foodservice.service;

import org.burgas.foodservice.entity.Size;
import org.burgas.foodservice.exception.SizeNotFoundException;
import org.burgas.foodservice.repository.SizeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.burgas.foodservice.entity.SizeMessage.SIZE_DELETED;
import static org.burgas.foodservice.entity.SizeMessage.SIZE_NOT_FOUND;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class SizeService {

    private final SizeRepository sizeRepository;

    public SizeService(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    public List<Size> findAll() {
        return sizeRepository.findAll();
    }

    public List<Size> findSizesByFoodId(Long foodId) {
        return sizeRepository.findSizesByFoodId(foodId);
    }

    public Size findById(Long sizeId) {
        return sizeRepository.findById(sizeId).orElseGet(Size::new);
    }

    public Size findBySize(Long size) {
        return sizeRepository.findSizeBySize(size).orElseGet(Size::new);
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public Long createOrUpdate(Size size) {
        return sizeRepository.save(size).getId();
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteById(Long sizeId) {
        return sizeRepository.findById(sizeId)
                .map(
                        size -> {
                            sizeRepository.deleteById(size.getId());
                            return String.format(SIZE_DELETED.getMessage(), size.getId());
                        }
                )
                .orElseThrow(
                        () -> new SizeNotFoundException(
                                String.format(SIZE_NOT_FOUND.getMessage(), sizeId))
                );
    }
}
