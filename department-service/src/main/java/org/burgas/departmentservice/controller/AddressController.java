package org.burgas.departmentservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.departmentservice.dto.AddressRequest;
import org.burgas.departmentservice.dto.AddressResponse;
import org.burgas.departmentservice.handler.IdentityPrincipalHandlerDepartmentService;
import org.burgas.departmentservice.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static java.net.URI.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

@Controller
@RequestMapping("/addresses")
@Tag(
        name = "AddressController",
        description = "Контроллер для управления и организации адресов филиалов"
)
public class AddressController {

    private final AddressService addressService;
    private final IdentityPrincipalHandlerDepartmentService identityPrincipalHandlerDepartmentService;

    public AddressController(
            AddressService addressService,
            IdentityPrincipalHandlerDepartmentService identityPrincipalHandlerDepartmentService
    ) {
        this.addressService = addressService;
        this.identityPrincipalHandlerDepartmentService = identityPrincipalHandlerDepartmentService;
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
        AddressResponse addressResponse = identityPrincipalHandlerDepartmentService.handleAdminIdentityPrincipal(
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
        AddressResponse addressResponse = identityPrincipalHandlerDepartmentService.handleAdminIdentityPrincipal(
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
                        identityPrincipalHandlerDepartmentService.handleAdminIdentityPrincipal(
                                addressService.deleteById(addressId), authentication
                        )
                );
    }
}
