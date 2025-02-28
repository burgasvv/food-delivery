package org.burgas.departmentservice.schedule;

import org.burgas.departmentservice.repository.DepartmentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@Component
public class DepartmentScheduleTime {

    private final DepartmentRepository departmentRepository;

    public DepartmentScheduleTime(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 50)
    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public void checkScheduleTime() {
        departmentRepository.findAll()
                .forEach(
                        department -> {
                            LocalTime time = LocalTime.now();
                            LocalTime opensAt = department.getOpensAt();
                            LocalTime closesAt = department.getClosesAt();

                            if (time.isAfter(opensAt) && time.isBefore(closesAt)) {
                                department.setOpened(true);
                                departmentRepository.save(department);

                            } else {
                                department.setOpened(false);
                                departmentRepository.save(department);
                            }
                        }
                );
    }
}
