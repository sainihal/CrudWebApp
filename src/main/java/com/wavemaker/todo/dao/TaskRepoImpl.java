package com.wavemaker.todo.dao;

import com.wavemaker.todo.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sainihala on 25/7/16.
 */
public class TaskRepoImpl implements TaskRepo {

    private static final Logger logger = LoggerFactory.getLogger(TaskRepoImpl.class.getName());
    private Map<Integer, Task> taskById = new ConcurrentHashMap<Integer, Task>();

    public Task insert(Task task) {
        int id = task.getId();
        taskById.put(id, task);
        for (Integer key : taskById.keySet()) {
            logger.info("key : {}", key);
            logger.info("value : {}", taskById.get(key));
        }
        return task;
    }

    public void delete(int id) {
        taskById.remove(id);
        logger.info("removing element " + id);
        logger.info("remaining elements");
        for (Integer key : taskById.keySet()) {
            logger.info("key :{} ", key);
            logger.info("value : {}", taskById.get(key));
        }
    }
    public void update(Task task) {
        taskById.get(task.getId()).setStatus("COMPLETED");
        logger.info("task updated");
    }

    public Task retrive(int id) {
        return taskById.get(id);
    }

    public List<Task> getAll() {
        List<Task> list = new ArrayList<Task>(taskById.values());
        return list;
    }

}
