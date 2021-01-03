package ua.alexander.sqlcmd.controller.web;

import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.service.Service;
import ua.alexander.sqlcmd.service.ServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    private Service service;

    @Override
    public void init() throws ServletException {
        super.init();
        service = new ServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);

        DataBaseManager manager = (DataBaseManager) req.getSession().getAttribute("manager");
        if (manager == null && !(action.startsWith("/menu") || action.startsWith("/help")))  {
            req.getRequestDispatcher("connect.jsp").forward(req, resp);
            return;
        }

        if (action.startsWith("/menu") || action.equals("/")) {
            req.setAttribute("items", service.commandsList());
            req.getRequestDispatcher("menu.jsp").forward(req, resp);
        } else if (action.startsWith("/help")) {
            req.getRequestDispatcher("help.jsp").forward(req, resp);
        } else if (action.startsWith("/connect")) {
            req.getRequestDispatcher("connect.jsp").forward(req, resp);
        }else if (action.startsWith("/find")) {
            resp.sendRedirect("/find");
        }
        else if (action.startsWith("/tables")) {
            resp.sendRedirect("/tables");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);

        if (action.startsWith("/connect")) {
            establishConnection(req, resp);
        }
    }

    private String getAction(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        return requestURI.substring(req.getContextPath().length());
    }

    private void establishConnection(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String dbName = req.getParameter("name");
        String user = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            DataBaseManager manager = service.connect(dbName, user, password);
            req.getSession().setAttribute("manager", manager);
            resp.sendRedirect(resp.encodeRedirectURL("menu"));
            req.getSession().setAttribute("dbname",dbName);
        } catch (RuntimeException ex) {
            req.setAttribute("message", ex.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
