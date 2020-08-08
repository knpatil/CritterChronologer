package com.kpatil.critter.controller;

import com.kpatil.critter.entity.Customer;
import com.kpatil.critter.entity.Employee;
import com.kpatil.critter.service.CustomerService;
import com.kpatil.critter.service.EmployeeService;
import com.kpatil.critter.dto.CustomerDTO;
import com.kpatil.critter.dto.EmployeeDTO;
import com.kpatil.critter.dto.EmployeeRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger =
            LoggerFactory.getLogger(UserController.class);

    private final CustomerService customerService;
    private final EmployeeService employeeService;

    @Autowired
    public UserController(CustomerService customerService, EmployeeService employeeService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        logger.info("Received request to create a new customer ...");
        Customer customer = Customer.build(customerDTO);
        customer = customerService.saveCustomer(customer);
        logger.info("Customer created with id = " + customer.getId());
        return CustomerDTO.build(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        logger.info("Received request to get all customers ...");
        List<Customer> customerList = customerService.getAllCustomers();
        logger.info(String.format("Found %d customers.", customerList.size()));
        return customerList.stream()
                .map(CustomerDTO::build)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        logger.info("Received request to find a owner for pet " + petId);
        Customer customer = customerService.getOwnerByPet(petId);
        logger.info("Found owner " + customer.getName());
        return CustomerDTO.build(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        logger.info("Received request to create a new Employee ...");
        Employee employee = Employee.build(employeeDTO);
        employee = employeeService.saveEmployee(employee);
        logger.info("Employee created with id " + employee.getId());
        return EmployeeDTO.build(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        logger.info("Received request to find an employee for id " + employeeId);
        Employee employee = employeeService.getEmployee(employeeId);
        logger.info("Found employee " + employee.getName());
        return EmployeeDTO.build(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        logger.info("Received request to set availability for " + daysAvailable.toString() + " for employee " + employeeId);
        this.employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        logger.info("Received request to find employees for service ...");
        List<Employee> employees =
                this.employeeService.findEmployeesForService(employeeDTO.getSkills(), employeeDTO.getDate());
        logger.info(String.format("Found %d employees.", employees.size()));
        return employees.stream()
                .map(EmployeeDTO::build)
                .collect(Collectors.toList());
    }

}
