package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CompaniesTest {

    @InjectMocks
    CompanyService service;

    @Mock
    CompanyRepository companyRepository;

    private List<Company> expectedCompanies = new ArrayList<>();

    @BeforeEach
    public void setup() {
        expectedCompanies.add(new Company(1,
                "BertCompany",
                Arrays.asList(new Employee(1, "Bert", 25, "Male", 100),
                        new Employee(2, "Kyle", 25, "Male", 100))));
        expectedCompanies.add(new Company(2,
                "KyleCompany",
                Arrays.asList(new Employee(1, "Bert2", 52, "Female", 1000),
                        new Employee(2, "Kyle2", 52, "Male", 1000))));
    }

    @Test
    void should_return_all_companies_when_getAllCompanies_given_all_companies() {
        given(companyRepository.getAll()).willReturn(expectedCompanies);
        List<Company> outputCompanies = service.getAllCompanies();
        assertEquals(outputCompanies, expectedCompanies);
    }

    @Test
    void should_return_specific_company_when_findById_given_company_id() {
        given(companyRepository.getAll()).willReturn(expectedCompanies);
        Company outputCompany = service.findById(1);
        assertEquals(expectedCompanies.get(0), outputCompany);
    }

    @Test
    void should_return_all_employees_in_company_when_getAllEmployeesInCompany_given_company_id() {
        given(companyRepository.getAll()).willReturn(expectedCompanies);
        List<Employee> outputEmployees = service.getAllEmployeesInCompany(1);
        assertEquals(outputEmployees, expectedCompanies.get(0).getEmployees());
    }

    @Test
    void should_return_five_elements_only_in_a_list_when_getListByPagination_given_pageSize_is_five_and_pageIndex_is_one() {
        given(companyRepository.getAll()).willReturn(Arrays.asList(new Company(),new Company(),new Company(),new Company(),new Company(),new Company()
                ,new Company(),new Company(),new Company(),new Company()));
        int expectedCount = 5;
        int outputCount = service.getListByPagination(1, 5).size();
        assertEquals(outputCount, expectedCount);
    }

    @Test
    void should_create_company_and_add_to_list_when_create_given_company_information() {
        List<Company> companies = new ArrayList<>();
        given(companyRepository.getAll()).willReturn(companies);
        Company createCompanyDetails = new Company(){{
            setName("TestCompany");
            setEmployees(Arrays.asList(new Employee(), new Employee()));
        }};
        service.create(createCompanyDetails);
        assertEquals(1, companies.size());
        assertEquals(companies.get(0).getId(), 1);
        assertEquals(createCompanyDetails.getName(), companies.get(0).getName());
        assertEquals(2, companies.get(0).getEmployees().size());
    }

    @Test
    void should_update_company_when_update_given_company_information_and_company_id() {
        given(companyRepository.getAll()).willReturn(expectedCompanies);
        Employee testEmployee = new Employee();
        Company updateCompanyDetails = new Company(){{
            setName("Bertwo");
            setEmployees(Arrays.asList(testEmployee,testEmployee));
        }};
        Company outputCompanyAfterUpdate = service.update(1, updateCompanyDetails);
        assertEquals(expectedCompanies.get(0).getName(), updateCompanyDetails.getName());
        assertEquals(testEmployee, updateCompanyDetails.getEmployees().get(0));
        assertEquals(outputCompanyAfterUpdate.getName(), updateCompanyDetails.getName());
        assertEquals(outputCompanyAfterUpdate.getEmployees().get(0),updateCompanyDetails.getEmployees().get(0));
    }

}
