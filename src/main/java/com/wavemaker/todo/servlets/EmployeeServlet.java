package com.wavemaker.todo.servlets;

import com.google.gson.Gson;
import com.wavemaker.todo.model.Employee;
import com.wavemaker.todo.services.EmployeeServicer;
import com.wavemaker.todo.services.Servicer;
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

public class EmployeeServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServlet.class.getName());
    private EmployeeServicer employeeServicer;
    private Gson gson;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        employeeServicer = Servicer.getEmployeeServicer();
        gson = new Gson();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = br.readLine();
        Employee employee = gson.fromJson(json, Employee.class);
        logger.info("added employee details are" + employee.toString());
        employeeServicer.create(employee);
        logger.info(gson.toJson(employee));
        out.write((gson.toJson(employee)));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        String path = request.getPathInfo();
        if (path != null) {
            String[] pathInfo = request.getPathInfo().split("/");
            int id = Integer.parseInt(pathInfo[pathInfo.length - 1]);
            logger.info("id is {}" + id);
            out.print(gson.toJson(employeeServicer.retrive(id)));
        } else {
            List<Employee> empList = employeeServicer.getAll();
            out.print(gson.toJson(empList));
        }
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = br.readLine();
        Employee employee = gson.fromJson(json, Employee.class);
        employeeServicer.update(employee);
        logger.info("Employee updated");
        out.print("emp updated");
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String[] pathInfo = request.getPathInfo().split("/");
        int id = Integer.parseInt(pathInfo[pathInfo.length - 1]);
        logger.info("id is {}", id);
        employeeServicer.delete(id);
        logger.info("Employee deleted");
        out.print("emp deleted");
    }
}
