package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CompanyService {
    @Resource
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompanies(){
        return companyRepository.getAll();
    }

    public Company findById(Integer companyId) {
        return companyRepository.getAll().stream()
                .filter(company -> company.getId().equals(companyId))
                .findAny()
                .orElse(null);
    }

    public List<Employee> getAllEmployeesInCompany(Integer companyId) {
        return findById(companyId).getEmployees();
    }
}
