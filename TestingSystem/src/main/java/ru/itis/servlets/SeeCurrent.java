package ru.itis.servlets;

import ru.itis.models.Pack;
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
import java.util.List;
import java.util.Optional;

@WebServlet("/seeCurrent")
public class SeeCurrent extends HttpServlet {

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

        Long id = Long.parseLong(req.getParameter("id"));
        if(id==null){
            req.setAttribute("errorCurrent", "Произошла внутренняя ошибка. Код ошибки: G-03");
            req.getRequestDispatcher("/jsp/errorCurrent.jsp").forward(req,resp);
        }else {
            Optional<Task> task = tasksRepository.findById(id);
            if(!task.isPresent()){
                req.setAttribute("errorCurrent", "Произошла внутренняя ошибка. Код ошибки: G-04");
                req.getRequestDispatcher("/jsp/errorCurrent.jsp").forward(req,resp);
            }else{
                Task c = task.get();
                req.setAttribute("task", c);

                List<Pack> packs = tasksRepository.getPacksByTaskUser(c.getId(), ((User)req.getSession().getAttribute("user")).getId() );
                req.setAttribute("currentPacks", packs);

                List<User> passed = usersRepository.findByCompletedTask(c.getId());
                req.setAttribute("passed", passed);

                req.getRequestDispatcher("/jsp/view.jsp").forward(req,resp);
            }
        }
    }
}
