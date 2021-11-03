package ru.itis.servlets;


import ru.itis.compiler.Tester;
import ru.itis.models.Pack;
import ru.itis.models.Task;
import ru.itis.models.User;
import ru.itis.repository.TasksRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/compile")
public class Compile extends HttpServlet {
    private TasksRepository tasksRepository;
    ObjectMapper objectMapper;

    private String path;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        tasksRepository = (TasksRepository) servletContext.getAttribute("tasksRepository");
        objectMapper = new ObjectMapper();
        path = (String) servletContext.getAttribute("filepath");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = (String)req.getParameter("code").replaceAll("\t","    ");
        Long taskid = Long.parseLong(req.getParameter("taskid"));
        Long userid = ((User)req.getSession().getAttribute("user")).getId();

        if(taskid==null){
            req.setAttribute("errorCurrent", "Произошла внутренняя ошибка. Код ошибки: G-08");
            req.getRequestDispatcher("/errorCurrent.jsp").forward(req,resp);
        }else {
            Optional<Task> task = tasksRepository.findById(taskid);
            if(!task.isPresent()){
                req.setAttribute("errorCurrent", "Произошла внутренняя ошибка. Код ошибки: G-09");
                req.getRequestDispatcher("/errorCurrent.jsp").forward(req,resp);
            }else{
                Task c = task.get();

                String classname = code.replaceAll("public class ","~");
                try {
                    classname = classname.substring(classname.indexOf('~') + 1);
                    classname = classname.substring(0, classname.indexOf('{')).trim();
                } catch (Exception e){
                    classname = "error";
                }

                System.out.println("Я решил, что класс назывался "+classname);


                for(int i = 0; i < classname.length(); i++) {
                    char d = classname.charAt(i);
                    if(!((d>='a' && d<='z')||(d>='A' && d<='Z') || (d<='9' && d>='0') || (d=='_'))){

                        System.out.println("мне не понравился символ "+d);
                        classname = "WRONGCLASSNAME";
                        break;
                    }
                }

                File testing = new File(path+"testSources\\Z"+taskid+"\\"+classname+".java");
                if(!testing.exists()){
                    testing.createNewFile();
                }

                BufferedWriter bw = new BufferedWriter(new FileWriter(testing));
                bw.write(code);
                bw.close();

                //Tester t = new Tester("c:\\\\ptest\\Z1\\", classname);
                Tester t = new Tester(path +"testSources\\Z"+taskid+"\\",classname);
                System.out.println("ok1");
                try {
                    t.run_tests();
                    String s = t.getResult();

                    String lastTest = s.replaceAll("TEST: ", "~");
                    System.out.println("lastTest1: "+lastTest);
                    lastTest = lastTest.substring(lastTest.lastIndexOf('~'));
                    System.out.println("lastTest2: "+lastTest);
                    lastTest = lastTest.replaceAll("SUB:\n", "SUB: ");
                    String lasts[] = lastTest.split("\n");
                    Map<String, String> results = new HashMap<>();
                    for(String l : lasts){
                        if(l.contains(" "))
                        results.put(l.substring(0, l.indexOf(' ')), l.substring(l.indexOf(' ')+1));
                    }

                    String result = results.get("VERDICT:");
                    String msg = results.get("SUB:");
                    String test = lastTest.substring(1,lastTest.indexOf('\n'));

                    if(result.equals("WA")){
                        msg = "Wrong answer at test " + test + ".";
                    }else if(result.equals("RE") || result.equals("TL")){
                        msg = "Test " + test + ": " + msg;
                    }else if(result.equals("OK")){
                        msg = "Time: " + msg;
                    }else {
                        msg = "MSG: " + msg + "...";
                    }

                    Pack p = Pack.builder().lang("Java")
                            .score(Integer.parseInt(results.get("SCORE").substring(0, results.get("SCORE").indexOf('/'))))
                            .message(msg)
                            .result(result)
                            .date(new Date().toString())
                            .taskId(taskid)
                            .code(code)
                            .userId(userid)
                            .build();
                    System.out.println("ok2");
                    tasksRepository.savePack(p);

                    //resp.setStatus(303);
                    //System.out.println("Прошёл по 303-2");
                    //resp.setHeader("Location", "/seeCurrent?id="+taskid);
                    objectMapper.writeValue(resp.getWriter(), p);

                } catch (Exception e) {
                    req.setAttribute("errorCurrent", "Произошла внутренняя ошибка. Код ошибки: G-10. "+e.getMessage());
                    req.getRequestDispatcher("/errorCurrent.jsp").forward(req,resp);
                    e.printStackTrace();
                }

            }
        }
    }
}
