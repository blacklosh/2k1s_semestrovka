package ru.itis.filters;

import ru.itis.models.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final String[] EXCLUDED = {"/checkSignIn", "/checkSignUp", "/", "/jsp/.*", "/css/.*", "/js/.*"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(true);

        for(int i = 0; i < EXCLUDED.length; i++) {
            if (request.getRequestURI().matches(EXCLUDED[i])) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        User currentUser = (User)session.getAttribute("user");

        if(currentUser==null){
            response.sendRedirect("/");
        }else{
            filterChain.doFilter(servletRequest,servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
