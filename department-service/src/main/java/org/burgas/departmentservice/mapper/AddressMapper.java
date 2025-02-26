package org.burgas.departmentservice.mapper;

import org.burgas.departmentservice.dto.AddressRequest;
import org.burgas.departmentservice.dto.AddressResponse;
import org.burgas.departmentservice.entity.Address;
import org.burgas.departmentservice.repository.AddressRepository;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    private final AddressRepository addressRepository;

    public AddressMapper(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    private <T> T getData(T first, T second) {
        return first == null ? second : first;
    }

    public Address toAddress(AddressRequest addressRequest) {
        Long addressId = getData(addressRequest.id(), 0L);
        return addressRepository.findById(addressId)
                .map(
                        address -> Address.builder()
                                .id(address.getId())
                                .city(getData(addressRequest.city(), address.getCity()))
                                .street(getData(addressRequest.street(), address.getStreet()))
                                .house(getData(addressRequest.house(), address.getHouse()))
                                .apartment(getData(addressRequest.apartment(), address.getApartment()))
                                .build()
                )
                .orElseGet(
                        () -> Address.builder()
                                .city(addressRequest.city())
                                .street(addressRequest.street())
                                .house(addressRequest.house())
                                .apartment(addressRequest.apartment())
                                .build()
                );
    }

    public AddressResponse toAddressResponse(Address address) {
        return AddressResponse.builder()
                .id(address.getId())
                .city(address.getCity())
                .street(address.getStreet())
                .house(address.getHouse())
                .apartment(address.getApartment())
                .build();
    }
}
