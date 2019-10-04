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
        <title>Вернуть книгу</title>
    </head>
    <body>
        <h1>Вернуть книгу</h1>
        <p>${info}</p>
        Список книг:<br>
        <form action="returnOnBook" method="POST">
            <select name="historyId">
                <option value="" hidden>Выберите книгу</option>
                <c:forEach var="history" items="${listHistories}">
                    <option value="${history.id}">Название книги: ${history.book.title}. Читатель: ${history.reader.name} ${history.reader.lastname}</option>
                </c:forEach>
            </select>
            <br>
            <input type="submit" value="Вернуть книгу">
        </form>
    </body>
</html>
