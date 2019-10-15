<%-- 
    Document   : newjsp
    Created on : Oct 4, 2019, 9:19:57 AM
    Author     : Melnikov
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Выдать книгу</title>
    </head>
    <body>
        <h1>Выдать книгу</h1>
        <a href="index.jsp">Главная страница</a>
        <p>${info}</p>
        Список книг:<br>
        <form action="createHistory" method="POST">
            <select name="bookId">
                <option value="" hidden>Выберите книгу</option>
                <c:forEach var="book" items="${listBooks}">
                    <option value="${book.id}">Название: ${book.title}. Автор: ${book.author}. ${book.year}. Количество: ${book.quantity}</option>
                </c:forEach>
            </select>
            <br>
            Список читателей:<br>
            <select name="readerId">
                <option value="" hidden>Выберите читателя</option>
                <c:forEach var="reader" items="${listReaders}">
                    <option value="${reader.id}">${reader.name} ${reader.lastname}</option>
                </c:forEach>
            </select>
            <br>
            <input type="submit" value="Выдать книгу">
        </form>
    </body>
</html>
