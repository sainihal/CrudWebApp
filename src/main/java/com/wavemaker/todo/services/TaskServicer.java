package com.wavemaker.todo.services;

import com.wavemaker.todo.dao.MysqlTaskRepoImpl;
import com.wavemaker.todo.dao.TaskRepo;
import com.wavemaker.todo.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created by sainihala on 26/7/16.
 */
public class TaskServicer {

    private static final Logger logger = LoggerFactory.getLogger(TaskServicer.class.getName());
    private TaskRepo taskRepo;

    public TaskServicer() {
        this.taskRepo = new MysqlTaskRepoImpl();
    }

    public void create(Task task) {
        logger.info("inserting {}", task.toString());
        taskRepo.insert(task);
    }

    public Task retrive(int id) {
        Task task = taskRepo.retrive(id);
        return task;
    }

    public void update(Task task) {
        taskRepo.update(task);
    }

    public void delete(int id) {
        taskRepo.delete(id);
    }

    public List<Task> getAll() {
        return taskRepo.getAll();
    }
}
