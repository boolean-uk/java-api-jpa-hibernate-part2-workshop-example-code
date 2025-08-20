package com.booleanuk.controllers;

import com.booleanuk.models.Department;
import com.booleanuk.models.Employee;
import com.booleanuk.repositories.DepartmentRepository;
import com.booleanuk.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return this.employeeRepository.findAll();
    }

    @GetMapping("{id}")
    public Employee getOneEmployee(@PathVariable int id) {
        return this.employeeRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!!!!!!"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        Employee newEmployee = this.employeeRepository.save(employee);
        Department department = this.departmentRepository.findById(employee.getDepartment().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found!!!!!!"));
        newEmployee.setDepartment(department);
        return newEmployee;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        Employee employeeToUpdate = this.employeeRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!!!!!!"));
        employeeToUpdate.setFirstName(employee.getFirstName());
        employeeToUpdate.setLastName(employee.getLastName());
        // Find the department which matches the one in the employee that was passed in and set it
        Department departmentOfEmployee = this.departmentRepository.findById(employee.getDepartment().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found!!!!!!"));
        employeeToUpdate.setDepartment(departmentOfEmployee);
        return this.employeeRepository.save(employeeToUpdate);
    }

    @DeleteMapping("{id}")
    public Employee deleteEmployee(@PathVariable int id) {
        Employee employeeToDelete = this.employeeRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!!!!!!"));
        this.employeeRepository.delete(employeeToDelete);
//        // Set the employee's department to null or we will get errors
//        employeeToDelete.setDepartment(null);
        return employeeToDelete;
    }

}
