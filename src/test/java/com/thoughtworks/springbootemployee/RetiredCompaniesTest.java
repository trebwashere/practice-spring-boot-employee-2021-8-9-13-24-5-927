package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.RetiredCompanyRepository;
import com.thoughtworks.springbootemployee.service.RetiredCompanyService;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class RetiredCompaniesTest {

    @InjectMocks
    RetiredCompanyService service;

    @Mock
    RetiredCompanyRepository retiredCompanyRepository;

    private List<Company> expectedCompanies = new ArrayList<>();

    @BeforeEach
    public void setup() {
        expectedCompanies.add(new Company(1,
                "BertCompany",
                Arrays.asList(new Employee(1, "Bert", 25, 100, "Male"),
                        new Employee(2, "Kyle", 25, 100, "Male"))));
        expectedCompanies.add(new Company(2,
                "KyleCompany",
                Arrays.asList(new Employee(1, "Bert2", 52, 1000, "Female"),
                        new Employee(2, "Kyle2", 52, 1000, "Male"))));
    }

    @Test
    void should_return_all_companies_when_getAllCompanies_given_all_companies() {
        given(retiredCompanyRepository.getAll()).willReturn(expectedCompanies);
        List<Company> outputCompanies = service.getAllCompanies();
        assertEquals(outputCompanies, expectedCompanies);
    }

    @Test
    void should_return_specific_company_when_findById_given_company_id() {
        given(retiredCompanyRepository.getAll()).willReturn(expectedCompanies);
        Company outputCompany = service.findById(1);
        assertEquals(expectedCompanies.get(0), outputCompany);
    }

    @Test
    void should_return_all_employees_in_company_when_getAllEmployeesInCompany_given_company_id() {
        given(retiredCompanyRepository.getAll()).willReturn(expectedCompanies);
        List<Employee> outputEmployees = service.getAllEmployeesInCompany(1);
        assertEquals(outputEmployees, expectedCompanies.get(0).getEmployees());
    }

    @Test
    void should_return_five_elements_only_in_a_list_when_getListByPagination_given_pageSize_is_five_and_pageIndex_is_one() {
        given(retiredCompanyRepository.getAll()).willReturn(Arrays.asList(new Company(), new Company(), new Company(), new Company(), new Company(), new Company()
                , new Company(), new Company(), new Company(), new Company()));
        int expectedCount = 5;
        int outputCount = service.getListByPagination(1, 5).size();
        assertEquals(outputCount, expectedCount);
    }

    @Test
    void should_create_company_and_add_to_list_when_create_given_company_information() {
        List<Company> companies = new ArrayList<>();
        given(retiredCompanyRepository.getAll()).willReturn(companies);
        Company createCompanyDetails = new Company() {{
            setCompanyName("TestCompany");
            setEmployees(Arrays.asList(new Employee(), new Employee()));
        }};
        service.create(createCompanyDetails);
        assertEquals(1, companies.size());
        assertEquals(companies.get(0).getId(), 1);
        assertEquals(createCompanyDetails.getCompanyName(), companies.get(0).getCompanyName());
        assertEquals(2, companies.get(0).getEmployees().size());
    }

    @Test
    void should_update_company_when_update_given_company_information_and_company_id() {
        given(retiredCompanyRepository.getAll()).willReturn(expectedCompanies);
        Employee testEmployee = new Employee();
        Company updateCompanyDetails = new Company() {{
            setCompanyName("Bertwo");
            setEmployees(Arrays.asList(testEmployee, testEmployee));
        }};
        Company outputCompanyAfterUpdate = service.update(1, updateCompanyDetails);
        assertEquals(expectedCompanies.get(0).getCompanyName(), updateCompanyDetails.getCompanyName());
        assertEquals(testEmployee, updateCompanyDetails.getEmployees().get(0));
        assertEquals(outputCompanyAfterUpdate.getCompanyName(), updateCompanyDetails.getCompanyName());
        assertEquals(outputCompanyAfterUpdate.getEmployees().get(0), updateCompanyDetails.getEmployees().get(0));
    }

    @Test
    void should_delete_company_when_delete_given_company_id_and_all_companies() {
        List<Company> expectedCompanies = new ArrayList<>();
        expectedCompanies.add(new Company(1,
                "BertCompany",
                Arrays.asList(new Employee(1, "Bert", 25, 100, "Male"),
                        new Employee(2, "Kyle", 25, 100, "Male"))));
        expectedCompanies.add(new Company(2,
                "KyleCompany",
                Arrays.asList(new Employee(1, "Bert2", 52, 1000, "Female"),
                        new Employee(2, "Kyle2", 52, 1000, "Male"))));
        given(retiredCompanyRepository.getAll()).willReturn(expectedCompanies);
        Company expectedCompanyToBeDeleted = service.delete(1);
        assertNotNull(expectedCompanyToBeDeleted);
        assertEquals(1, expectedCompanies.size());
        assertEquals(1, expectedCompanyToBeDeleted.getId());
    }

}
