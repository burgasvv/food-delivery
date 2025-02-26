package org.burgas.departmentservice.service;

import org.burgas.departmentservice.dto.DepartmentRequest;
import org.burgas.departmentservice.dto.DepartmentResponse;
import org.burgas.departmentservice.exception.DepartmentNotFoundException;
import org.burgas.departmentservice.mapper.DepartmentMapper;
import org.burgas.departmentservice.repository.DepartmentRepository;
import org.burgas.departmentservice.util.ServiceUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    public List<DepartmentResponse> findAll() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toDepartmentResponse)
                .toList();
    }

    public DepartmentResponse findById(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .map(departmentMapper::toDepartmentResponse)
                .orElseGet(DepartmentResponse::new);
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public Long createOrUpdate(DepartmentRequest departmentRequest) {
        return departmentRepository.save(
                departmentMapper.toDepartment(departmentRequest)
        )
                .getId();
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteById(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .map(
                        department -> {
                            departmentRepository.deleteById(department.getId());
                            return String.format(ServiceUtil.DEPARTMENT_DELETED, department.getId());
                        }
                )
                .orElseThrow(() -> new DepartmentNotFoundException(String.format(ServiceUtil.DEPARTMENT_NOT_FOUND, departmentId)));
    }
}
