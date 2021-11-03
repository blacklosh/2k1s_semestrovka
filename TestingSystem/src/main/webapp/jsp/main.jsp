<%@ page import="ru.itis.models.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.itis.repository.FilesRepository" %>
<%@ page import="ru.itis.models.User" %><%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 23.09.2021
  Time: 20:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>

<%@include file="user-nav.jsp"%>

<div class="block-list">
    <h2>Список доступных задач:</h2>

    <% for(Task t : (List<Task>)request.getAttribute("tasks")) { %>

    <div class="block">
        <h3><%=t.getId()%>. <%=t.getTitle()%></h3>
        <form action="/seeCurrent?" method="get">
            <input type="hidden" value="<%=t.getId()%>" name="id">
            <input class="button" type="submit" value="Открыть"/>
        </form>
    </div>

    <%}%>

</div>
</body>
</html>
