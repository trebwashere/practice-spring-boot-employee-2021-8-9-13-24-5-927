package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.exceptions.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exceptions.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CompaniesIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CompanyService testService;

    private Company company;

    @BeforeEach
    public void setup() {
        List<Employee> employeeList1 = new ArrayList<>();
        employeeList1.add(new Employee(null, "Bert", 25, 100, "Male"));
        employeeList1.add(new Employee(null, "Kyle", 25, 100, "Male"));
        List<Employee> employeeList2 = new ArrayList<>();
        employeeList2.add(new Employee(null, "Bert2", 30, 1000, "Female"));
        employeeList2.add(new Employee(null, "Kyle2", 30, 1000, "Female"));
        company = (new Company(1, "BertCompany", employeeList1));
        testService.save(company);
        company = (new Company(2, "BertCompany2", employeeList2));
        testService.save(company);
    }

    @Test
    void should_return_all_companies_when_findAll_given_all_companies() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").exists())
                .andExpect(jsonPath("$.[*].id").isNotEmpty());
    }

    @Test
    void should_delete_company_when_delete_given_company_id_and_all_companies() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/companies/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("BertCompany"))
                .andExpect(jsonPath("$.employees[*]", hasSize(2)))
                .andExpect(jsonPath("$.employees[0].name").value("Bert"))
                .andExpect(jsonPath("$.employees[1].name").value("Kyle"));

    }

    @Test
    void should_update_company_when_update_given_company_information_and_company_id() throws Exception {
        String companyStr = "{\n" +
                "    \"companyName\": \"Test1Company\",\n" +
                "    \"employees\": [\n" +
                "        {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"Bert111\",\n" +
                "            \"age\": 52,\n" +
                "            \"gender\": \"Female\",\n" +
                "            \"salary\": 1000,\n" +
                "            \"companyId\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 4,\n" +
                "            \"name\": \"Kyle311\",\n" +
                "            \"age\": 52,\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"salary\": 1000,\n" +
                "            \"companyId\": 2\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.put("/companies/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(companyStr))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("Test1Company"))
                .andExpect(jsonPath("$.employees[*]", hasSize(2)))
                .andExpect(jsonPath("$.employees[0].name").value("Bert111"))
                .andExpect(jsonPath("$.employees[1].name").value("Kyle311"));
    }

    @Test
    void should_create_company_and_add_to_list_when_create_given_company_information() throws Exception {
        String companyStr = "\n" +
                "{\n" +
                "    \"companyName\": \"Test3Company\",\n" +
                "    \"employees\": []\n" +
                "}";
        testService.save(company);

        mockMvc.perform(MockMvcRequestBuilders.post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(companyStr))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyName").value("Test3Company"));
    }

    @Test
    void should_return_list_of_companies_based_on_pageIndex_and_pageSize() throws Exception {
        String pageIndex = "1";
        String pageSize = "2";
        mockMvc.perform(MockMvcRequestBuilders.get("/companies")
                .param("pageIndex", pageIndex).param("pageSize", pageSize))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)));
    }

    @Test
    void should_return_company_when_findById_given_companyId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("BertCompany"))
                .andExpect(jsonPath("$.employees", hasSize(2)));
    }

    @Test
    void should_return_CompanyNotFoundException_when_findById_given_invalid_companyId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}", 3))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CompanyNotFoundException))
                .andExpect(jsonPath("$.message").value("Company does not exist!"));
    }
}
