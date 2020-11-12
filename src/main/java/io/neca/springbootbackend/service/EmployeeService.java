package io.neca.springbootbackend.service;

import io.neca.springbootbackend.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> getAll();
    Optional<Employee> getOne(int id);
    Employee create(Employee employee);
    void delete(int id);

}
