<%-- 
    Document   : newReader
    Created on : Oct 2, 2019, 9:00:53 AM
    Author     : Melnikov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Новый читатель</title>
    </head>
    <body>
        <h1>Запись читателя в библиотеку</h1>
        <a href="index">Главная страница</a>
        <p>${info}</p>
        <form action="addReader" method="POST">
            Имя читателя: <input type="text" name="name" value="${name}"><br>
            Фамилия читателя: <input type="text" name="lastname" value="${lastname}"><br>
            День рождения: <input type="text" name="day" value="${day}"><br>
            Месяц рождения: <input type="text" name="month" value="${month}"><br>
            Год рождения: <input type="text" name="year" value="${year}"><br>
            Кошелек: <input type="text" name="cash" value="${cash}"><br>
            Логин: <input type="text" name="login" value="${login}"><br>
            Пароль: <input type="password" name="password1" value=""><br>
            Повторить пароль: <input type="password" name="password2" value=""><br>
            <input type="submit" value="Добавить нового читателя">
        </form>
    </body>
</html>
