<%@ page import="ru.itis.models.User" %><%--
  Created by IntelliJ IDEA.
  User: Master5
  Date: 01.11.2021
  Time: 20:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" href="/css/style.css">
</head>

<body>
<div class="head">
    <a href="/justAuth">
        <h1>Система 1.0</h1>
    </a>

</div>

<% User currentUser = (User) session.getAttribute("user"); %>

<% if(currentUser!=null) { %>

<div class="user-navigation">

    <span>

        <a class="navigation_button a" href="/logout">Выйти</a>
        <a class="navigation_button a" href="/file-upload">Изменить аватар</a>
        <a class="navigation_button a" href="/listAllUserPacks">Посмотреть все свои попытки</a>

        <% Long path = currentUser.getAvatarId();
            if(path==null || path.equals(0)  || path==0){
        %>
        <img class="avatar navigation_button" src="/no-avatar.png" />
        <% }else{ %>
        <img class="avatar navigation_button" src="/files/<%=path%>" />
        <%}%>

        <a class="navigation_button" href="/profile?see-id=<%=currentUser.getId()%>">
            <%=currentUser.getNickname()%>
        </a>
    </span>
</div>

<%}%>
</body>
</html>
