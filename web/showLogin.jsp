<%-- 
    Document   : showLogin
    Created on : Oct 14, 2019, 3:12:56 PM
    Author     : Melnikov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

        <h1>Вход в систему</h1>
        <form action="login" method="POST">
            Логин: <input type="text" name="login">
            <br>
            Пароль: <input type="password" name="password">
            <br>
            <input type="submit" value="Войти">
        </form>
        <p>У вас нет логина? <a href="newReader">Зарегистрируйтесь</a></p>
    
