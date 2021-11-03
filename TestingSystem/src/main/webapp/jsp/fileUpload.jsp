<%--
  Created by IntelliJ IDEA.
  User: Master5
  Date: 01.11.2021
  Time: 23:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Загрузка аватара</title>
</head>

<%@include file="user-nav.jsp"%>

<body>
Разрешены только JPG / PNG!
<form action="/file-upload" method="post" enctype="multipart/form-data">
    <input class="button" type="file" name="file" accept="image/jpeg,image/png"/>
    <input class="button" type="submit" value="Send!"/>
</form>
</body>
</html>
