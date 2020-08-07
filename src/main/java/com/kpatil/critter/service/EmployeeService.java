package com.kpatil.critter.service;

import com.kpatil.critter.entity.Employee;
import com.kpatil.critter.repository.EmployeeRepository;
import com.kpatil.critter.user.EmployeeSkill;
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

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    public Employee getEmployee(long employeeId) {
        return this.employeeRepository.getOne(employeeId);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee emp = this.employeeRepository.getOne(employeeId);
        emp.setDaysAvailable(daysAvailable);
        this.employeeRepository.save(emp);
    }

    public List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, LocalDate date) {
        return this.employeeRepository.findByDaysAvailable(date.getDayOfWeek()).stream()
                .filter(e -> e.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }
}
