package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RetiredEmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();

    public RetiredEmployeeRepository() {
        employees.add(new Employee(1, "Bert", 25, 100,"Male"));
        employees.add(new Employee(2, "Kyle", 25, 100, "Male"));
    }

    public List<Employee> getAll(){
        return employees;
    }
}
