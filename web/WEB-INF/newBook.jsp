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
        <a href="index">Главная страница</a>
        <p>${info}</p>
        <a href="showUploadFile">Загрузить файл</a><br>
        <form action="addBook" method="POST">
            Название книги: <input type="text" name="title" value="${book.title}"><br>
            Автор книги: <input type="text" name="author" value="${book.author}"><br>
            Год издания книги: <input type="text" name="year" value="${book.year}"><br>
            Цена: <input type="text" name="price" value="${book.price}"><br>
            <select name="imageId">
                <option value="" hidden></option>
                <c:forEach var="image" items="${images}">
                    <option value="${image.id}">${image.description}</option>
                </c:forEach>
            </select>
            <input type="submit" value="Добавить новую книгу">
            <c:forEach var="image" items="${images}">
                <p>
                    <img src="insertFile/${image.path}">
                    <br>
                    ${image.description}
                </p>
            </c:forEach>
        </form>
    </body>
</html>
