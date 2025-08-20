package com.booleanuk.controllers;

import com.booleanuk.models.Department;
import com.booleanuk.models.Employee;
import com.booleanuk.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public List<Department> getAllDepartments() {
        return this.departmentRepository.findAll();
    }

    @GetMapping("{id}")
    public Department getOneDepartment(@PathVariable int id) {
        return this.departmentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!!!!!!"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department createDepartment(@RequestBody Department department) {
        Department newDepartment = this.departmentRepository.save(department);
        newDepartment.setEmployees(new ArrayList<>());
        return newDepartment;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department updateDepartment(@PathVariable int id, @RequestBody Department department) {
        Department departmentToUpdate = this.departmentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!!!!!!"));
        departmentToUpdate.setName(department.getName());
        departmentToUpdate.setLocation(department.getLocation());
        return this.departmentRepository.save(departmentToUpdate);
    }

    @DeleteMapping("{id}")
    public Department deleteDepartment(@PathVariable int id) {
        Department departmentToDelete = this.departmentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!!!!!!"));
        this.departmentRepository.delete(departmentToDelete);
        departmentToDelete.setEmployees(new ArrayList<>());
        return departmentToDelete;
    }


}
