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
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="css/bootstrap.css">
        <title>Сетевая библиотека группы JKTVR18</title>
    </head>
    <body>
        <H1>СЕТЕВАЯ БИБЛИОТЕКА</H1>
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
          <a class="navbar-brand" href="#">Меню</a>
          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>

          <div class="collapse navbar-collapse" id="navbarColor01">
            <ul class="navbar-nav mr-auto">
              <li class="nav-item active">
                <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Features</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Pricing</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">About</a>
              </li>
            </ul>
            <form class="form-inline my-2 my-lg-0">
              <input class="form-control mr-sm-2" type="text" placeholder="Search">
              <button class="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
            </form>
            <ul class="navbar-nav">
              <c:if test="${null == user}">
                <li class="nav-item">
                  <a class="nav-link" href="showLogin">Войти</a>
                </li>
              </c:if>
              <c:if test="${null != user}">
                <li class="nav-item">
                  <a class="nav-link" href="logout">Выйти</a>
                  
                </li>
                <li class="nav-item">
                  <a class="nav-link"  href="showUserProfile">Профиль</a>
                </li>
              </c:if>
            </ul>
          </div>
        </nav>
        <div class="alert alert-dismissible alert-success">
          <button type="button" class="close" data-dismiss="alert">&times;</button>
          ${info}
        </div>

        
        <a href="newReader">Регистрация</a><br>
        <a href="showListAllBooks">Все книги библиотеки</a><br>
        <br>
        <c:if test="${'ADMIN' eq userRole || 'MANAGER' eq userRole}">
            <a href="newBook">Новая книга</a><br>
            <a href="showProfit">Прибыль</a>
           
        </c:if>
        <c:if test="${'ADMIN' eq userRole}">    
            <a href="showChangeUserRole">Изменить роль пользователю</a>
        </c:if>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>    
<script src="js/main.js"></script>
    </body>
</html>
