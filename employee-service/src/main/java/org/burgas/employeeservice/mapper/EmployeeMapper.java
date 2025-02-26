package org.burgas.employeeservice.mapper;

import org.burgas.employeeservice.dto.EmployeeRequest;
import org.burgas.employeeservice.dto.EmployeeResponse;
import org.burgas.employeeservice.entity.Employee;
import org.burgas.employeeservice.handler.RestClientHandlerInEmployeeService;
import org.burgas.employeeservice.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    private final EmployeeRepository employeeRepository;
    private final RestClientHandlerInEmployeeService restClientHandlerInEmployeeService;

    public EmployeeMapper(
            EmployeeRepository employeeRepository,
            RestClientHandlerInEmployeeService restClientHandlerInEmployeeService
    ) {
        this.employeeRepository = employeeRepository;
        this.restClientHandlerInEmployeeService = restClientHandlerInEmployeeService;
    }

    private <T> T getData(T first, T second) {
        return first == null ? second : first;
    }

    public Employee toEmployee(EmployeeRequest employeeRequest) {
        Long employeeId = getData(employeeRequest.id(), 0L);
        return employeeRepository.findById(employeeId)
                .map(
                        employee -> Employee.builder()
                                .id(employee.getId())
                                .firstname(getData(employeeRequest.firstname(), employee.getFirstname()))
                                .lastname(getData(employeeRequest.lastname(), employee.getLastname()))
                                .patronymic(getData(employeeRequest.patronymic(), employee.getPatronymic()))
                                .departmentId(getData(employeeRequest.departmentId(), employee.getDepartmentId()))
                                .identityId(getData(employeeRequest.identityId(), employee.getIdentityId()))
                                .build()
                )
                .orElseGet(
                        () -> Employee.builder()
                                .firstname(employeeRequest.firstname())
                                .lastname(employeeRequest.lastname())
                                .patronymic(employeeRequest.patronymic())
                                .departmentId(employeeRequest.departmentId())
                                .identityId(employeeRequest.identityId())
                                .build()
                );
    }

    public EmployeeResponse toEmployeeResponse(Employee employee, String authentication) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstname(employee.getFirstname())
                .lastname(employee.getLastname())
                .patronymic(employee.getPatronymic())
                .department(
                        restClientHandlerInEmployeeService
                                .getDepartmentResponse(employee.getDepartmentId())
                                .getBody()
                )
                .identity(
                        restClientHandlerInEmployeeService.getIdentityResponse(
                                employee.getIdentityId(), authentication
                        )
                                .getBody()
                )
                .build();
    }
}
