package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.exceptions.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EmployeesIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EmployeeService service;

    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = new Employee(1, "Bert", 25, 100, "Male");
        service.save(employee);
        employee = new Employee(2, "Bert2", 25, 100, "Female");
        service.save(employee);
    }

    @Test
    void should_return_all_employees_when_getAllEmployees_given_all_employees() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").exists())
                .andExpect(jsonPath("$.[*].id").isNotEmpty());
    }

    @Test
    void should_delete_employee_when_delete_given_employee_id_and_all_employees() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bert"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(100));
    }

    @Test
    void should_update_employee_when_update_given_employee_information_and_employee_id() throws Exception {
        String employeeStr = "{\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"BertUpdate\",\n" +
                "        \"age\": 99,\n" +
                "        \"salary\": 1000,\n" +
                "        \"gender\": \"Female\"\n" +
                "    }";

        mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(employeeStr))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("BertUpdate"))
                .andExpect(jsonPath("$.age").value(99))
                .andExpect(jsonPath("$.salary").value(1000))
                .andExpect(jsonPath("$.gender").value("Female"));
    }

    @Test
    void should_create_employee_and_add_to_list_when_create_given_employee_information() throws Exception {
        String employeeStr = "{\n" +
                "        \"name\": \"BertCreate\",\n" +
                "        \"age\": 99,\n" +
                "        \"salary\": 1000,\n" +
                "        \"gender\": \"Male\"\n" +
                "    }";

        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(employeeStr))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("BertCreate"))
                .andExpect(jsonPath("$.age").value(99))
                .andExpect(jsonPath("$.salary").value(1000))
                .andExpect(jsonPath("$.gender").value("Male"));
    }

    @Test
    void should_return_list_of_employees_based_on_pageIndex_and_pageSize() throws Exception {
        String pageIndex = "1";
        String pageSize = "2";
        mockMvc.perform(MockMvcRequestBuilders.get("/employees")
                .param("pageIndex", pageIndex).param("pageSize", pageSize))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)));
    }

    @Test
    void should_return_list_of_employees_when_findAllByGender_given_gender() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employees")
                .param("gender", "Male"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].gender").value("Male"));
    }

    @Test
    void should_return_employee_when_findById_given_employeeId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bert"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.salary").value(100))
                .andExpect(jsonPath("$.gender").value("Male"));
    }

    @Test
    void should_return_EmployeeNotFoundException_when_findById_given_invalid_employeeId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", 3))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmployeeNotFoundException))
                .andExpect(jsonPath("$.message").value("Employee does not exist!"));
    }
}
