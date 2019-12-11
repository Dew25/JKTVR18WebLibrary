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
document.getElementById("search").addEventListener("change",searchAjax);
function printBooks(data){
  let str='';
  for(let i in data){
    str +=`
      <div class="card border-light m-3 card-inline">
        <a href="showBook?bookId=${data[i].id}">
          <img class="bookDataImg "  src="#" alt="Card image">
        </a>
          <div class="card-header">${data[i].title}</div>
        <div class="card-body">
          <h4 class="card-title">${data[i].author}. ${data[i].year}</h4>
          <p class="card-text">Цена: ${data[i].price}</p>
        </div>
      </div>
    `
  }
  document.getElementById('content').innerHTML = str;
}