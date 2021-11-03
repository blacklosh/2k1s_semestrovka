package ru.itis.servlets;

import ru.itis.models.User;
import ru.itis.repository.UsersRepository;
import ru.itis.services.FormValidateService;

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

@WebServlet("/checkSignUp")
public class CheckSignUp extends HttpServlet {
    private UsersRepository usersRepository;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        usersRepository = (UsersRepository) servletContext.getAttribute("usersRepository");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("ErrorSignUp", "Сбой регистрации, попробуйте ещё раз! Код ошибки: G-02");
        request.getRequestDispatcher("/jsp/sign-up.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nickname = (String) req.getParameter("nickname");
        String password = (String) req.getParameter("password");
        String password2 = (String) req.getParameter("repeat_password");

        HttpSession session = req.getSession();

        List<String> errors = FormValidateService.checkForm(nickname, password, password2);

        if(!errors.isEmpty()){
            session.setAttribute("user", null);
            req.setAttribute("ErrorSignUp", errors.get(0));
            req.getRequestDispatcher("/jsp/sign-up.jsp").forward(req,resp);
        }

        req.setAttribute("ErrorSignUp", null);

        Optional<User> user = usersRepository.findByName(nickname);
        if (!user.isPresent()) {
            User u = usersRepository.save(User.builder().nickname(nickname).password(password).avatarId(0L).build());
            session.setAttribute("user", u);
            req.getRequestDispatcher("/justAuth").forward(req, resp);
        } else {
            session.setAttribute("user", null);
            req.setAttribute("ErrorSignUp", "Логин занят!");
            req.getRequestDispatcher("/jsp/sign-up.jsp").forward(req, resp);
        }
    }
}
