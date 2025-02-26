package org.burgas.departmentservice.service;

import org.burgas.departmentservice.dto.AddressRequest;
import org.burgas.departmentservice.dto.AddressResponse;
import org.burgas.departmentservice.exception.AddressNotFoundException;
import org.burgas.departmentservice.mapper.AddressMapper;
import org.burgas.departmentservice.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.burgas.departmentservice.util.ServiceUtil.ADDRESS_DELETED;
import static org.burgas.departmentservice.util.ServiceUtil.ADDRESS_NOT_FOUND;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public List<AddressResponse> findAll() {
        return addressRepository.findAll()
                .stream()
                .map(addressMapper::toAddressResponse)
                .toList();
    }

    public AddressResponse findById(Long addressId) {
        return addressRepository.findById(addressId)
                .map(addressMapper::toAddressResponse)
                .orElseGet(AddressResponse::new);
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public AddressResponse createOrUpdate(AddressRequest addressRequest) {
        return addressMapper.toAddressResponse(
                addressRepository.save(addressMapper.toAddress(addressRequest))
        );
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteById(Long addressId) {
        return addressRepository.findById(addressId)
                .map(
                        address -> {
                            addressRepository.deleteById(address.getId());
                            return String.format(ADDRESS_DELETED, addressId);
                        }
                )
                .orElseThrow(() -> new AddressNotFoundException(String.format(ADDRESS_NOT_FOUND, addressId)));
    }
}
