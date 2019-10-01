<%-- 
    Document   : page1
    Created on : Sep 27, 2019, 10:59:53 AM
    Author     : Melnikov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Новая книга</h1>
        <p>${info}</p>
        <form action="addBook" method="POST">
            Название книги: <input type="text" name="title"><br>
            Автор книги: <input type="text" name="author"><br>
            Год издания книги: <input type="text" name="year"><br>
            Количество экземпляров: <input type="text" name="quantity"><br>
            <input type="submit" value="Добавить новую книгу">
        </form>
    </body>
</html>
