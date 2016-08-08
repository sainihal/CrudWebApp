function GetURLParameter(sParam)
{
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) 
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) 
        {
            return sParameterName[1];
        }
    }
    return null;
}


function getEmployeeDetails(){
var thisField = document.getElementById('id');
var value = GetURLParameter('id');
if(value != null){
thisField.innerHTML = value;
var employee = getEmployeeByID(value);
getImage(value);
 }
console.log("done");
console.log(GetURLParameter('id'));
}


function process(){
var value = GetURLParameter('id');
if(value == null)
{
console.log("called add employee");
addEmployee();
}
else{
console.log("called update employee employee");
updateEmployee(value)
}
}

function processTask(){
var value = GetURLParameter('id');
if(value == null)
{
console.log("called add employee");
addTask();
}
else{
console.log("called update employee employee");
updateTask(value)
}
}
function getTaskDetails(){
var thisField = document.getElementById('id');
var value = GetURLParameter('id');
getAvailableEmployees();
if(value != null){
thisField.innerHTML = value;
getTaskByID(value);
 }
console.log("done");
console.log(GetURLParameter('id'));
}


function getAvailableEmployees(){
 var xmlHttpRequest = getXMLHttpRequest();
     	xmlHttpRequest.onreadystatechange = function() {
             if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
             var s = $('<select id="options" name="options"/>');
                    $.each(JSON.parse(xmlHttpRequest.responseText), function () {
                      var employee = new Object();
                          $.each(this, function (name, value) {
                         $('<option />', {value: value, text: value}).appendTo(s);
                         return false;
                        });
         });
         s.appendTo('#dropdown');
        alert("get all success")
        }
        }
     	xmlHttpRequest.open("GET", "Employee", true);
     	xmlHttpRequest.setRequestHeader("Content-Type","application/json");
     	xmlHttpRequest.send();
}



