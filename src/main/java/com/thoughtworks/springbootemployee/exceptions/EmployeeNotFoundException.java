package com.thoughtworks.springbootemployee.exceptions;

public class EmployeeNotFoundException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Employee does not exist!";
    }
}
