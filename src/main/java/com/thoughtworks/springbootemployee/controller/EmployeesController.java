package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.request.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.response.EmployeeResponse;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeMapper employeeMapper;

    @GetMapping()
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping(path = "/{employeeId}")
    public EmployeeResponse findById(@PathVariable Integer employeeId) {
        return employeeMapper.toResponse(employeeService.findById(employeeId));
    }

    @GetMapping(params = "gender")
    public List<Employee> findByGender(@RequestParam("gender") String gender) {
        return employeeService.findAllByGender(gender);
    }

    @DeleteMapping("/{employeeId}")
    public Employee delete(@PathVariable Integer employeeId) {
        return employeeService.delete(employeeId);
    }

    @GetMapping(params = {"pageIndex", "pageSize"})
    public List<Employee> getListByPagination(@RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        return employeeService.getListByPagination(pageIndex, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse create(@RequestBody EmployeeRequest employeeRequest) {
        return employeeMapper.toResponse(employeeService.save(employeeMapper.toEntity(employeeRequest)));
    }

    @PutMapping(path = "/{employeeId}")
    public EmployeeResponse update(@PathVariable Integer employeeId, @RequestBody EmployeeRequest employeeRequest) {
        return employeeMapper.toResponse(employeeService.update(employeeId, employeeMapper.toEntity(employeeRequest)));
    }
}
