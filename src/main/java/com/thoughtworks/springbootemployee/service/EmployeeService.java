package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exceptions.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EmployeeService {

    @Resource
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(int employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findAllByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public List<Employee> getListByPagination(Integer pageIndex, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(pageIndex - 1, pageSize)).getContent();
    }

    public Employee delete(Integer employeeId) {
        Employee toBeRemoved = employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
        employeeRepository.deleteById(employeeId);
        return toBeRemoved;
    }

    public Employee update(int employeeId, Employee updateEmployeeDetails) {
        Employee toBeUpdated = employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
        updateEmployeeInformation(toBeUpdated, updateEmployeeDetails);
        return save(toBeUpdated);
    }

    private void updateEmployeeInformation(Employee employee, Employee employeeUpdate) {
        if (employeeUpdate.getAge() != null) {
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
        if (employeeUpdate.getCompanyId() != null) {
            employee.setCompanyId(employeeUpdate.getCompanyId());
        }
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
}
