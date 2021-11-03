package ru.itis.servlets;

import ru.itis.models.Task;
import ru.itis.models.User;
import ru.itis.repository.FilesRepository;
import ru.itis.repository.TasksRepository;
import ru.itis.repository.UsersRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet("/justAuth")
public class MainPage extends HttpServlet {

    private TasksRepository tasksRepository;
    private UsersRepository usersRepository;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        tasksRepository = (TasksRepository) servletContext.getAttribute("tasksRepository");
        usersRepository = (UsersRepository) servletContext.getAttribute("usersRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");

        Long imgId = (Long) session.getAttribute("setImg");
        session.setAttribute("setImg", null);

        if(imgId!=null){
            currentUser.setAvatarId(imgId);
            usersRepository.update(currentUser.getId(), currentUser);
        }

        req.setAttribute("setImg", null);
        req.setAttribute("errorCurrent", null);
        req.setAttribute("task", null);
        req.setAttribute("id", null);
        List<Task> tasks = tasksRepository.findAll();
        //System.out.println(tasks);
        req.setAttribute("tasks", tasks);

        req.getRequestDispatcher("/jsp/main.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(303);
        //System.out.println("Прошёл по 303");
        resp.setHeader("Location", "/justAuth");
    }
}
