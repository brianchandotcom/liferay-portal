const btnBack = document.getElementById("custom-back-button");
console.log(btnBack);
btnBack.addEventListener("click",function(){
   history.back();
});