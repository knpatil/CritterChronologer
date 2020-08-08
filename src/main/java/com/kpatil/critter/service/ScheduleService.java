package com.kpatil.critter.service;

import com.kpatil.critter.entity.Customer;
import com.kpatil.critter.entity.Employee;
import com.kpatil.critter.entity.Pet;
import com.kpatil.critter.entity.Schedule;
import com.kpatil.critter.repository.CustomerRepository;
import com.kpatil.critter.repository.EmployeeRepository;
import com.kpatil.critter.repository.PetRepository;
import com.kpatil.critter.repository.ScheduleRepository;
import com.kpatil.critter.dto.ScheduleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    private static final Logger logger =
            LoggerFactory.getLogger(ScheduleService.class);

    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository, PetRepository petRepository) {
        this.scheduleRepository = scheduleRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
    }

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        logger.info("Creating a schedule ...");

        logger.info("Finding employees ...");
        List<Employee> employees =
                this.employeeRepository.findAllById(scheduleDTO.getEmployeeIds());

        logger.info("Finding all pets with ids ...");
        List<Pet> pets =
                this.petRepository.findAllById(scheduleDTO.getPetIds());

        Schedule schedule = Schedule.build(scheduleDTO);
        schedule.setEmployees(employees);
        schedule.setPets(pets);

        logger.info("Saving a schedule ...");
        schedule = this.scheduleRepository.save(schedule);

        logger.info("Schedule created with id " + schedule.getId());
        return ScheduleDTO.build(schedule);
    }

    public List<Schedule> getAllSchedules() {
        logger.info("Finding all schedules ...");
        return this.scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(long petId) {
        logger.info("Finding schedule for a pet " + petId);
        Pet pet = this.petRepository.getOne(petId);
        logger.info("Finding a schedule ...");
        return this.scheduleRepository.findByPets(pet);
    }

    public List<Schedule> getScheduleForEmployee(long employeeId) {
        logger.info("Finding a employee ...");
        Employee employee = this.employeeRepository.getOne(employeeId);
        logger.info("Find a schedule ...");
        return this.scheduleRepository.findByEmployees(employee);
    }

    public List<Schedule> getScheduleForCustomer(long customerId) {
        logger.info("Finding a customer ...");
        Customer customer = this.customerRepository.getOne(customerId);
        logger.info("Finding pets ..");
        List<Pet> pets = customer.getPets();
        logger.info("Finding a schedule ...");
        return this.scheduleRepository.findByPetsIn(pets);
    }
}
