package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exceptions.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CompanyService {
    @Resource
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findById(Integer companyId) {
        return companyRepository.findById(companyId).orElseThrow(CompanyNotFoundException::new);
    }

    public List<Employee> findAllByEmployeeCompanyId(Integer companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(CompanyNotFoundException::new);
        return company.getEmployees();
    }

    public List<Company> getListByPagination(Integer pageIndex, Integer pageSize) {
        return companyRepository.findAll(PageRequest.of(pageIndex - 1, pageSize)).getContent();
    }

    public Company save(Company company) {
        if (company.getEmployees().isEmpty()) {
            return companyRepository.save(company);
        }
        Company toBeCreated = companyRepository.save(company);
        toBeCreated.getEmployees().forEach(employee -> employee.setCompanyId(toBeCreated.getId()));
        return companyRepository.save(toBeCreated);
    }

    public Company update(Integer companyId, Company updateCompanyDetails) {
        Company toBeUpdated = companyRepository.findById(companyId).orElseThrow(CompanyNotFoundException::new);
        updateCompanyInformation(toBeUpdated, updateCompanyDetails);
        return companyRepository.save(toBeUpdated);
    }

    private void updateCompanyInformation(Company company, Company companyUpdate) {
        if (companyUpdate.getCompanyName() != null) {
            company.setCompanyName(companyUpdate.getCompanyName());
        }
        if (!companyUpdate.getEmployees().isEmpty() && companyUpdate.getEmployees() != null) {
            company.setEmployees(companyUpdate.getEmployees());
        }
    }

    public Company delete(Integer companyId) {
        Company toBeDeleted = companyRepository.findById(companyId).orElseThrow(CompanyNotFoundException::new);
        if (!toBeDeleted.getEmployees().isEmpty()) {
            toBeDeleted.getEmployees().forEach(employee -> employee.setCompanyId(null));
        }
        companyRepository.deleteById(companyId);
        return toBeDeleted;
    }
}
