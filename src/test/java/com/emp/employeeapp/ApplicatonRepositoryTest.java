package com.emp.employeeapp;

import com.emp.employeeapp.models.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ApplicatonRepositoryTest {

    @Autowired
    private com.emp.employeeapp.repository.AddressRepository repoAddress;

    @Autowired
    private com.emp.employeeapp.repository.DepartamentRepository repoDepartament;

    @Autowired
    private com.emp.employeeapp.repository.MissionRepository repoMission;

    @Autowired
    private com.emp.employeeapp.repository.EmployeeRepository repoEmployee;

    @Test
    public void testAddAddress() {

        Address address = new Address();
        address.setStreetName("22th Acacia Avenue");
        address.setHouseNumber("110");
        address.setZipCode("11111999");

        Address saveAddress = repoAddress.save(address);

        Assertions.assertThat(saveAddress).isNotNull();
        Assertions.assertThat(saveAddress.getId()).isGreaterThan(0);

    }

    @Test
    public void testAddDepartament() {

        Departament departament = new Departament();
        departament.setName("Production Manager");

        Departament saveDepartament = repoDepartament.save(departament);

        Assertions.assertThat(saveDepartament).isNotNull();
        Assertions.assertThat(saveDepartament.getId()).isGreaterThan(0);

    }

    @Test
    public void testAddMission() {

        Mission mission = new Mission();
        mission.setName ("Manage the production process");
        mission.setDuration(6);

        Mission saveMission = repoMission.save(mission);

        Assertions.assertThat(saveMission).isNotNull();
        Assertions.assertThat(saveMission.getId()).isGreaterThan(0);

        Mission mission2 = new Mission();
        mission2.setName ("Plan the use of supplies in the production process");
        mission2.setDuration(4);

        Mission saveMission2 = repoMission.save(mission2);

        Assertions.assertThat(saveMission2).isNotNull();
        Assertions.assertThat(saveMission2.getId()).isGreaterThan(0);

    }

    @Test
    public void testAddEmployee() {

        Employee employee = new Employee();
        employee.setBirthDate(LocalDate.of(2026, 1, 27));
        employee.setFirstName("Paul");
        employee.setLastName("Smith");
        employee.setIdentifier("1234567890");
        employee.setRole(EmployeeRole.MANAGER);

        Employee saveEmployee = repoEmployee.save(employee);

        Assertions.assertThat(saveEmployee).isNotNull();
        Assertions.assertThat(saveEmployee.getId()).isGreaterThan(0);

    }

    @Test
    public void testAddOperations() {

        // Add address to employee
        Integer employeeId = 1;
        Optional<Employee> optionalEmployee = repoEmployee.findById(employeeId);
        Employee employee = optionalEmployee.get();

        Integer addressId = 1;
        Optional<Address> optionalAddress = repoAddress.findById(addressId);
        Address address = optionalAddress.get();

        address.setEmployee(employee);
        repoAddress.save(address);

        employee.setAddress(address);
        repoEmployee.save(employee);


        // Add departament to employee
        Integer departamentId = 1;
        Optional<Departament> optionalDepartament = repoDepartament.findById(departamentId);
        Departament departament = optionalDepartament.get();

        employee.setDepartament(departament);
        repoEmployee.save(employee);


        // Add mission to employee
        Integer missionId = 1;
        Optional<Mission> optionalMission = repoMission.findById(missionId);
        Mission mission = optionalMission.get();

        missionId = 2;
        optionalMission = repoMission.findById(missionId);
        Mission mission2 = optionalMission.get();

        List<Mission> missions = new ArrayList<Mission>();
        missions.add(mission);
        missions.add(mission2);

        employee.setMissions(missions);
        repoEmployee.save(employee);
    }

}
