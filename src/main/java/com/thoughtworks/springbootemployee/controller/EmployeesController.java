package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeesController {
    private final List<Employee> employees = new ArrayList<>();

    public EmployeesController() {
        employees.add(new Employee(1, "Bert", 25, "Male", 100));
        employees.add(new Employee(2, "Kyle", 25, "Male", 100));
    }

    @GetMapping()
    public List<Employee> getAllEmployees() {
        return employees;
    }

    @GetMapping(path = "/{employeeId}")
    public Employee findById(@PathVariable Integer employeeId) {
        return employees.stream()
                .filter((employee -> employee.getId().equals(employeeId))).findFirst().orElse(null);
    }

    @GetMapping(params = "gender")
    public List<Employee> findByGender(@RequestParam("gender") String gender) {
        return employees.stream()
                .filter((employee -> employee.getGender().equals(gender)))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{employeeId}")
    public Employee delete(@PathVariable Integer employeeId) {
        Employee toBeRemoved = employees.stream()
                .filter(employee -> employee.getId()
                        .equals(employeeId))
                .findFirst().orElse(null);
        if (toBeRemoved != null) {
            employees.remove(toBeRemoved);
            return toBeRemoved;
        }
        return null;
    }

    @GetMapping(params = {"pageIndex", "pageSize"})
    public List<Employee> getListByPagination(@RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        return employees.stream()
                .skip((long) (pageIndex - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) {
        Employee employeeToBeAdded = new Employee(employees.size() + 1, employee.getName(),
                employee.getAge(), employee.getGender(), employee.getSalary());
        employees.add(employeeToBeAdded);

        return employeeToBeAdded;
    }

}
