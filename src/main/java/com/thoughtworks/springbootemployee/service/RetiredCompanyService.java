package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.RetiredCompanyRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RetiredCompanyService {
    @Resource
    private RetiredCompanyRepository retiredCompanyRepository;

    public RetiredCompanyService(RetiredCompanyRepository retiredCompanyRepository) {
        this.retiredCompanyRepository = retiredCompanyRepository;
    }

    public List<Company> getAllCompanies() {
        return retiredCompanyRepository.getAll();
    }

    public Company findById(Integer companyId) {
        return retiredCompanyRepository.getAll().stream()
                .filter(company -> company.getId().equals(companyId))
                .findAny()
                .orElse(null);
    }

    public List<Employee> getAllEmployeesInCompany(Integer companyId) {
        return findById(companyId).getEmployees();
    }

    public List<Company> getListByPagination(Integer pageIndex, Integer pageSize) {
        long paginationFormula = (long) (pageIndex - 1) * pageSize;
        return retiredCompanyRepository.getAll().stream()
                .skip(paginationFormula)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company create(Company company) {
        Company companyToBeAdded = new Company(retiredCompanyRepository.getAll().size() + 1, company.getCompanyName(), company.getEmployees());
        retiredCompanyRepository.getAll().add(companyToBeAdded);

        return companyToBeAdded;
    }


    public Company update(Integer companyId, Company updateCompanyDetails) {
        return retiredCompanyRepository.getAll().stream()
                .filter(company -> company.getId().equals(companyId))
                .findFirst()
                .map(company -> updateCompanyInformation(company, updateCompanyDetails))
                .orElse(null);
    }

    private Company updateCompanyInformation(Company company, Company companyUpdate) {
        if (companyUpdate.getCompanyName() != null) {
            company.setCompanyName(companyUpdate.getCompanyName());
        }
        if (!companyUpdate.getEmployees().isEmpty() && companyUpdate.getEmployees() != null) {
            company.setEmployees(companyUpdate.getEmployees());
        }
        return company;
    }

    public Company delete(Integer companyId) {
        Company toBeRemoved = retiredCompanyRepository.getAll().stream()
                .filter(company -> company.getId()
                        .equals(companyId))
                .findFirst().orElse(null);
        if (toBeRemoved != null) {
            retiredCompanyRepository.getAll().remove(toBeRemoved);
            return toBeRemoved;
        }
        return null;
    }
}
