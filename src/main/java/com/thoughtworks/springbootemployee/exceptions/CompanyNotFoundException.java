package com.thoughtworks.springbootemployee.exceptions;

public class CompanyNotFoundException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Company does not exist!";
    }
}
