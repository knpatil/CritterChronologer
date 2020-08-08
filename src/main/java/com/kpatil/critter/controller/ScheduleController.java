package com.kpatil.critter.controller;

import com.kpatil.critter.entity.Schedule;
import com.kpatil.critter.dto.ScheduleDTO;
import com.kpatil.critter.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return this.scheduleService.createSchedule(scheduleDTO);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = this.scheduleService.getAllSchedules();
        return schedules.stream()
                .map(ScheduleDTO::build)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules =
                this.scheduleService.getScheduleForPet(petId);
        return schedules.stream()
                .map(ScheduleDTO::build)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules =
                this.scheduleService.getScheduleForEmployee(employeeId);
        return schedules.stream()
                .map(ScheduleDTO::build)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules =
                this.scheduleService.getScheduleForCustomer(customerId);
        return schedules.stream()
                .map(ScheduleDTO::build)
                .collect(Collectors.toList());
    }
}
