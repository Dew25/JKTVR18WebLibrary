<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <div id="show-book-wrap">
        <h1>${book.title}</h1>
        <a href="index">Главная страница</a><br>
        <img class="bookDataImg" src="insertFile/${image.path}?key=cover">
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
        <div id="comments" class="comments mb-15">
          <h2>Коментарии к книге</h2>
          <c:forEach var="comment" items="${comments}">
            <div id="card_${comment.id}" class="card border-light m-3 col-6">
              <div class="card-header ">
                  <span class="col-4  pull-left">
                    <fmt:formatDate value="${comment.createDate}"  pattern="dd.MM.yyyy HH:mm:ss"/>
                  </span>
                  <span class="col-4  pull-right">
                      ${comment.user.reader.name} ${comment.user.reader.lastname}
                  </span>
              </div>
                <div class="card-body min-vh-50">
                  <p id="text_${comment.id}" class="card-text">${comment.commentText}</p>
                  <span id="editDate_${comment.id}">
                    <c:if test="${comment.lastEditDate ne null}">
                        (Редактированно: <fmt:formatDate value="${comment.lastEditDate}"  pattern="dd.MM.yyyy HH:mm:ss"/>)
                    </c:if>
                    
                  </span>
                </div>
                
            </div>
            <div class="col-2 comment-btns mb-15">  
                <c:if test="${user eq comment.user}">
                  <input id="edit_${comment.id}" class="edit-comment-btn w-2" type="button" value="Редактировать" onclick="window.editComment(${comment.id})"/>
                  <input id="change_${comment.id}" class="change-comment-btn w-2" type="button" value="Изменить" onclick="window.sendCommentToServer(${comment.id})"/>
                </c:if>
            </div>
          </c:forEach>
          
          
        </div>
        <div id="addComment" class="card border-light m-3 col-6">
          <div class="card-body min-vh-50">
            <input type="hidden" id="bookId" value="${book.id}"><br>
            <textarea class="addCommentText" id="commentTextarea" cols="100" rows="3"></textarea>
          </div>
          <div class="col-2 comment-btns  pull-right"> 
            <input type="button" onclick="window.addComment()" value="Добавить">
          </div>
        </div>
    </div>
            
