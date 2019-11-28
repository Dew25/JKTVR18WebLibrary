<%-- 
    Document   : sowUserProfile
    Created on : Nov 6, 2019, 2:31:59 PM
    Author     : Melnikov
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Пользователь ${user.login}</title>
    </head>
    <body>
        <h1>Профиль пользователя</h1>
        <a href="index">Главная страница</a>
        <p>${info}</p>
        <form action="changeReader" method="POST">
            Имя читателя: <input type="text" name="name" value="${user.reader.name}"><br>
            Фамилия читателя: <input type="text" name="lastname" value="${user.reader.lastname}"><br>
            День рождения: <input type="text" name="day" value="${user.reader.day}"><br>
            Месяц рождения: <input type="text" name="month" value="${user.reader.month}"><br>
            Год рождения: <input type="text" name="year" value="${user.reader.year}"><br>
            Кошелек: <input type="text" name="cash" value="${user.reader.cash}"><br>
            Логин: <input type="text" name="login" value="${user.login}"><br>
            Пароль: <input type="password" name="password1" value=""><br>
            Повторить пароль: <input type="password" name="password2" value=""><br>
            <input type="submit" value="Внести изменения">
            <br>
            Купленные книги:
            <br>
            <c:forEach var="entry" items="${boughtBooksMap}">
                <a href="insertFile/${entry.key}?key=file">${entry.value}</a><br>
            </c:forEach>
        </form>
    </body>
</html>
