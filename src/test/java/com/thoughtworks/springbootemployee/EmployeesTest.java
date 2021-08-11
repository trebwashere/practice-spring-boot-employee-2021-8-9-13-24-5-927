package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class EmployeesTest {

    @InjectMocks
    EmployeeService service;

    @Mock
    EmployeeRepository employeeRepository;

    private List<Employee> expectedEmployees;

    @BeforeEach
    public void setup() {
        expectedEmployees = Arrays.asList(new Employee(1, "Bert", 25, "Male", 100),
                new Employee(2, "Kyle", 25, "Male", 100));
    }

    @Test
    void should_return_all_employees_when_getAllEmployees_given_all_employees() {
        given(employeeRepository.getAll()).willReturn(expectedEmployees);
        List<Employee> outputEmployees = service.getAllEmployees();
        assertEquals(outputEmployees, expectedEmployees);
    }

    @Test
    void should_return_specific_employee_when_findById_given_employee_id() {
        given(employeeRepository.getAll()).willReturn(expectedEmployees);
        Employee expectedEmployee = new Employee(1, "Bert", 25, "Male", 100);
        Employee outputEmployee = service.findById(1);
        assertEquals(expectedEmployee.getId(), outputEmployee.getId());
    }
}
