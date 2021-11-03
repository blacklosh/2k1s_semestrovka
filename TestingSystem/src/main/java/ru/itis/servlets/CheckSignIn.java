package ru.itis.servlets;

import ru.itis.models.User;
import ru.itis.repository.UsersRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/checkSignIn")
public class CheckSignIn extends HttpServlet {
    private UsersRepository usersRepository;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        usersRepository = (UsersRepository) servletContext.getAttribute("usersRepository");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request.setAttribute("ErrorSignIn", "Сбой авторизации, попробуйте ещё раз! Код ошибки: G-01");
        request.getRequestDispatcher("/jsp/sign-in.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nickname = (String) req.getParameter("nickname");
        String password = (String) req.getParameter("password");
        HttpSession session = req.getSession();
        req.setAttribute("ErrorSignIn", null);
        Optional<User> user = usersRepository.findByNamePassword(nickname, password);
        if(user.isPresent()){
            session.setAttribute("user", user.get());
            req.getRequestDispatcher("/justAuth").forward(req,resp);
        }else{
            session.setAttribute("user", null);
            req.setAttribute("ErrorSignIn", "Неверная пара логин-пароль!");
            req.getRequestDispatcher("/jsp/sign-in.jsp").forward(req,resp);
        }
    }
}
