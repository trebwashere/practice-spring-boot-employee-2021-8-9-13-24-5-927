package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Resource
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.getAll();
    }

    public Employee findById(int employeeId) {
        return employeeRepository.getAll().stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findAny()
                .orElse(null);
    }

    public List<Employee> findByGender(String gender) {
        return employeeRepository.getAll().stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public List<Employee> getListByPagination(Integer pageIndex, Integer pageSize) {
        long paginationFormula =  (long) (pageIndex - 1) * pageSize;
        return employeeRepository.getAll().stream()
                .skip(paginationFormula)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Employee delete(Integer employeeId) {
        Employee toBeRemoved = employeeRepository.getAll().stream()
                .filter(employee -> employee.getId()
                        .equals(employeeId))
                .findFirst().orElse(null);
        if (toBeRemoved != null) {
            employeeRepository.getAll().remove(toBeRemoved);
            return toBeRemoved;
        }
        return null;
    }

    public Employee update(int employeeId, Employee updateEmployeeDetails) {
        return employeeRepository.getAll().stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .map(employee -> updateEmployeeInformation(employee, updateEmployeeDetails))
                .orElse(null);
    }

    private Employee updateEmployeeInformation(Employee employee, Employee employeeUpdate) {
        if(employeeUpdate.getAge() != null) {
            employee.setAge(employeeUpdate.getAge());
        }
        if (employeeUpdate.getGender() != null) {
            employee.setGender(employeeUpdate.getGender());
        }
        if (employeeUpdate.getSalary() != null) {
            employee.setSalary(employeeUpdate.getSalary());
        }
        if (employeeUpdate.getName() != null) {
            employee.setName(employeeUpdate.getName());
        }
        return employee;
    }
}
