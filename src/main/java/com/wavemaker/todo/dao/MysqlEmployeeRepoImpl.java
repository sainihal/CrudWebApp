package com.wavemaker.todo.dao;

import com.wavemaker.todo.exceptions.DataBaseException;
import com.wavemaker.todo.model.Employee;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sainihala on 5/8/16.
 */
public class MysqlEmployeeRepoImpl implements EmployeeRepo {
    private Connection dbConnection = MysqlDBConnection.getConnection();
    private static Logger logger = LoggerFactory.getLogger(MysqlEmployeeRepoImpl.class.getName());

    public Employee insert(Employee employee) {
        String query = " insert into Employee (id,name,role,address)"
                + " values (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setString(3, employee.getRole());
            preparedStatement.setString(4, employee.getAddress());
//            preparedStatement.setBlob(5, new ByteArrayInputStream(new byte[10]));
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                employee.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        logger.info(employee.toString());
        return employee;
    }

    public void delete(int id) {
        String query = "delete from Employee where id = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    public void update(Employee employee) {
        String query = "update  Employee set name = ?,role = ?,address = ? where id = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setInt(4, employee.getId());
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getRole());
            preparedStatement.setString(3, employee.getAddress());
//            preparedStatement.setBlob(5, new ByteArrayInputStream(new byte[10]));
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    public Employee retrive(int id) {
        Employee employee = null;
        String query = "SELECT * FROM Employee WHERE id = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                employee = getNextEmployee(rs);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return employee;
    }

    public List<Employee> getAll() {
        List<Employee> list = new ArrayList();
        String query = "SELECT * FROM Employee";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Employee employee = getNextEmployee(rs);
                list.add(employee);
            }
        } catch (SQLException sql) {
            throw new DataBaseException(sql);
        }
        return list;
    }

    public byte[] getImage(int id) {
        String query = "select * from EmpImages where id ="+id;
        try{
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                Blob image = rs.getBlob("image");
                logger.info(""+image);
                return image.getBytes(1,(int)image.length());
            }

        }catch (SQLException sql){
            logger.info("exception in  inserting image");
            sql.printStackTrace();
            throw new DataBaseException(sql);
        }
        return null;
    }

    public void saveImage(int id, FileItem fileItem){
        String query = "Insert into EmpImages(id,image) values (?,?)";
        try{
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            preparedStatement.setBlob(2,fileItem.getInputStream(),fileItem.getSize());
            preparedStatement.execute();
        }catch (Exception e ){
            logger.info("exception in insertin image");
            e.printStackTrace();
            throw new DataBaseException(e);
        }
    }

    private Employee getNextEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setName(rs.getString("name"));
        employee.setAddress(rs.getString("address"));
        employee.setRole(rs.getString("role"));
        return employee;
    }

}
