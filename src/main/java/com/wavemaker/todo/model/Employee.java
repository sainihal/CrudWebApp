package com.wavemaker.todo.model;

/**
 * Created by sainihala on 25/7/16.
 */
public class Employee {
    private int id;
    private String name;
    private String role;
    private String address;
    private static final long serialVersionUID = 1L;

    public Employee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toString() {
        return "Employee   id " + id + " name " + name + " role " + role + " address " + address;
    }
}
