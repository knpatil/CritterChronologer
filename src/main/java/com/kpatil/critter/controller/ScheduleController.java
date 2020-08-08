package com.kpatil.critter.controller;

import com.kpatil.critter.dto.ScheduleDTO;
import com.kpatil.critter.entity.Schedule;
import com.kpatil.critter.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        logger.info("Received request to create a schedule ...");
        return this.scheduleService.createSchedule(scheduleDTO);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        logger.info("Received request to get all schedules ...");
        List<Schedule> schedules = this.scheduleService.getAllSchedules();
        logger.info(String.format("Found %d schedules.", schedules.size()));
        return schedules.stream()
                .map(ScheduleDTO::build)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        logger.info("Received request to get a schedule for pet : " + petId);
        List<Schedule> schedules =
                this.scheduleService.getScheduleForPet(petId);
        logger.info(String.format("Found %d schedules.", schedules.size()));
        return schedules.stream()
                .map(ScheduleDTO::build)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        logger.info("Received request to get a schedule for employee ...");
        List<Schedule> schedules =
                this.scheduleService.getScheduleForEmployee(employeeId);
        logger.info(String.format("Found %d schedules.", schedules.size()));
        return schedules.stream()
                .map(ScheduleDTO::build)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        logger.info("Received request to get a schedule for a customer ...");
        List<Schedule> schedules =
                this.scheduleService.getScheduleForCustomer(customerId);
        logger.info(String.format("Found %d schedules.", schedules.size()));
        return schedules.stream()
                .map(ScheduleDTO::build)
                .collect(Collectors.toList());
    }
}
