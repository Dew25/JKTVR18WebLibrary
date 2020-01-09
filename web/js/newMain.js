"use strict";
import {get,json,status} from './HttpModule.js';
function closeInfo(){
    document.getElementById("info").innerHTML = "";
}
setTimeout(closeInfo, 5000);
document.getElementById("enter").onclick = function(){
  let login = document.getElementById("login").value;
  let password = document.getElementById("password").value;
  let token=localStorage.getItem('token');
  let url = 'rest/loginControllerREST/loginRest';
  let data = [token, login, password];
  
  let response = get(url,data);
  response.then(status)
               .then(json)
               .catch(function(error) {  
                  console.log('Request failed', error);  
                })
               .then(function(res) {  
                  console.log('Request succeeded with JSON response: ', res.token); 
                  localStorage.setItem('token',res.token);
               });
};

