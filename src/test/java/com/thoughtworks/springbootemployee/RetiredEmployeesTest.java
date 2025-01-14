package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.RetiredEmployeeRepository;
import com.thoughtworks.springbootemployee.service.RetiredEmployeeService;
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
public class RetiredEmployeesTest {

    @InjectMocks
    RetiredEmployeeService service;

    @Mock
    RetiredEmployeeRepository retiredEmployeeRepository;

    private List<Employee> expectedEmployees;

    @BeforeEach
    public void setup() {
        expectedEmployees = Arrays.asList(new Employee(1, "Bert", 25, 100, "Male"),
                new Employee(2, "Kyle", 25, 100, "Male"),
                new Employee(3, "Shanine", 24, 1000, "Female"));
    }

    @Test
    void should_return_all_employees_when_getAllEmployees_given_all_employees() {
        given(retiredEmployeeRepository.getAll()).willReturn(expectedEmployees);
        List<Employee> outputEmployees = service.getAllEmployees();
        assertEquals(outputEmployees, expectedEmployees);
    }

    @Test
    void should_return_specific_employee_when_findById_given_employee_id() {
        given(retiredEmployeeRepository.getAll()).willReturn(expectedEmployees);
        Employee outputEmployee = service.findById(1);
        assertEquals(expectedEmployees.get(0), outputEmployee);
    }

    @Test
    void should_return_male_employees_when_findByGender_given_all_employees() {
        given(retiredEmployeeRepository.getAll()).willReturn(expectedEmployees);
        List<Employee> outputEmployees = service.findByGender("Male");
        assertEquals(2, outputEmployees.stream()
                .map(Employee::getGender)
                .filter(gender -> gender.equals("Male")).count());
        assertEquals(expectedEmployees.get(0).getGender(), outputEmployees.get(0).getGender());
        assertEquals(expectedEmployees.get(1).getGender(), outputEmployees.get(1).getGender());
    }

    @Test
    void should_return_female_employees_when_findByGender_given_all_employees() {
        given(retiredEmployeeRepository.getAll()).willReturn(expectedEmployees);
        List<Employee> outputEmployees = service.findByGender("Female");
        assertEquals(1, outputEmployees.stream()
                .map(Employee::getGender)
                .filter(gender -> gender.equals("Female")).count());
        assertEquals(expectedEmployees.get(2).getGender(), outputEmployees.get(0).getGender());
    }

    @Test
    void should_return_five_elements_only_in_a_list_when_getListByPagination_given_pageSize_is_five_and_pageIndex_is_one() {
        given(retiredEmployeeRepository.getAll()).willReturn(Arrays.asList(new Employee(), new Employee(), new Employee(), new Employee(), new Employee(), new Employee()
                , new Employee(), new Employee(), new Employee(), new Employee()));
        int expectedCount = 5;
        int outputCount = service.getListByPagination(1, 5).size();
        assertEquals(outputCount, expectedCount);
    }

    @Test
    void should_delete_employee_when_delete_given_employee_id_and_all_employees() {
        List<Employee> expectedEmployees = new ArrayList<>();
        expectedEmployees.add(new Employee(1, "Bert", 25, 100, "Male"));
        expectedEmployees.add(new Employee(2, "Kyle", 25, 100, "Male"));
        expectedEmployees.add(new Employee(3, "Shanine", 24, 1000, "Female"));
        given(retiredEmployeeRepository.getAll()).willReturn(expectedEmployees);
        Employee expectedEmployeeToBeDeleted = service.delete(1);
        assertNotNull(expectedEmployeeToBeDeleted);
        assertEquals(2, expectedEmployees.size());
        assertEquals(1, expectedEmployeeToBeDeleted.getId());
    }

    @Test
    void should_update_employee_when_update_given_employee_information_and_employee_id() {
        given(retiredEmployeeRepository.getAll()).willReturn(expectedEmployees);
        Employee updateEmployeeDetails = new Employee() {{
            setName("Bertwo");
            setSalary(10000);
        }};
        Employee outputEmployeeAfterUpdate = service.update(1, updateEmployeeDetails);
        assertEquals(expectedEmployees.get(0).getName(), updateEmployeeDetails.getName());
        assertEquals(expectedEmployees.get(0).getSalary(), updateEmployeeDetails.getSalary());
        assertEquals(outputEmployeeAfterUpdate.getName(), updateEmployeeDetails.getName());
        assertEquals(outputEmployeeAfterUpdate.getSalary(), updateEmployeeDetails.getSalary());
    }

    @Test
    void should_create_employee_and_add_to_list_when_create_given_employee_information() {
        List<Employee> employees = new ArrayList<>();
        given(retiredEmployeeRepository.getAll()).willReturn(employees);
        Employee updateEmployeeDetails = new Employee() {{
            setName("Bertwo");
            setAge(25);
            setGender("Male");
            setSalary(10000);
        }};
        service.create(updateEmployeeDetails);
        assertEquals(1, employees.size());
        assertEquals(employees.get(0).getId(), 1);
        assertEquals(updateEmployeeDetails.getName(), employees.get(0).getName());
        assertEquals(updateEmployeeDetails.getAge(), employees.get(0).getAge());
        assertEquals(updateEmployeeDetails.getSalary(), employees.get(0).getSalary());
    }
}
