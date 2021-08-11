package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Company> getListByPagination(Integer pageIndex, Integer pageSize) {
        long paginationFormula =  (long) (pageIndex - 1) * pageSize;
        return companyRepository.getAll().stream()
                .skip(paginationFormula)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company create(Company company) {
        Company companyToBeAdded = new Company(companyRepository.getAll().size() + 1, company.getName(), company.getEmployees());
        companyRepository.getAll().add(companyToBeAdded);

        return companyToBeAdded;
    }


    public Company update(Integer companyId, Company updateCompanyDetails) {
        return companyRepository.getAll().stream()
                .filter(company -> company.getId().equals(companyId))
                .findFirst()
                .map(company -> updateCompanyInformation(company, updateCompanyDetails))
                .orElse(null);
    }

    private Company updateCompanyInformation(Company company, Company companyUpdate) {
        if (companyUpdate.getName() != null) {
            company.setName(companyUpdate.getName());
        }
        if (!companyUpdate.getEmployees().isEmpty() && companyUpdate.getEmployees() != null) {
            company.setEmployees(companyUpdate.getEmployees());
        }
        return company;
    }
}
