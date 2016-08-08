package com.wavemaker.todo.dao;

import com.wavemaker.todo.exceptions.DataBaseException;
import com.wavemaker.todo.model.Employee;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sainihala on 25/7/16.
 */
public class EmployeeRepoImpl implements EmployeeRepo {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeRepoImpl.class.getName());
    private Map<Integer, Employee> employeeById = new ConcurrentHashMap<Integer, Employee>();
    private int id = 0;

    public Employee insert(Employee employee) {
        employeeById.put(id++, employee);
        for (Integer key : employeeById.keySet()) {
            logger.info("key : {}", key);
            logger.info("value : {}", employeeById.get(key));
        }
        return employee;
    }

    public void delete(int id) {
        employeeById.remove(id);
        logger.info("removing element " + id);
        logger.info("remaining elements");
        for (Integer key : employeeById.keySet()) {
            logger.info("key :{} ", key);
            logger.info("value : {}", employeeById.get(key));
        }
    }

    public void update(Employee employee) {
        employeeById.put(employee.getId(), employee);
        logger.info("employee updated");
    }

    public Employee retrive(int id) {
        return employeeById.get(id);
    }

    public List<Employee> getAll() {
        return new ArrayList<Employee>(employeeById.values());
    }

    public byte[] getImage(int id) {
        try {
            File file = new File("/tmp/" + id);
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            byte[] imgData = new byte[(int) file.length()];
            dis.readFully(imgData);
            return imgData;
        }catch (Exception e){
            throw new DataBaseException(e);
        }
    }

    public void saveImage(int id, FileItem fileItem) {
        File storeFile = new File("/tmp/" + id);
        try {
            fileItem.write(storeFile);
        }catch (Exception e){
            throw new DataBaseException(e);
        }
    }

}
