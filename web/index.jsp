<%@ page pageEncoding="UTF-8" %>
          <div id="content" class="row height-row">
            <c:forEach var="entry" items="${mapBookData}">
              <div class="card border-light m-3 card-inline">
                <a href="showBook?bookId=${entry.key.id}">
                  <img class="indexBookDataImg "  src="insertFile/${entry.value}?key=cover" alt="Card image">
                </a>
                  <div class="card-header">${entry.key.title}</div>
                <div class="card-body">
                  <h4 class="card-title">${entry.key.author}. ${entry.key.year}</h4>
                  <p class="card-text">Цена: ${entry.key.price}</p>
                </div>
              </div>
            </c:forEach>
          </div>
    
        
      
