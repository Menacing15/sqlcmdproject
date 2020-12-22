package ua.alexander.sqlcmd.controller.web;

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

        if(action.startsWith("/menu") || action.equals("/")){
            req.setAttribute("items", service.commandsList());
            req.getRequestDispatcher("menu.jsp").forward(req,resp);
        }else if(action.startsWith("/help")){
            req.getRequestDispatcher("help.jsp").forward(req,resp);
        }else if(action.startsWith("/connect")){
            req.getRequestDispatcher("connect.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);

        if(action.startsWith("/connect")){
            String dbName = req.getParameter("dbname");
            String user = req.getParameter("username");
            String password = req.getParameter("password");
            try {
                service.connect(dbName, user, password);
                resp.sendRedirect(resp.encodeRedirectURL("menu"));
            }catch (RuntimeException ex){
                req.setAttribute("msg", ex.getMessage());
                req.getRequestDispatcher("error.jsp").forward(req,resp);
            }
        }
    }

    private String getAction(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        return requestURI.substring(req.getContextPath().length());
    }
}
