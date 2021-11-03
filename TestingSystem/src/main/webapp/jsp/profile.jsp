<%@ page import="ru.itis.models.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Optional" %><%--
  Created by IntelliJ IDEA.
  User: Master5
  Date: 02.11.2021
  Time: 21:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Редактирование профиля</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>

<%@include file="user-nav.jsp"%>

<%
    Optional<User> optionalUser = (Optional<User>)request.getAttribute("see-user");
    List<Task> completed = (List<Task>)request.getAttribute("completed");
    List<Task> started = (List<Task>)request.getAttribute("started");

    if(!optionalUser.isPresent()) {
%>

<h2>Упс!.. Такого пользователя не найдено:(</h2>

<%}else{ User user = optionalUser.get();%>

<h1>
    <%=user.getNickname()%>
    <% Long path = user.getAvatarId();
        System.out.println(path);
        if(path==null || path.equals(0) || path==0){
    %>
    <img class="avatar2" src="/no-avatar.png" />
    <% }else{ %>
    <img class="avatar2" src="/files/<%=path%>" />
    <%}%>
</h1>

<h2>Решено задач: <%=completed.size()%></h2>

<% for(Task t : completed) { %>
    <div>
        <%=t.getId()%>. <%=t.getTitle()%>
        <a href="/seeCurrent?id=<%=t.getId()%>">Перейти к задаче</a>
    </div>
<% } %>

<h2>Начато, но не решено задач: <%=started.size()%></h2>

<% for(Task t : started) { %>
<div>
    <%=t.getId()%>. <%=t.getTitle()%>
    <a href="/seeCurrent?id=<%=t.getId()%>">Перейти к задаче</a>
</div>
<% }} %>

</body>
</html>
