package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class RetiredCompanyRepository {
    private final List<Company> companies = new ArrayList<>();

    public RetiredCompanyRepository() {
        companies.add(new Company(1,
                "BertCompany",
                Arrays.asList(new Employee(1, "Bert", 25, 100, "Male"),
                        new Employee(2, "Kyle", 25, 100, "Male"))));
        companies.add(new Company(2,
                "KyleCompany",
                Arrays.asList(new Employee(1, "Bert2", 52, 1000, "Female"),
                        new Employee(2, "Kyle2", 52, 1000, "Male"))));
    }

    public List<Company> getAll(){
        return companies;
    }
}
