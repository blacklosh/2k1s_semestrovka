<%@ page import="ru.itis.models.User" %><%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 23.09.2021
  Time: 20:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/css/style.css"/>
</head>
<body>
<%@include file="/jsp/user-nav.jsp"%>
<br/><br/><br/>
    <a class="a" href="/checkSignIn">Войти</a>
    <%
        User currentUser2 = (User) session.getAttribute("user");
        if(currentUser2==null){
    %>
        <a class="a" href="/jsp/sign-up.jsp">Регистрация</a>
    <%
        }else{
    %>
        (Вход выполнен)
    <%}%>
</body>
</html>
