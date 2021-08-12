package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeesIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EmployeeService service;

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
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}", 229))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bert"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(100));

    }

    @Test
    void should_update_employee_when_update_given_employee_information_and_employee_id() throws Exception {
        final Employee employee = new Employee(33, "Bert", 25, 100, "Male");
        service.update(employee.getId(),employee);
        String employeeStr = "{\n" +
                "        \"id\": 33,\n" +
                "        \"name\": \"Bert\",\n" +
                "        \"age\": 25,\n" +
                "        \"salary\": 100,\n" +
                "        \"gender\": \"Male\"\n" +
                "    }";

        mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}", 33)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(employeeStr))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bert"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.salary").value(100))
                .andExpect(jsonPath("$.gender").value("Male"));
    }

    @Test
    void should_create_employee_and_add_to_list_when_create_given_employee_information() throws Exception {
        final Employee employee = new Employee(1, "Bert", 25, 100, "Male");
        service.save(employee);

        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[9].name").value("Bert"))
                .andExpect(jsonPath("$[9].age").value(25))
                .andExpect(jsonPath("$[9].salary").value(100))
                .andExpect(jsonPath("$[9].gender").value("Male"));
    }
}
