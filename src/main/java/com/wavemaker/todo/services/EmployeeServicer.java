package com.wavemaker.todo.services;

import com.wavemaker.todo.dao.EmployeeRepo;
import com.wavemaker.todo.dao.MysqlEmployeeRepoImpl;
import com.wavemaker.todo.model.Employee;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by sainihala on 26/7/16.
 */
public class EmployeeServicer {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServicer.class.getName());
    private EmployeeRepo employeeRepo;

    public EmployeeServicer() {
            this.employeeRepo = new MysqlEmployeeRepoImpl();
    }

    public void create(Employee employee) {
        logger.info("inserting {}", employee.toString());
        employeeRepo.insert(employee);
    }

    public Employee retrive(int id) {
        Employee employee = employeeRepo.retrive(id);
        return employee;
    }

    public void update(Employee employee) {
        employeeRepo.update(employee);
    }

    public void delete(int id) {
        employeeRepo.delete(id);
    }

    public List<Employee> getAll() {
        return employeeRepo.getAll();
    }

    public byte[] getImage(int id) {
        return employeeRepo.getImage(id);
    }
    public void saveImage(int id, FileItem fileItem) {
         employeeRepo.saveImage(id,fileItem);
    }

}
