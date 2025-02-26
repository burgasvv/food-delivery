package org.burgas.departmentservice.mapper;

import org.burgas.departmentservice.dto.AddressResponse;
import org.burgas.departmentservice.dto.DepartmentRequest;
import org.burgas.departmentservice.dto.DepartmentResponse;
import org.burgas.departmentservice.entity.Department;
import org.burgas.departmentservice.repository.AddressRepository;
import org.burgas.departmentservice.repository.DepartmentRepository;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    private final DepartmentRepository departmentRepository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public DepartmentMapper(
            DepartmentRepository departmentRepository,
            AddressRepository addressRepository, AddressMapper addressMapper
    ) {
        this.departmentRepository = departmentRepository;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    private <T> T getData(T first, T second) {
        return first == null ? second : first;
    }

    public Department toDepartment(DepartmentRequest departmentRequest) {
        Long departmentId = getData(departmentRequest.id(), 0L);
        return departmentRepository.findById(departmentId)
                .map(
                        department -> Department.builder()
                                .id(department.getId())
                                .addressId(getData(departmentRequest.addressId(), department.getAddressId()))
                                .opensAt(getData(departmentRequest.opensAt(), department.getOpensAt()))
                                .closesAt(getData(departmentRequest.closesAt(), department.getClosesAt()))
                                .opened(getData(departmentRequest.opened(), department.getOpened()))
                                .build()
                )
                .orElseGet(
                        () -> Department.builder()
                                .addressId(departmentRequest.addressId())
                                .opensAt(departmentRequest.opensAt())
                                .closesAt(departmentRequest.closesAt())
                                .opened(departmentRequest.opened())
                                .build()
                );
    }

    public DepartmentResponse toDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .address(
                        addressRepository.findById(department.getAddressId())
                                .map(addressMapper::toAddressResponse)
                                .orElseGet(AddressResponse::new)
                )
                .opensAt(department.getOpensAt())
                .closesAt(department.getClosesAt())
                .opened(department.getOpened())
                .build();
    }
}
