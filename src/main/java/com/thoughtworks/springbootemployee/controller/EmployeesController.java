package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeesController{
    private final List<Employee> employees = new ArrayList<>();

    public EmployeesController() {
        employees.add(new Employee(1, "Bert", 25, "Male", 100));
        employees.add(new Employee(2, "Kyle", 25, "Male", 100));
    }

    @GetMapping()
    public List<Employee> getAllEmployees(){
        return employees;
    }

    @GetMapping(path = "/{employeeId}")
    public Employee findById(@PathVariable Integer employeeId){
        return employees.stream()
                .filter((employee -> employee.getId().equals(employeeId))).findFirst().orElse(null);
    }

    @GetMapping(params = "gender")
    public List<Employee> findByGender(@RequestParam("gender") String gender) {
        return employees.stream()
                .filter((employee -> employee.getGender().equals(gender)))
                .collect(Collectors.toList());
    }
}
