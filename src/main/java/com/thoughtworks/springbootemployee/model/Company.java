package com.thoughtworks.springbootemployee.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPANY_SEQ")
    @SequenceGenerator(sequenceName = "COMPANY_SEQ", allocationSize = 1, name = "COMPANY_SEQ")
    private Integer id;
    private String companyName;
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "companyId", orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();

    public Company(Integer id, String companyName, List<Employee> employees) {
        this.id = id;
        this.companyName = companyName;
        this.employees = employees;
        this.employees.forEach(employee -> employee.setCompanyId(this.id));
    }

    public Company() {
    }

    public Integer getId(){
        return id;
    }

    public String getCompanyName(){
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
        this.employees.forEach(employee -> employee.setCompanyId(this.id));
    }
}
