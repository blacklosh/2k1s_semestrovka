package ru.itis.servlets;

import ru.itis.models.Pack;
import ru.itis.models.User;
import ru.itis.repository.TasksRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/listAllUserPacks")
public class ListAllUserPacks extends HttpServlet {
    private TasksRepository tasksRepository;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        tasksRepository = (TasksRepository) servletContext.getAttribute("tasksRepository");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Pack> packs = tasksRepository.getPacksByUser(((User)req.getSession().getAttribute("user")).getId());
        req.setAttribute("currentPacks", packs);
        req.getRequestDispatcher("/jsp/LAUP.jsp").forward(req,resp);
    }
}
