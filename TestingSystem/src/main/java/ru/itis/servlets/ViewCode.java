package ru.itis.servlets;

import ru.itis.models.Pack;
import ru.itis.models.Task;
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
import java.util.Optional;

@WebServlet("/viewCode")
public class ViewCode extends HttpServlet {
    private TasksRepository tasksRepository;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        tasksRepository = (TasksRepository) servletContext.getAttribute("tasksRepository");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long packid = Long.parseLong(req.getParameter("packid"));
        if(packid==null){
            req.setAttribute("errorCurrent", "Произошла внутренняя ошибка. Код ошибки: G-06");
            req.getRequestDispatcher("/jsp/errorCurrent.jsp").forward(req,resp);
        }else {
            Optional<Pack> pack = tasksRepository.getPackById(packid);
            if(!pack.isPresent()){
                req.setAttribute("errorCurrent", "Произошла внутренняя ошибка. Код ошибки: G-07");
                req.getRequestDispatcher("/jsp/errorCurrent.jsp").forward(req,resp);
            }else{
                Pack p = pack.get();
                req.setAttribute("currentPack", p);
                req.getRequestDispatcher("/jsp/VC.jsp").forward(req,resp);
            }
        }
    }
}
