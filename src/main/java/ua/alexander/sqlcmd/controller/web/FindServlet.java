package ua.alexander.sqlcmd.controller.web;

import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.service.Service;
import ua.alexander.sqlcmd.service.ServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FindServlet extends HttpServlet {

    private Service service;

    @Override
    public void init() throws ServletException {
        super.init();
        service = new ServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("prefind.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        DataBaseManager manager = (DataBaseManager) req.getSession().getAttribute("manager");

        String tableName = req.getParameter("tableName");

        List<List<String>> result = service.find(manager, tableName);
        if (result.get(0).isEmpty()) {
            req.setAttribute("table", null);
        } else {
            req.setAttribute("table", result);
        }

        req.getRequestDispatcher("find.jsp").forward(req, resp);
    }
}
