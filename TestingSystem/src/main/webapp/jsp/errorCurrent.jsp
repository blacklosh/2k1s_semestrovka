<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 23.09.2021
  Time: 21:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Упс...</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h1>О-о-опс!</h1>
    <h2>Что-то пошло не так :(</h2>
    <%=(String)request.getAttribute("errorCurrent")==null?"Код ошибки G-05":(String)request.getAttribute("errorCurrent")%>
    <a href="/justAuth">Вернуться на главную</a>
</body>
</html>
