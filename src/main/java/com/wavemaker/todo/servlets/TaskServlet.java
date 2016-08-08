package com.wavemaker.todo.servlets;

import com.google.gson.Gson;
import com.wavemaker.todo.model.Task;
import com.wavemaker.todo.services.Servicer;
import com.wavemaker.todo.services.TaskServicer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by sainihala on 26/7/16.
 */

public class TaskServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TaskServlet.class.getName());
    private TaskServicer taskServicer;
    private Gson gson;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        taskServicer = Servicer.getTaskServicer();
        gson = new Gson();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = br.readLine();
        Task task = gson.fromJson(json, Task.class);
        logger.info("added task details are" + task.toString());
        taskServicer.create(task);
        logger.info(gson.toJson(task));
        out.write((gson.toJson(task).toString()));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        String path = request.getPathInfo();
        if (path != null) {
            String[] pathInfo = path.split("/");
            int id = Integer.parseInt(pathInfo[pathInfo.length - 1]);
            logger.info("id is {}" + id);
            out.print(gson.toJson(taskServicer.retrive(id)));
        } else {
            List<Task> taskList = taskServicer.getAll();
            out.print(gson.toJson(taskList));
            logger.info(taskList.toString());
        }
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = br.readLine();
        Task task = gson.fromJson(json, Task.class);
        taskServicer.update(task);
        logger.info("Task updated");
        out.print("task updated");
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String[] pathInfo = request.getPathInfo().split("/");
        int id = Integer.parseInt(pathInfo[pathInfo.length - 1]);
        logger.info("id is {}", id);
        taskServicer.delete(id);
        logger.info("Task deleted");
        out.print("task deleted");
    }
}
