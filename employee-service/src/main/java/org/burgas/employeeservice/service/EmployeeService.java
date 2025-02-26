package org.burgas.employeeservice.service;

import org.burgas.employeeservice.dto.EmployeeRequest;
import org.burgas.employeeservice.dto.EmployeeResponse;
import org.burgas.employeeservice.entity.Employee;
import org.burgas.employeeservice.exception.EmployeeNotFoundException;
import org.burgas.employeeservice.exception.MediaNotFoundException;
import org.burgas.employeeservice.handler.RestClientHandlerInEmployeeService;
import org.burgas.employeeservice.mapper.EmployeeMapper;
import org.burgas.employeeservice.repository.EmployeeRepository;
import org.burgas.employeeservice.repository.MediaRepositoryEmployeeRepository;
import org.burgas.identityservice.dto.IdentityPrincipal;
import org.burgas.identityservice.exception.NotAuthorizedException;
import org.burgas.mediaservice.entity.Media;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.burgas.employeeservice.entity.EmployeeMessage.*;
import static org.burgas.mediaservice.entity.MediaMessage.MEDIA_NOT_FOUND;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final RestClientHandlerInEmployeeService restClientHandlerInEmployeeService;
    private final MediaRepositoryEmployeeRepository mediaRepositoryEmployeeRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository, EmployeeMapper employeeMapper,
            RestClientHandlerInEmployeeService restClientHandlerInEmployeeService,
            MediaRepositoryEmployeeRepository mediaRepositoryEmployeeRepository
    ) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.restClientHandlerInEmployeeService = restClientHandlerInEmployeeService;
        this.mediaRepositoryEmployeeRepository = mediaRepositoryEmployeeRepository;
    }

    public List<EmployeeResponse> findAll(String authentication) {
        return employeeRepository.findAll()
                .stream()
                .map(employee -> employeeMapper.toEmployeeResponse(employee, authentication))
                .toList();
    }

    public List<EmployeeResponse> findByDepartmentId(Long departmentId, String authentication) {
        return employeeRepository.findEmployeesByDepartmentId(departmentId)
                .stream()
                .map(employee -> employeeMapper.toEmployeeResponse(employee, authentication))
                .toList();
    }

    public EmployeeResponse findById(Long employeeId, String authentication) {
        return employeeRepository.findById(employeeId)
                .map(employee -> employeeMapper.toEmployeeResponse(employee, authentication))
                .orElseGet(EmployeeResponse::new);
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public Long createOrUpdate(EmployeeRequest employeeRequest, String authentication) {
        if (employeeRequest.identityId() == null) {
            throw new RuntimeException(NO_IDENTITY_ID.getMessage());
        }
        return Optional.ofNullable(restClientHandlerInEmployeeService.getIdentityPrincipal(authentication).getBody())
                .filter(
                        identityPrincipal -> Objects.requireNonNull(identityPrincipal).getAuthenticated() &&
                                             identityPrincipal.getId().equals(employeeRequest.identityId())
                )
                .map(_ -> employeeRepository.save(employeeMapper.toEmployee(employeeRequest)).getId())
                .orElseThrow(
                        () -> new NotAuthorizedException(NOT_AUTHORIZED_AND_NOT_AUTHENTICATED.getMessage())
                );
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteById(Long employeeId, String authentication) {
        Long identityId = employeeRepository.findIdentityIdByEmployeeId(employeeId);
        return Optional.ofNullable(restClientHandlerInEmployeeService.getIdentityPrincipal(authentication).getBody())
                .filter(
                        identityPrincipal -> Objects.requireNonNull(identityPrincipal).getAuthenticated() &&
                                             identityPrincipal.getId().equals(identityId)
                )
                .map(
                        _ -> employeeRepository.findById(employeeId)
                                .map(
                                        employee -> {
                                            employeeRepository.deleteById(employee.getId());
                                            return String.format(EMPLOYEE_DELETED.getMessage(), employee.getId());
                                        }
                                )
                                .orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND.getMessage()))
                )
                .orElseThrow(() -> new NotAuthorizedException(NOT_AUTHORIZED_AND_NOT_AUTHENTICATED.getMessage()));
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String uploadEmployeeImage(Long employeeId, Long previousMediaId, MultipartFile multipartFile, String authentication) {
        Long identityId = employeeRepository.findIdentityIdByEmployeeId(employeeId);
        IdentityPrincipal idp = restClientHandlerInEmployeeService.getIdentityPrincipal(authentication).getBody();

        return Optional.ofNullable(idp)
                .filter(
                        identityPrincipal -> Objects.requireNonNull(identityPrincipal).getAuthenticated() &&
                                             identityPrincipal.getId().equals(identityId) &&
                                             (identityPrincipal.getAuthority().equals("ROLE_ADMIN") ||
                                              identityPrincipal.getAuthority().equals("ROLE_EMPLOYEE"))
                )
                .map(
                        _ -> {
                            try {
                                Employee employee = employeeRepository.findEmployeeByMediaId(previousMediaId);
                                if (employee != null && employee.getId().equals(employeeId)) {
                                    Media media = mediaRepositoryEmployeeRepository.findById(previousMediaId)
                                            .orElse(null);

                                    if (media != null)
                                        mediaRepositoryEmployeeRepository.deleteById(media.getId());
                                    else
                                        throw new MediaNotFoundException(MEDIA_NOT_FOUND.getMessage());
                                }
                                return mediaRepositoryEmployeeRepository
                                        .save(
                                                Media.builder()
                                                        .name(multipartFile.getOriginalFilename())
                                                        .contentType(multipartFile.getContentType())
                                                        .data(multipartFile.getBytes())
                                                        .build()
                                        );
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .map(
                        media -> {
                            Employee employee = employeeRepository.findById(employeeId).orElse(null);

                            if (employee != null) {
                                employee.setMediaId(media.getId());
                                employeeRepository.save(employee);

                                return EMPLOYEE_IMAGE_SAVED.getMessage();

                            } else
                                throw new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND.getMessage());
                        }
                )
                .orElseThrow(
                        () -> new NotAuthorizedException(NOT_AUTHORIZED_AND_NOT_AUTHENTICATED.getMessage())
                );
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteEmployeeImage(Long employeeId, String authentication) {
        Long identityId = employeeRepository.findIdentityIdByEmployeeId(employeeId);
        IdentityPrincipal idp = restClientHandlerInEmployeeService.getIdentityPrincipal(authentication).getBody();

        return Optional.ofNullable(idp)
                .filter(
                        identityPrincipal -> Objects.requireNonNull(identityPrincipal).getAuthenticated() &&
                                             identityPrincipal.getId().equals(identityId) &&
                                             (identityPrincipal.getAuthority().equals("ROLE_ADMIN") ||
                                              identityPrincipal.getAuthority().equals("ROLE_EMPLOYEE"))
                )
                .flatMap(_ -> employeeRepository.findById(employeeId))
                .map(
                        employee -> {
                            Long mediaId = employee.getMediaId();
                            employee.setMediaId(null);
                            employeeRepository.save(employee);
                            mediaRepositoryEmployeeRepository.deleteById(mediaId);
                            return EMPLOYEE_IMAGE_DELETED.getMessage();
                        }
                )
                .orElseThrow(() -> new NotAuthorizedException(NOT_AUTHORIZED_AND_NOT_AUTHENTICATED.getMessage()));
    }
}
