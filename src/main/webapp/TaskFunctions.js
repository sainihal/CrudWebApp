
function addTask()
{
    var task = new Object();
    task.name = $('#name').val();
    task.description = $('#description').val();
    task.dueDate = ($("#date").val());
    task.personId =  parseInt($('#options').val(), 10);
    var date = new Date(task.dueDate)
    task.dueDate = date.getTime();
    	var xmlHttpRequest = getXMLHttpRequest();
    	xmlHttpRequest.onreadystatechange = function() {
            if (xmlHttpRequest.readyState == 4) {
                    if (xmlHttpRequest.status == 200) {
                           alert("task added");
                           $('#name').val("");
                           $('#description').val("");
                           $('#date').val("");
                        } else {
                        alert("HTTP error " + xmlHttpRequest.status + ": " + xmlHttpRequest.statusText);
                        }
                    }
            };
    	xmlHttpRequest.open("POST", "Task", true);
    	xmlHttpRequest.setRequestHeader("Content-Type","multipart/form-data");
    	xmlHttpRequest.send(JSON.stringify(task));
}

function getXMLHttpRequest() {
        var xmlHttpReq = false;
    	if (window.XMLHttpRequest) {
    		xmlHttpReq = new XMLHttpRequest();
    	} else if (window.ActiveXObject) {
    		try {
    			xmlHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
    		} catch (exp1) {
    			try {
    				xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
   			} catch (exp2) {
    				xmlHttpReq = false;
    			}
    		}
    	}
    	return xmlHttpReq;
}

function viewAllTasks(){
 var xmlHttpRequest = getXMLHttpRequest();
     	xmlHttpRequest.onreadystatechange = function() {
             if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
                      $("tr:has(td)").remove();
                    $.each(JSON.parse(xmlHttpRequest.responseText), function () {
                      var task = new Object();
                          $.each(this, function (name, value) {
                         task[name] = value;
                        });
                        if(task.status == "INCOMPLETE"){
                         addToTable(task);
                         }else{
                         addToTable1(task);
                         }
         });
        alert("get all success")
        }
        }
     	xmlHttpRequest.open("GET", "Task", true);
     	xmlHttpRequest.send();
}

function getTaskByID(id){
 var xmlHttpRequest = getXMLHttpRequest();
     	xmlHttpRequest.onreadystatechange = function() {
             if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
             console.log(xmlHttpRequest.responseText)
             var data = JSON.parse(xmlHttpRequest.responseText)
               $('#name').val(data.name);
               $('#description').val(data.description);
               var date = new Date(data.dueDate);
               console.log((date.getFullYear()+"-"+date.getMonth()+"-"+date.getDay()))
               $('#date').val((date.getFullYear()+"-"+("0" + (date.getMonth() + 1)).slice(-2)+"-"+("0" + date.getDate()).slice(-2)))
             }
        }
     	xmlHttpRequest.open("GET", "Task/"+id, true);
     	xmlHttpRequest.setRequestHeader("Content-Type","application/json");
     	xmlHttpRequest.send();
}
function updateTask(id){
          var task = new Object();
          task.id = id;
          	var xmlHttpRequest = getXMLHttpRequest();
          	xmlHttpRequest.onreadystatechange = function() {
                  if (xmlHttpRequest.readyState == 4) {
                          if (xmlHttpRequest.status == 200) {
                                viewAllTasks();
                                alert("update success");
                              } else {
                              alert("HTTP error " + xmlHttpRequest.status + ": " + xmlHttpRequest.statusText);
                              }
                          }
                  };
          	xmlHttpRequest.open("PUT", "Task", true);
          	xmlHttpRequest.setRequestHeader("Content-Type",
          			"application/json");
          	xmlHttpRequest.send(JSON.stringify(task));
}
function deleteTask(id){
    var xmlHttpRequest = getXMLHttpRequest();
           	xmlHttpRequest.onreadystatechange = function() {
                     if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
                                 viewAllTasks();
                                 alert("delete success");
                }
                }
             	xmlHttpRequest.open("DELETE", "Task/"+id, true);
             	xmlHttpRequest.setRequestHeader("Content-Type","application/json");
             	xmlHttpRequest.send();
}
function addToTable(task){
           $("#added-articles").append($('<tr/>')
           .append($('<td/>').html("id: "+task.id+" name: "+task.name+" description:  "+task.description))
           .append('<td><input type="submit" onclick="deleteTask('+task.id+')" value="delete"> </td>')
           .append('<td><input type="submit" onclick="updateTask('+task.id+')" value="completed" name = "completed"></td>')
           );

}

function addToTable1(task){
           $("#added-articles").append($('<tr/>')
          .append($('<td/>').html("id: "+task.id+" name: "+task.name+" description:  "+task.description))
          .append('<td><input type="submit" onclick="deleteTask('+task.id+')" value="delete"> </td>')
          .append(($('<td/>').html("task completed")))

          );
}



