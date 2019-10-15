<%-- 
    Document   : index
    Created on : Oct 14, 2019, 1:33:00 PM
    Author     : Melnikov
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Сетевая библиотека группы JKTVR18</title>
    </head>
    <body>
        <H1>СЕТЕВАЯ БИБЛИОТЕКА</H1>
        <p>${info}</p>
        <c:if test="${user == null}">
           <a href="showLogin">Войти</a><br>
        </c:if> 
        <c:if test="${user != null}">
           <a href="logout">Выйти</a><br>
        </c:if>
        <a href="newBook">Новая книга</a><br>
        <a href="newReader">Новый читатель</a><br>
        <a href="takeOnBook">Выдать книгу</a><br>
        <a href="returnBook">Вернуть книгу</a><br>
        <a href="showListAllBooks">Все книги библиотеки</a><br>
    </body>
</html>
