<%@ page import="ru.itis.models.Pack" %><%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 23.09.2021
  Time: 23:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Просмотр кода</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <%@include file="user-nav.jsp"%>
    <!--<a href="/justAuth">Назад</a><br/>-->
    <% Pack p = (Pack)request.getAttribute("currentPack"); %>
    Посылка: <%=p.getId()%> <br/>
    Задача: <%=p.getTaskId()%> <br/>
    Код:<br/> <%=p.getCode().replaceAll("\n","<br/>")%> <br/>
</body>
</html>