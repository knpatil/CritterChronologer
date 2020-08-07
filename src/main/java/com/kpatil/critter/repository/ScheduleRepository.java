package com.kpatil.critter.repository;

import com.kpatil.critter.entity.Employee;
import com.kpatil.critter.entity.Pet;
import com.kpatil.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByPets(Pet pet);

    List<Schedule> findByEmployees(Employee employee);

    List<Schedule> findByPetsIn(List<Pet> pets);
}
