<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 23.09.2021
  Time: 19:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<%@include file="user-nav.jsp"%>
    <%
        String err = (String) request.getAttribute("ErrorSignIn");
        if(err!=null && err.length()>1){
    %>
    <%=err%>
    <%}%>
    <form action="/checkSignIn" method="post">
        <input class="inp" type="text" name="nickname" placeholder="Введите логин..."/>
        <input class="inp" type="password" name="password" placeholder="Введите пароль..."/>
        <input class="button" type="submit" />
    </form>

</body>
</html>
