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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
                new Employee(2, "Kyle", 25, "Male", 100),
                new Employee(3, "Shanine", 24, "Female", 1000));
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

    @Test
    void should_return_male_employees_when_findByGender_given_all_employees() {
        given(employeeRepository.getAll()).willReturn(expectedEmployees);
        List<Employee> expectedEmployees = Arrays.asList(new Employee(1, "Bert", 25, "Male", 100),
                new Employee(2, "Kyle", 25, "Male", 100));
        List<Employee> outputEmployees = service.findByGender("Male");
        assertEquals(2, outputEmployees.stream()
                .map(Employee::getGender)
                .filter(gender -> gender.equals("Male")).count());
        assertEquals(expectedEmployees.get(0).getGender(), outputEmployees.get(0).getGender());
        assertEquals(expectedEmployees.get(1).getGender(), outputEmployees.get(1).getGender());
    }

    @Test
    void should_return_female_employees_when_findByGender_given_all_employees() {
        given(employeeRepository.getAll()).willReturn(expectedEmployees);
        List<Employee> expectedEmployees = Collections.singletonList(new Employee(3, "Shanine", 24, "Female", 1000));
        List<Employee> outputEmployees = service.findByGender("Female");
        assertEquals(1, outputEmployees.stream()
                .map(Employee::getGender)
                .filter(gender -> gender.equals("Female")).count());
        assertEquals(expectedEmployees.get(0).getGender(), outputEmployees.get(0).getGender());
    }

    @Test
    void should_return_five_elements_only_in_a_list_when_getListByPagination_given_pageSize_is_five_and_pageIndex_is_one() {
        given(employeeRepository.getAll()).willReturn(Arrays.asList(new Employee(),new Employee(),new Employee(),new Employee(),new Employee(),new Employee()
        ,new Employee(),new Employee(),new Employee(),new Employee()));
        int expectedCount = 5;
        int outputCount = service.getListByPagination(1, 5).size();
        assertEquals(outputCount, expectedCount);
    }

    @Test
    void should_delete_employee_when_delete_given_employee_id_and_all_employees() {
        List<Employee> expectedEmployees = new ArrayList<>();
        expectedEmployees.add(new Employee(1, "Bert", 25, "Male", 100));
        expectedEmployees.add(new Employee(2, "Kyle", 25, "Male", 100));
        expectedEmployees.add(new Employee(3, "Shanine", 24, "Female", 1000));
        given(employeeRepository.getAll()).willReturn(expectedEmployees);
        Employee expectedEmployeeToBeDeleted = service.delete(1);
        assertNotNull(expectedEmployeeToBeDeleted);
        assertEquals(2, expectedEmployees.size());
        assertEquals(1, expectedEmployeeToBeDeleted.getId());
    }

    @Test
    void should_update_employee_when_update_given_employee_information_and_employee_id() {
        given(employeeRepository.getAll()).willReturn(expectedEmployees);
        Employee updateEmployeeDetails = new Employee(){{
           setName("Bertwo");
           setSalary(10000);
        }};
        Employee outputEmployeeAfterUpdate = service.update(1, updateEmployeeDetails);
        assertEquals(expectedEmployees.get(0).getName(), updateEmployeeDetails.getName());
        assertEquals(expectedEmployees.get(0).getSalary(), updateEmployeeDetails.getSalary());
        assertEquals(outputEmployeeAfterUpdate.getName(), updateEmployeeDetails.getName());
        assertEquals(outputEmployeeAfterUpdate.getSalary(), updateEmployeeDetails.getSalary());
    }
}
