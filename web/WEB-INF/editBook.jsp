<%-- 
    Document   : page1
    Created on : Sep 27, 2019, 10:59:53 AM
    Author     : Melnikov
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Новая книга</h1>
        <a href="index.jsp">Главная страница</a>
        <p>${info}</p>
        <form action="changeBook" method="POST">
            <input type="hidden" name="bookId" value="${book.id}">
            Название книги: <input type="text" name="title" value="${book.title}"><br>
            Автор книги: <input type="text" name="author" value="${book.author}"><br>
            Год издания книги: <input type="text" name="year" value="${book.year}"><br>
            Количество экземпляров: <input type="text" name="quantity" value="${book.quantity}"><br>
            Доступность книги: <input type="checkbox" name="active" <c:if test="${book.active == true}">checked</c:if>><br>
            <input type="submit" value="Изменить книгу">
        </form>
    </body>
</html>
