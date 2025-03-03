package org.burgas.employeeservice.repository;

import org.burgas.employeeservice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    delete from media m where m.id = ?1
                    """
    )
    void deleteMediaByIdFromEmployee(Long mediaId);

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    insert into media(name, content_type, data) VALUES (?1, ?2, ?3)
                    """
    )
    Integer insertIntoMediaFromEmployee(String name, String contentType, byte[] data);
}
