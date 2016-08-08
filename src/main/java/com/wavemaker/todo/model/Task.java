package com.wavemaker.todo.model;

/**
 * Created by sainihala on 25/7/16.
 */
public class Task {
    private int id;
    private String name;
    private String description;
    private String status = "INCOMPLETE";
    private int personId;
    private long dueDate;

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "id " + id + " name " + name + " description " + description + " status " + status + " personId " + personId;
    }
}
