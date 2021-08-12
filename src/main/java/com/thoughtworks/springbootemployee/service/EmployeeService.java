package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import javafx.scene.control.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Resource
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    public Employee findById(int employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    public List<Employee> findAllByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public List<Employee> getListByPagination(Integer pageIndex, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(pageIndex - 1, pageSize)).getContent();
    }

    public Employee delete(Integer employeeId) {
        Optional<Employee> toBeRemoved = employeeRepository.findById(employeeId);
        employeeRepository.deleteById(employeeId);
        return toBeRemoved.orElse(null);
    }

    public Employee update(int employeeId, Employee updateEmployeeDetails) {
        Employee toBeUpdated = employeeRepository.findById(employeeId).orElse(null);
        if (toBeUpdated != null) {
            updateEmployeeInformation(toBeUpdated, updateEmployeeDetails);
            return save(toBeUpdated);
        }
        return null;
    }

    private void updateEmployeeInformation(Employee employee, Employee employeeUpdate) {
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
        if (employeeUpdate.getCompanyId() != null) {
            employee.setCompanyId(employeeUpdate.getCompanyId());
        }
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
}
