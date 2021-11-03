<%@ page import="ru.itis.models.Pack" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 23.09.2021
  Time: 23:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Все попытки</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <!--<a href="/justAuth">Назад</a>-->
    <%@include file="user-nav.jsp"%>
    <h2>Ваши попытки:</h2>
    <table border="1" class="pack-table">
        <tr>
            <th>#</th>
            <th>Номер задачи</th>
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
            <td><%=p.getTaskId()%></td>
            <td><%=p.getDate()%></td>
            <td><%=p.getLang()%></td>
            <td><%=p.getResult()%></td>
            <td><%=p.getMessage()%></td>
            <td><%=p.getScore()%></td>
            <td><a href="/viewCode?packid=<%=p.getId()%>">Просмотреть код</a></td>
        </tr>
        <%}%>
    </table>
</body>
</html>
