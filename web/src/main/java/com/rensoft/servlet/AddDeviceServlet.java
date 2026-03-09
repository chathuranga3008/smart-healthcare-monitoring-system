package com.rensoft.servlet;

import com.rensoft.remote.DataProcessor;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;

@WebServlet(name = "addDevice", value = "/add")
public class AddDeviceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String patientId = request.getParameter("pid");
        DataProcessor dataProcessor;

        try {
            InitialContext context = new InitialContext();
            HttpSession session = request.getSession();
            if (session.getAttribute("device-session") == null) {
                dataProcessor = (DataProcessor) context.lookup("java:global/ear-1.0/com.rensoft-ejb-1.0/DataProcessorBean");
                session.setAttribute("device-session", dataProcessor);
            } else {
                dataProcessor = (DataProcessor) session.getAttribute("device-session");
            }
            dataProcessor.newDevice(patientId);
            response.sendRedirect("device");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
