<%-- 
    Document   : listAllBooks
    Created on : Oct 9, 2019, 9:21:06 AM
    Author     : Melnikov
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Чтение книги</title>
    </head>
    <body>
        <h1>${book.title}</h1>
        <a href="index">Главная страница</a><br>
        <img src="insertFile/${image.path}"><br>
        <p>${image.description}</p>
        Автор: ${book.author}<br>
        Год издания: ${book.year}<br>
        Цена книги: ${book.price}<br>
        Доступность книги: <c:if test="${book.active == true}">доступна для читателей</c:if>
        <c:if test="${book.active == false}">неактивна</c:if>
        <br>
        <a href="createHistory?bookId=${book.id}">Купить книгу</a>
        <c:if test="${'MANAGER' eq userRole || 'ADMIN' eq userRole}">
            <a href="editBook?bookId=${book.id}">Редактировать</a>
        </c:if>
    </body>
</html>
