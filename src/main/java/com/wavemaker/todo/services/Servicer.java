package com.wavemaker.todo.services;

/**
 * Created by sainihala on 8/8/16.
 */
public class Servicer {
   private static EmployeeServicer employeeServicer;
   private static TaskServicer taskServicer;
    static{
        employeeServicer= new EmployeeServicer();
        taskServicer = new TaskServicer();
    }
    public static EmployeeServicer getEmployeeServicer(){
        return employeeServicer;
    }
    public static TaskServicer getTaskServicer(){
        return taskServicer;
    }
}
