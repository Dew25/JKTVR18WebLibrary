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
        newCommentTextarea.innerHTML=commentText.textContent;
        commentText.style.display="none";
        let parentCommentText=commentText.parentElement;
        parentCommentText.appendChild(newCommentTextarea);
        let buttonEdit = document.getElementById("edit_"+commentId);
        buttonEdit.style.display = "none";
        let buttonChange = document.getElementById("change_"+commentId);
        buttonChange.style.display="block";
        
    }
    function changeComment(commentId){
        let commentAreatext = document.getElementById("newText_"+commentId);
        let url = "changeComment?commentId="+commentId+"&commentText="+commentAreatext.textContent;
        fetch(url)
          .then(status)  
          .then(json)  
          .then(function(data) {  
            printComment(data,commentId);
            console.log('Request succeeded with JSON response', data);  
          }).catch(function(error) {  
            console.log('Request failed', error);  
          });
    }
    function printComment(data,commentId){
        let commentText = document.getElementById("text_"+commentId);
        commentText.innerHTML=data;
        let commentAreatext = document.getElementById("newText_"+commentId);
        commentAreatext.style.display = "none";
        commentText.style.distplay = "block";
        let buttonEdit = document.getElementById("edit_"+commentId);
        buttonEdit.style.display = "block";
        let buttonChange = document.getElementById("change_"+commentId);
        buttonChange.style.display="none";
        
    }