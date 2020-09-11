/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//select element
const selectElement = (s) => document.querySelector(s);
//open menu on click
selectElement('.open').addEventListener('click', () => {
        selectElement('.nav-list').classList.add('active');
});
//close menu on click
selectElement('.close').addEventListener('click', () => {
        selectElement('.nav-list').classList.remove('active');
});



