package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();

    public List<Employee> getAll(){
        return employees;
    }

    public Employee add(Employee employee){
        employees.add(employee);
        return employee;
    }
}
