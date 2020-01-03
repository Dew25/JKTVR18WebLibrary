"use strict"
function closeInfo(){
    document.getElementById("info").innerHTML = "";
}
setTimeout(closeInfo, 5000);

function status(response) {  
  if (response.status >= 200 && response.status < 300) {  
    return Promise.resolve(response)  
  } else {  
    return Promise.reject(new Error(response.statusText))  
  }  
}

function json(response) {  
  return response.json()  
}
function searchAjax(){
  let url = "searchAjax?search="+document.getElementById("search").value;
  fetch(url)
          .then(status)  
          .then(json)  
          .then(function(data) {  
            printBooks(data);
            console.log('Request succeeded with JSON response', data);  
          }).catch(function(error) {  
            console.log('Request failed', error);  
          });
}
document.getElementById("search").addEventListener("keyup",searchAjax);


function printBooks(data){
  let str='';
  for(let i in data){
    str +=`
      <div class="card border-light m-3 card-inline">
        <a href="showBook?bookId=${data[i].book.id}">
          <img class="bookDataImg "  src="insertFile/${data[i].image.path}?key=cover" alt="Card image">
        </a>
          <div class="card-header">${data[i].book.title}</div>
        <div class="card-body">
          <h4 class="card-title">${data[i].book.author}. ${data[i].book.year}</h4>
          <p class="card-text">Цена: ${data[i].book.price}</p>
        </div>
      </div>
    `
  }
  document.getElementById('content').innerHTML = str;
  
   
  
} 
function editComment(commentId){
    let commentText = document.getElementById("text_"+commentId);
    let newCommentTextarea = document.createElement("textarea");
    newCommentTextarea.setAttribute("id","newText_"+commentId);
    newCommentTextarea.setAttribute("cols",70);
    newCommentTextarea.innerHTML=commentText.textContent;
    commentText.style.display="none";
    let parentCommentText=commentText.parentElement;
    let editDate = document.getElementById('editDate_'+commentId);
      if(editDate !== null){
        parentCommentText.insertBefore(newCommentTextarea,editDate);
      }else{
        parentCommentText.appendChild(newCommentTextarea);
      }
    let buttonEdit = document.getElementById("edit_"+commentId);
    buttonEdit.style.display = "none";
    let buttonChange = document.getElementById("change_"+commentId);
    buttonChange.style.display="block";

}
function sendCommentToServer(commentId){
    let commentAreatext = document.getElementById("newText_"+commentId);
    let url = "changeComment?commentId="+commentId+"&commentText="+commentAreatext.value;
    fetch(url)
      .then(status)  
      .then(json)  
      .then(function(data) {  
        changeComment(data,commentId);
        console.log('Request succeeded with JSON response', data);  
      }).catch(function(error) {  
        console.log('Request failed', error);  
      });
}

function changeComment(data,commentId){
  let commentText = document.getElementById("text_"+commentId);
  commentText.innerHTML=data[0];
  let commentAreatext = document.getElementById("newText_"+commentId);
  commentAreatext.parentNode.removeChild(commentAreatext);
  commentText.style.display = "block";
  if(data[1] !== ''){
    let editDate = document.getElementById('editDate_'+commentId);
    if(editDate !== null){
      editDate.parentNode.removeChild(editDate);
    }
    editDate = document.createElement("span");
    editDate.setAttribute("id","editDate_"+commentId);
    commentText.parentElement.insertBefore(editDate,commentText.nextSibling);
    editDate.innerHTML = '(Редактировано: '+data[1]+')';
    editDate.style.displey = "block";
  }

  let buttonChange = document.getElementById("change_"+commentId);
  buttonChange.style.display="none";
  let buttonEdit = document.getElementById("edit_"+commentId);
  buttonEdit.style.display = "block";
}

function addComment() {
  let bookId = document.getElementById('bookId');
  let commentTextarea = document.getElementById('commentTextarea');
  let data={
    bookId: bookId.value,
    commentText: commentTextarea.value
  }
  let url = "addComment";
  
  let response = fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json;charset=utf-8'
    },
    body: JSON.stringify(data)
  });
  response.then(status)  
          .then(json)  
          .then(function(data) {  
            insertComment(data);
            console.log('Request succeeded with JSON response', data);  
          })
          .catch(function(error) {  
            console.log('Request failed', error);  
          });
  commentTextarea.innerHTML='';
}
function insertComment(data) {
  let commentButton='';

    commentButton = 
            ` <input id="edit_${data.id}" class="edit-comment-btn" type="button" value="Редактировать" onclick="window.editComment(${data.id})"/>
              <input id="change_${data.id}" class="change-comment-btn w-3" type="button" value="Изменить" onclick="window.changeComment(${data.id})"/>
            `


  let commentHTML = 
    `<div class="card-header ">
              <span class="col-4 my-2 pull-left">
                ${data.createDate}
              </span>
              <span class="col-4  my-2 pull-right">
                  ${data.user.reader.name} ${data.user.reader.lastname}
              </span>
          </div>
          <div class="card-body min-vh-50">
            <p id="text_${data.id}" class="card-text">${data.commentText}</p>
          </div>`;

  let commentButtonElem = document.createElement("div");
  commentButtonElem.setAttribute("class","col-2 comment-btns pull-right");
  commentButtonElem.innerHTML = commentButton;

  let cart = document.createElement("div");
  cart.setAttribute("id","cart_"+data.id);
  cart.setAttribute("class", "card border-light m-3 col-6");
  cart.innerHTML = commentHTML;
  cart.visible = true;
  
  let commentsElem = document.getElementById("comments");
  commentsElem.appendChild(cart);
  commentsElem.insertBefore(commentButtonElem,cart.nextSubling);
  cart.style.display = "block";
  commentButtonElem.style.display = "block";
  let commentTextarea = document.getElementById("commentTextarea");
  commentTextarea.value = '';
}
document.getElementById("enter").onclick=function(){
    let login = document.getElementById("login").value;
    let password = document.getElementById("password").value;
    let url = "webresources/loginController/loginRest";
    let token = localStorage.getItem("token");
    let data = [login,password,token];
    let response = fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json;charset=utf-8'
      },
      body: JSON.stringify(data)
    });
    response.then(status)  
            .then(json)  
            .then(function(token) {  
              localStorage.setItem("token",token.data);
              console.log('Request succeeded with JSON response', token);  
            })
            .catch(function(error) {  
              console.log('Request failed', error);  
            });
    
}
