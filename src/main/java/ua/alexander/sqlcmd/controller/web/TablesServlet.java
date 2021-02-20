package ua.alexander.sqlcmd.controller.web;

import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.service.Service;
import ua.alexander.sqlcmd.service.ServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TablesServlet extends HttpServlet {

    private Service service;

    @Override
    public void init() throws ServletException {
        super.init();
        service = new ServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataBaseManager manager = (DataBaseManager) req.getSession().getAttribute("manager");
        req.setAttribute("tablenames", service.tables(manager));
        req.getRequestDispatcher("tables.jsp").forward(req, resp);
    }
}
