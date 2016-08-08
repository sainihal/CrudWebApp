package com.wavemaker.todo.dao;

import com.wavemaker.todo.model.Employee;
import org.apache.commons.fileupload.FileItem;

/**
 * Created by sainihala on 26/7/16.
 */
public interface EmployeeRepo extends Repository<Employee> {
    byte[] getImage(int id);
    void saveImage(int id, FileItem fileItem);
}
