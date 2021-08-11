package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class CompanyRepository {
    private final List<Company> companies = new ArrayList<>();

    public CompanyRepository() {
        companies.add(new Company(1,
                "BertCompany",
                Arrays.asList(new Employee(1, "Bert", 25, "Male", 100),
                        new Employee(2, "Kyle", 25, "Male", 100))));
        companies.add(new Company(2,
                "KyleCompany",
                Arrays.asList(new Employee(1, "Bert2", 52, "Female", 1000),
                        new Employee(2, "Kyle2", 52, "Male", 1000))));
    }

    public List<Company> getAll(){
        return companies;
    }
}
