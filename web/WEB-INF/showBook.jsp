<%-- 
    Document   : listAllBooks
    Created on : Oct 9, 2019, 9:21:06 AM
    Author     : Melnikov
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Чтение книги</title>
    </head>
    <body>
      <div class="container">
        <h1>${book.title}</h1>
        <a href="index">Главная страница</a><br>
        <img src="insertFile/${image.path}?key=cover"><br>
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
        <div>
          <h2>Коментарии к книге</h2>
          <c:forEach var="comment" items="${comments}">
            <div class="card border-light m-3 col-4">
              <div class="card-header ">
                  <span class="col-4 my-2 pull-left">
                    <fmt:formatDate value="${comment.date}"  pattern="dd.MM.yyyy"/>
                  </span>
                  <span class="col-4  my-2 pull-right">
                      ${comment.user.reader.name} ${comment.user.reader.lastname}
                  </span>
              </div>
                <div class="card-body">
                  <p id="${comment.id}" class="card-text">${comment.commentText}</p>
                </div>
            </div>
              <c:if test="${user eq comment.user}">
                  <button onclick="editComment(${comment.id})">Редактировать</button>
              </c:if>
          </c:forEach>
          
          <form action="addComment" method="POST">
            <input type="hidden" name="bookId" value="${book.id}">
            <textarea name="commentText" cols="30" rows="5"></textarea>
            <br>
            <button type="submit">Добавить</button>
          </form>
        </div>
      </div>
    </body>
</html>
