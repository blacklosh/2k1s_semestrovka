<%@ page import="ru.itis.models.Pack" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.itis.models.Task" %>
<%@ page import="ru.itis.models.User" %><%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 23.09.2021
  Time: 22:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Просмотр задачи</title>
    <link rel="stylesheet" href="/css/style.css">

    <script src="/js/j.js"></script>
    <script src="/js/packageManager.js"></script>
</head>
<body>
    <!--<a class="a" href="/justAuth">Назад</a>-->
    <%@include file="user-nav.jsp"%>

    <% Task task = (Task)request.getAttribute("task");%>

    <h1><%=task.getId()%>. <%=task.getTitle()%></h1>
    <%=task.getDescription()%>

    <h2>Ваши попытки:</h2>
    <table border="1" id="pack-list" class="pack-table">
        <tr>
            <th>#</th>
            <th>Время</th>
            <th>Язык</th>
            <th>Результат</th>
            <th>Сообщение</th>
            <th>Баллы</th>
            <th>Код</th>
        </tr>

        <%
            for(Pack p : (List<Pack>)request.getAttribute("currentPacks")){
        %>

        <tr>
            <td><%=p.getId()%></td>
            <td><%=p.getDate()%></td>
            <td><%=p.getLang()%></td>
            <td><%=p.getResult()%></td>
            <td><%=p.getMessage()%></td>
            <td><%=p.getScore()%></td>
            <td><a href="/viewCode?packid=<%=p.getId()%>">Просмотреть код</a></td>
        </tr>
        <%}%>
    </table>

<h2>Сдать решение:</h2>

Поддерживается только язык Java, compiler 1.8<br/>
    Не указывайте package!<br/>

<form id="form" action="/compile" method="post">
    <input id="taskid" type="hidden" name="taskid" value="<%=task.getId()%>">
    <textarea id="content" class="code" name="code"></textarea>
    <input class="button" type="submit" value="Отправить!"/>
</form>

<hr/>

    <% List<User> passed = (List<User>) request.getAttribute("passed"); %>

<h2>Эту задачу успешно сдали <%=passed.size()%> человек</h2>

<% for(User u : passed) { %>
    <div>
        <a href="/profile?see-id=<%=u.getId()%>">
                <%=u.getNickname()%>
            <% Long path = u.getAvatarId();
                if(path==null || path.equals(0) || path==0){
            %>
            <img class="avatar" src="/no-avatar.png" />
            <% }else{ %>
            <img class="avatar" src="/files/<%=path%>" />
            <%}%>
        </a>
    </div>
<%}%>

</body>
</html>
