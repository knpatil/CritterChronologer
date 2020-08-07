package com.kpatil.critter.service;

import com.kpatil.critter.entity.Customer;
import com.kpatil.critter.entity.Employee;
import com.kpatil.critter.entity.Pet;
import com.kpatil.critter.entity.Schedule;
import com.kpatil.critter.repository.CustomerRepository;
import com.kpatil.critter.repository.EmployeeRepository;
import com.kpatil.critter.repository.PetRepository;
import com.kpatil.critter.repository.ScheduleRepository;
import com.kpatil.critter.schedule.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

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
        List<Employee> employees =
                this.employeeRepository.findAllById(scheduleDTO.getEmployeeIds());

        List<Pet> pets =
                this.petRepository.findAllById(scheduleDTO.getPetIds());

        Schedule schedule = Schedule.build(scheduleDTO);
        schedule.setEmployees(employees);
        schedule.setPets(pets);

        schedule = this.scheduleRepository.save(schedule);

        return ScheduleDTO.build(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return this.scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(long petId) {
        Pet pet = this.petRepository.getOne(petId);
        return this.scheduleRepository.findByPets(pet);
    }

    public List<Schedule> getScheduleForEmployee(long employeeId) {
        Employee employee = this.employeeRepository.getOne(employeeId);
        return this.scheduleRepository.findByEmployees(employee);
    }

    public List<Schedule> getScheduleForCustomer(long customerId) {
        Customer customer = this.customerRepository.getOne(customerId);
        List<Pet> pets = customer.getPets();
        return this.scheduleRepository.findByPetsIn(pets);
    }
}
