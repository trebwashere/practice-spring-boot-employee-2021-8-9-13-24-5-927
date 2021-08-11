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
}
