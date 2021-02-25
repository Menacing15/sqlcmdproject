package ua.alexander.sqlcmd.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.service.ServiceFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TablesServlet extends HttpServlet {

    @Autowired
    private ServiceFactory serviceFactory;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataBaseManager manager = (DataBaseManager) req.getSession().getAttribute("manager");
        req.setAttribute("tableNames", serviceFactory.getService().tables(manager));
        req.getRequestDispatcher("tables.jsp").forward(req, resp);
    }
}
