package ua.alexander.sqlcmd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ua.alexander.sqlcmd.service.ServiceFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DropServlet extends HttpServlet {

    @Autowired
    private ServiceFactory serviceFactory;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("submit_name.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String tableName = req.getParameter("tableName");
        try {
            serviceFactory.getService().drop(tableName);
            req.getRequestDispatcher("drop.jsp").forward(req, resp);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            req.setAttribute("errorMessage", ex.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
