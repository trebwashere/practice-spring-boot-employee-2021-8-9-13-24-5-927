package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompaniesController {
    @Autowired
    CompanyService companyService;

    @GetMapping()
    public List<Company> findAll() {
        return companyService.findAll();
    }

    @GetMapping(path = "/{companyId}")
    public Company findById(@PathVariable Integer companyId) {
        return companyService.findById(companyId);
    }

    @GetMapping(path = "/{companyId}/employees")
    public List<Employee> findAllByEmployeeCompanyId(@PathVariable Integer companyId) {
        return companyService.findAllByEmployeeCompanyId(companyId);
    }

    @GetMapping(params = {"pageIndex", "pageSize"})
    public List<Company> getListByPagination(@RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        return companyService.getListByPagination(pageIndex, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company save(@RequestBody Company company) {
        return companyService.save(company);
    }

    @PutMapping(path = "/{companyId}")
    public Company update(@PathVariable Integer companyId, @RequestBody Company companyUpdate) {
        return companyService.update(companyId, companyUpdate);
    }

    @DeleteMapping("/{companyId}")
    public Company delete(@PathVariable Integer companyId) {
        return companyService.delete(companyId);
    }

}
