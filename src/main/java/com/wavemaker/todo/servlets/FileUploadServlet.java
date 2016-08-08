package com.wavemaker.todo.servlets;

import com.wavemaker.todo.services.Servicer;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


/**
 * Created by sainihala on 3/8/16.
 */
public class FileUploadServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadServlet.class.getName());

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List formItems = upload.parseRequest(request);
            Iterator iter = formItems.iterator();
            logger.info(formItems.toString());
            FileItem image = null;
            String id = null;
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.getFieldName().equals("empid")) {
                    id = item.getString();
                    logger.info("id is" + id);
                }
                logger.info(item.getName());
                if (!item.isFormField()) {
                    image = item;
                }
            }
            Servicer.getEmployeeServicer().saveImage(Integer.parseInt(id),image);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/EmployeeOperations.html");
            requestDispatcher.forward(request, response);
        } catch (Exception ex) {
            logger.info("message", ex);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        String[] pathInfo = request.getPathInfo().split("/");
        int id = Integer.parseInt(pathInfo[pathInfo.length - 1]);
        logger.info("id is {}", id);
        String encoded = Base64.encodeBase64String(Servicer.getEmployeeServicer().getImage(id));
        response.getOutputStream().print(encoded);
    }
}




