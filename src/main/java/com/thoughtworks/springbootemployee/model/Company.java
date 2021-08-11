package com.thoughtworks.springbootemployee.model;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private Integer id;
    private String name;
    private List<Employee> employees = new ArrayList<>();

    public Company(Integer id, String name, List<Employee> employees){
        this.id = id;
        this.name = name;
        this.employees = employees;
    }

    public Company() {

    }

    public Integer getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
