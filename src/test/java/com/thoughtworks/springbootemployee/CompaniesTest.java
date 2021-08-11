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

}
