package org.burgas.foodservice.service;

import org.burgas.foodservice.entity.Capacity;
import org.burgas.foodservice.exception.CapacityNotFoundException;
import org.burgas.foodservice.repository.CapacityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.burgas.foodservice.entity.CapacityMessage.CAPACITY_DELETED;
import static org.burgas.foodservice.entity.CapacityMessage.CAPACITY_NOT_FOUND;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CapacityService {

    private final CapacityRepository capacityRepository;

    public CapacityService(CapacityRepository capacityRepository) {
        this.capacityRepository = capacityRepository;
    }

    public List<Capacity> findAll() {
        return capacityRepository.findAll();
    }

    public Capacity findById(Long capacityId) {
        return capacityRepository.findById(capacityId).orElseGet(Capacity::new);
    }

    public Capacity findByLiters(Float liters) {
        return capacityRepository.findCapacityByLiters(liters).orElseGet(Capacity::new);
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public Long createOrUpdate(Capacity capacity) {
        return capacityRepository.save(capacity).getId();
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteById(Long capacityId) {
        return capacityRepository.findById(capacityId)
                .map(
                        capacity -> {
                            capacityRepository.deleteById(capacity.getId());
                            return String.format(CAPACITY_DELETED.getMessage(), capacity.getId());
                        }
                )
                .orElseThrow(
                        () -> new CapacityNotFoundException(
                                String.format(CAPACITY_NOT_FOUND.getMessage(), capacityId))
                );
    }
}
