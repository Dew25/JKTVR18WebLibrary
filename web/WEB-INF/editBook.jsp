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
        <h1>Редактирование книги</h1>
        <a href="index">Главная страница</a>
        <p>${info}</p>
        <img src="insertFile/${booksData.image.path}?key=cover"><br>
        <form action="changeBook" method="POST">
            <input type="hidden" name="bookId" value="${booksData.book.id}">
            Название книги: <input type="text" name="title" value="${booksData.book.title}"><br>
            Автор книги: <input type="text" name="author" value="${booksData.book.author}"><br>
            Год издания книги: <input type="text" name="year" value="${booksData.book.year}"><br>
            Цена книги: <input type="text" name="price" value="${booksData.book.price}"><br>
            Доступность книги: <input type="checkbox" name="active" <c:if test="${booksData.book.active == true}">checked</c:if>><br>
            <select name="imageId">
                <c:forEach var="image" items="${listImages}">
                    <option 
                      value="${image.id}" 
                      <c:if test="${booksData.image.id == image.id}">selected</c:if>      
                    >${image.description}</option>
                </c:forEach>
            </select>
            <select name="textId">
                <c:forEach var="text" items="${listTexts}">
                    <option 
                      value="${text.id}"
                      <c:if test="${booksData.text.id == text.id}">selected</c:if>
                    >${text.description}</option>
                </c:forEach>
            </select>
            <input type="submit" value="Изменить книгу">
        </form>
    </body>
</html>
