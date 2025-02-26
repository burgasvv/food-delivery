package org.burgas.employeeservice.repository;

import org.burgas.employeeservice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(
            nativeQuery = true,
            value = """
                    select i.id from identity i join employee e on i.id = e.identity_id
                                        where e.id = ?1
                    """
    )
    Long findIdentityIdByEmployeeId(Long employeeId);

    List<Employee> findEmployeesByDepartmentId(Long departmentId);

    Employee findEmployeeByMediaId(Long mediaId);
}
