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
        <title>Список всех книг</title>
    </head>
    <body>
        <h1>Книги библиотеки:</h1>
        <a href="index">Главная страница</a>
            <ul>
                <c:forEach var="book" items="${listAllBooks}" varStatus="num">
                    <li>
                        ${num.index + 1}. ${book.title}. Автор: ${book.author}. ${book.year}
                        <c:if test="${'ivan' eq user.login}">
                            <c:if test="${book.active eq true}">
                                активно <a href="changeActiveBook?bookId=${book.id}&active=${book.active}">Деактивировать</a>
                            </c:if>
                            <c:if test="${book.active ne true}">
                                не активно <a href="changeActiveBook?bookId=${book.id}&active=${book.active}">Активировать</a>
                            </c:if> 
                            
                        </c:if>
                        <c:if test="${user ne null}">
                            <a href="showBook?bookId=${book.id}">Читать</a>
                        </c:if>
                    </li>
                </c:forEach>
            </ul>
            <br>
    </body>
</html>
