package io.neca.springbootbackend.controller;

import io.neca.springbootbackend.exception.EmployeeNotFoundException;
import io.neca.springbootbackend.model.Employee;
import io.neca.springbootbackend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    // create employee
    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return service.create(employee);
    }

    // get all employees
    @GetMapping("/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployees() {
        return service.getAll();
    }

    // get employee by id
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable int id) {
        Employee employee = service.getOne(id)
                .orElseThrow(() -> new EmployeeNotFoundException("employee with id " + id + " can not be found"));

        return ResponseEntity.ok(employee);
    }

    // update employee
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable int id) {
        Employee employeeToModify = service.getOne(id)
                .orElseThrow(() -> new EmployeeNotFoundException("employee with id " + id + " can not be found"));
        employeeToModify.setFirstName(employee.getFirstName());
        employeeToModify.setLastName(employee.getLastName());
        employeeToModify.setEmail(employee.getEmail());
        Employee newEmployee = service.create(employeeToModify);

        return ResponseEntity.ok(newEmployee);
    }

    // delete employee
    @DeleteMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable int id) {
        service.delete(id);
    }

}
