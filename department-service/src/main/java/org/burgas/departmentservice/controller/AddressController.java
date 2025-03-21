package org.burgas.departmentservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.departmentservice.dto.AddressRequest;
import org.burgas.departmentservice.dto.AddressResponse;
import org.burgas.departmentservice.handler.IdentityPrincipalHandler;
import org.burgas.departmentservice.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.net.URI.create;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Controller
@RequestMapping("/addresses")
@Tag(
        name = "AddressController",
        description = "Контроллер для управления и организации адресов филиалов"
)
public class AddressController {

    private final AddressService addressService;
    private final IdentityPrincipalHandler identityPrincipalHandler;

    public AddressController(
            AddressService addressService,
            IdentityPrincipalHandler identityPrincipalHandler
    ) {
        this.addressService = addressService;
        this.identityPrincipalHandler = identityPrincipalHandler;
    }

    @GetMapping
    @Operation(
            summary = "Весь список адресов",
            description = "Получение полного списка адресов филиалов"
    )
    public @ResponseBody ResponseEntity<List<AddressResponse>> getAllAddresses() {
        return ResponseEntity
                .ok()
                .contentType(APPLICATION_JSON)
                .body(addressService.findAll());
    }

    @GetMapping(value = "/by-id")
    @Operation(
            summary = "Адрес по идентификатору",
            description = "Получение адреса по идентификатору"
    )
    public @ResponseBody ResponseEntity<AddressResponse> getAddressById(@RequestParam Long addressId) {
        return ResponseEntity
                .ok()
                .contentType(APPLICATION_JSON)
                .body(addressService.findById(addressId));
    }

    @PostMapping(value = "/create")
    @Operation(
            summary = "Добавление адреса",
            description = "Добавление данных об адресе и переадресация на страницу"
    )
    public @ResponseBody ResponseEntity<AddressResponse> createAddress(
            @RequestBody AddressRequest addressRequest, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        AddressResponse addressResponse = identityPrincipalHandler.handleAdminIdentityPrincipal(
                addressService.createOrUpdate(addressRequest), authentication
        );
        return ResponseEntity
                .status(FOUND)
                .location(create("/addresses/by-id?addressId=" + addressResponse.getId()))
                .body(addressResponse);
    }

    @PostMapping(value = "/update")
    @Operation(
            summary = "Изменение адреса",
            description = "Изменение данных об адресе и переадресация на страницу"
    )
    public @ResponseBody ResponseEntity<AddressResponse> updateAddress(
            @RequestBody AddressRequest addressRequest, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        AddressResponse addressResponse = identityPrincipalHandler.handleAdminIdentityPrincipal(
                addressService.createOrUpdate(addressRequest), authentication
        );
        return ResponseEntity
                .status(FOUND)
                .location(create("/addresses/by-id?addressId=" + addressResponse.getId()))
                .body(addressResponse);
    }

    @DeleteMapping(value = "/delete")
    @Operation(
            summary = "Удаление адреса",
            description = "Удаление данных об адресе"
    )
    public @ResponseBody ResponseEntity<String> deleteAddress(
            @RequestParam Long addressId, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity
                .status(OK)
                .body(
                        identityPrincipalHandler.handleAdminIdentityPrincipal(
                                addressService.deleteById(addressId), authentication
                        )
                );
    }
}
