package com.thoughtworks.springbootemployee.model;

public class Employee {

    private Integer id;
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;

    public Employee(Integer id, String name, Integer age, String gender, Integer salary){
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;

    }

    public Employee() {

    }

    public Integer getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Integer getAge(){
        return age;
    }

    public String getGender(){
        return gender;
    }

    public Integer getSalary(){
        return salary;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}
