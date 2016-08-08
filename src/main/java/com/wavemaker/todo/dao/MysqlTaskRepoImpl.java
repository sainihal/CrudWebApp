package com.wavemaker.todo.dao;

import com.wavemaker.todo.exceptions.DataBaseException;
import com.wavemaker.todo.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sainihala on 5/8/16.
 */
public class MysqlTaskRepoImpl implements TaskRepo {
    private static Logger logger = LoggerFactory.getLogger(MysqlTaskRepoImpl.class.getName());
    private Connection dbConnection = MysqlDBConnection.getConnection();

    public Task insert(Task task) {
        String query = " insert into Task (id,name,description,status,personId,dueDate)"
                + " values (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            logger.info("inserting  task with id" + task.getId());
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, task.getName());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setString(4, task.getStatus());
            preparedStatement.setLong(5, task.getPersonId());
            preparedStatement.setLong(6, task.getDueDate());
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                task.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return task;
    }

    public void delete(int id) {
        String query = "delete from Task where id = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    public void update(Task task) {
        String query = " update Task set status = \"COMPLETED\" where  id =" + task.getId();
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    public Task retrive(int id) {
        Task task = null;
        String selectSQL = "SELECT * FROM Task WHERE id = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                task = getNextTask(rs);
            }
        } catch (Exception e) {
            throw new DataBaseException(e);
        }
        return task;
    }

    public List<Task> getAll() {
        List<Task> list = new ArrayList<Task>();
        String selectSQL = "SELECT * FROM Task";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Task task = getNextTask(rs);
                list.add(task);
            }
        } catch (SQLException sql) {
            throw new DataBaseException(sql);
        }
        System.out.println("list is " + list.toString());
        return list;
    }


    private Task getNextTask(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setStatus(rs.getString("status"));
        task.setDescription(rs.getString("description"));
        task.setName(rs.getString("name"));
        task.setDueDate(rs.getLong("dueDate"));
        task.setPersonId(rs.getInt("personId"));
        task.setId(rs.getInt("id"));
        return task;
    }
}
