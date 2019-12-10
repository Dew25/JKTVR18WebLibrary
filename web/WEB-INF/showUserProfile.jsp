<%@page contentType="text/html" pageEncoding="UTF-8"%>

      <div class="row profileWrap">
          <h1 text-center>Профиль пользователя</h1>
      </div>
      <div class="row profileWrap">
        <form action="changeReader" method="POST">
          <div class="form-group">
            <label for="name">Имя читателя</label>
            <input type="text" class="form-control"  value="${user.reader.name}" name="name" id="name" placeholder="Имя пользователя">
          </div>
          <div class="form-group">
            <label for="lastname">Фамилия читателя</label>
            <input type="text" class="form-control" name="lastname" value="${user.reader.lastname}" id="lastname" placeholder="Фамилия пользователя">
          </div>
          <div class="form-group">
            <label for="year">Год рождения</label>
            <input type="text" class="form-control" name="year" value="${user.reader.year}" id="year" placeholder="Год рождения пользователя">
          </div>  
          <div class="form-group">
            <label for="cash">Кошелек</label>
            <input type="text" class="form-control" name="cash" value="${user.reader.cash}" id="cash" placeholder="Количество денег у пользователя">
          </div>  
          <div class="form-group">
            <label for="login">Логин</label>
            <input type="text" class="form-control" id="login" name="login" aria-describedby="emailHelp" placeholder="Enter email">
          </div>
          <div class="form-group">
            <label for="password1">Пароль</label>
            <input type="password" class="form-control" id="password1" name="password1" placeholder="Password">
          </div>  
          <div class="form-group">
            <label for="password2">Повторить пароль</label>
            <input type="password" class="form-control" id="password2" name="password2" placeholder="Password repeat">
          </div>  
          <button type="submit" class="btn btn-primary">Внести изменения</button>
        </form>
      </div> 
        <div class="lead text-center mt-5 profileWrap">
            <p>Купленные книги:</p>
            <c:forEach var="bd" items="${listBooksData}">
              <div class="card border-light m-3 card-inline">
                <a href="insertFile/${bd.text.fileName}?key=file">
                  <img class="bookDataImg "  src="insertFile/${bd.image.path}?key=cover" alt="Card image">
                </a>
                  <div class="card-header">${bd.book.title}</div>
                <div class="card-body">
                  <h4 class="card-title">${bd.book.author}. ${bd.book.year}</h4>
                </div>
              </div>
            </c:forEach>
        </div>
                   
