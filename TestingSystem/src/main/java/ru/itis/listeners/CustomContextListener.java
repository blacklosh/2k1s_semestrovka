package ru.itis.listeners;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.itis.repository.FilesRepository;
import ru.itis.repository.TasksRepository;
import ru.itis.repository.UsersRepository;
import ru.itis.repository.jdbcimpl.FilesRepositoryImpl;
import ru.itis.repository.jdbcimpl.TasksDaoJdbcImpl;
import ru.itis.repository.jdbcimpl.UsersDaoJdbcImpl;
import ru.itis.services.FilesService;
import ru.itis.services.impl.FilesServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CustomContextListener implements ServletContextListener {

    DriverManagerDataSource dataSource;

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "qwerty";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String FILE_PATH = "c:\\ptest\\";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_URL);

        UsersRepository usersRepository = new UsersDaoJdbcImpl(dataSource);
        TasksRepository tasksRepository = new TasksDaoJdbcImpl(dataSource);

        servletContext.setAttribute("usersRepository", usersRepository);
        servletContext.setAttribute("tasksRepository", tasksRepository);

        FilesRepository filesRepository = new FilesRepositoryImpl(dataSource);
        FilesService filesService = new FilesServiceImpl(filesRepository, FILE_PATH);

        servletContext.setAttribute("filesRepository", filesRepository);
        servletContext.setAttribute("filesService", filesService);

        servletContext.setAttribute("filepath", FILE_PATH);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}