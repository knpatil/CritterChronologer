package com.kpatil.critter.service;

import com.kpatil.critter.constants.EmployeeSkill;
import com.kpatil.critter.entity.Employee;
import com.kpatil.critter.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        logger.info("Saving employee " + employee.getName());
        return this.employeeRepository.save(employee);
    }

    public Employee getEmployee(long employeeId) {
        logger.info("Finding employee by id " + employeeId);
        return this.employeeRepository.getOne(employeeId);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        logger.info("Setting availability for employee with id " + employeeId);
        Employee emp = this.employeeRepository.getOne(employeeId);
        logger.info("Found employee " + emp.getName());
        emp.setDaysAvailable(daysAvailable);
        this.employeeRepository.save(emp);
    }

    public List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, LocalDate date) {
        logger.info("Looking for employees for service skills : " + skills);
        return this.employeeRepository.findByDaysAvailable(date.getDayOfWeek()).stream()
                .filter(e -> e.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }
}
