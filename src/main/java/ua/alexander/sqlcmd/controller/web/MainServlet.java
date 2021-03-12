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

public class MainServlet extends HttpServlet {

    @Autowired
    private ServiceFactory serviceFactory;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);

        DataBaseManager manager = (DataBaseManager) req.getSession().getAttribute("manager");
        if (manager == null && !(action.startsWith("/menu") || action.startsWith("/help"))) {
            req.getRequestDispatcher("connect.jsp").forward(req, resp);
            return;
        }
        if (action.startsWith("/menu") || action.equals("/")) {
            req.setAttribute("items", serviceFactory.getService().commandsList());
            goToJSP(req, resp, "menu.jsp");
        } else if (action.startsWith("/help")) {
            goToJSP(req, resp, "help.jsp");
        } else if (action.startsWith("/connect")) {
            goToJSP(req, resp, "connect.jsp");
        } else if (action.startsWith("/find")) {
            resp.sendRedirect("/find");
        } else if (action.startsWith("/tables")) {
            resp.sendRedirect("/tables");
        } else if (action.startsWith("/create")) {
            resp.sendRedirect("/create");
        } else if (action.startsWith("/drop")) {
            resp.sendRedirect("/drop");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        establishConnection(req, resp);
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
            DataBaseManager manager = serviceFactory.getService().connect(dbName, user, password);
            req.getSession().setAttribute("manager", manager);
            req.getSession().setAttribute("dbname", dbName);
            resp.sendRedirect(resp.encodeRedirectURL("menu"));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            req.setAttribute(ex.getMessage(),"errorMessage");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }

    private void goToJSP(HttpServletRequest req, HttpServletResponse resp, String jsp) throws ServletException, IOException {
        req.getRequestDispatcher(jsp).forward(req, resp);
    }
}
