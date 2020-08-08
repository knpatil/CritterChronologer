package com.kpatil.critter.dto;

import com.kpatil.critter.entity.Employee;
import com.kpatil.critter.constants.EmployeeSkill;

import java.time.DayOfWeek;
import java.util.Set;

/**
 * Represents the form that employee request and response data takes. Does not map
 * to the database directly.
 */
public class EmployeeDTO {
    private long id;
    private String name;
    private Set<EmployeeSkill> skills;
    private Set<DayOfWeek> daysAvailable;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public static EmployeeDTO build(Employee e) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(e.getId());
        employeeDTO.setName(e.getName());
        employeeDTO.setSkills(e.getSkills());
        employeeDTO.setDaysAvailable(e.getDaysAvailable());
        return employeeDTO;
    }
}
