package ru.itis.servlets;

import ru.itis.models.Task;
import ru.itis.models.User;
import ru.itis.repository.TasksRepository;
import ru.itis.repository.UsersRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

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
        User currentUser = (User)req.getSession().getAttribute("user");

        Long seeId = Long.parseLong(req.getParameter("see-id"));
        Optional<User> user = usersRepository.findById(seeId);

        List<Task> completed = new LinkedList<>();
        List<Task> started = new LinkedList<>();
        try {
            completed = tasksRepository.findCompletedTasksByUserId(user.get().getId());
            started = tasksRepository.findStartedTasksByUserId(user.get().getId());
            System.out.println("хорошо: ");
        }catch (Exception e){
            // Обрабатывать не нужно: jsp умеет разруливать ситуацию некорректного пользователя
            System.out.println("плохо");
        }

        req.setAttribute("see-user", user);
        req.setAttribute("completed", completed);
        req.setAttribute("started", started);

        req.getRequestDispatcher("/jsp/profile.jsp").forward(req,resp);
    }
}
