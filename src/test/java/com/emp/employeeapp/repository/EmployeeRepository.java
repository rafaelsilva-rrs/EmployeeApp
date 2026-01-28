package com.emp.employeeapp.repository;

import com.emp.employeeapp.models.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

}
