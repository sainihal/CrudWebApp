
function addEmployee() {
    var employee = new Object();
    employee.name = $('#name').val();
    employee.role = $('#description').val();
    employee.address = $('#address').val();
    	var xmlHttpRequest = getXMLHttpRequest();
    	xmlHttpRequest.onreadystatechange = function() {
            if (xmlHttpRequest.readyState == 4) {
                    if (xmlHttpRequest.status == 200) {
                        addToTable(JSON.parse(xmlHttpRequest.responseText));
                           alert("employee added");
                           $('#name').val("");
                           $('#description').val("");
                           $('#address').val("");
                        } else {
                        alert("HTTP error " + xmlHttpRequest.status + ": " + xmlHttpRequest.statusText);
                        }
                    }
            };
    	xmlHttpRequest.open("POST", "Employee", true);
    	xmlHttpRequest.setRequestHeader("Content-Type","multipart/form-data");
    	xmlHttpRequest.send(JSON.stringify(employee));
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

function viewAllEmployees(){
 var xmlHttpRequest = getXMLHttpRequest();
     	xmlHttpRequest.onreadystatechange = function() {
             if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
                      $("tr:has(td)").remove();
                    $.each(JSON.parse(xmlHttpRequest.responseText), function () {
                      var employee = new Object();
                          $.each(this, function (name, value) {
                         employee[name] = value;
                        });
                         addToTable(employee);
         });
        alert("get all success")
        }
        }
     	xmlHttpRequest.open("GET", "Employee", true);
     	xmlHttpRequest.setRequestHeader("Content-Type","application/json");
     	xmlHttpRequest.send();
}

function getEmployeeByID(id){
console.log("method is hit")
console.log(id)
 var xmlHttpRequest = getXMLHttpRequest();
     	xmlHttpRequest.onreadystatechange = function() {
             if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
             console.log(xmlHttpRequest.responseText)
             var data = JSON.parse(xmlHttpRequest.responseText)
               $('#name').val(data.name);
               $('#description').val(data.role);
               $('#address').val(data.address);
                    console.log("address is")
                              console.log($('#address').val())
               $('#empid').val(data.id)
               console.log("id is")
               console.log($('#empid').val())
             }
        }
     	xmlHttpRequest.open("GET", "Employee/"+id, true);
     	xmlHttpRequest.setRequestHeader("Content-Type","application/json");
     	xmlHttpRequest.send();
}

function updateEmployee(id){
            console.log("update employee called ")
          var employee = new Object();
          employee.id = id;
          employee.name = $('#name').val();
          employee.role = $('#description').val();
          employee.address = $('#address').val();

          	var xmlHttpRequest = getXMLHttpRequest();
          	xmlHttpRequest.onreadystatechange = function() {
                  if (xmlHttpRequest.readyState == 4) {
                          if (xmlHttpRequest.status == 200) {
                              alert("update success");
                              } else {
                              alert("HTTP error " + xmlHttpRequest.status + ": " + xmlHttpRequest.statusText);
                              }
                          }
                  };
          	xmlHttpRequest.open("PUT", "Employee", true);
          	xmlHttpRequest.setRequestHeader("Content-Type",
          			"application/json");
          	xmlHttpRequest.send(JSON.stringify(employee));

}
function deleteEmployee(id){
    var xmlHttpRequest = getXMLHttpRequest();
           	xmlHttpRequest.onreadystatechange = function() {
                     if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
                                 viewAllEmployees();
                                 alert("delete success");
                }
                }
             	xmlHttpRequest.open("DELETE", "Employee/"+id, true);
             	xmlHttpRequest.setRequestHeader("Content-Type","application/json");
             	xmlHttpRequest.send();
}
function getImage(id){
console.log("get image is hit")
var xmlHttpRequest = getXMLHttpRequest();
	xmlHttpRequest.onreadystatechange = function() {
                     if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
                    alert("success");
                    alert(xmlHttpRequest.responseText);
                    $(function () {
                        var imgSrc = "";
                        var $img = $("<img/>");
                        $img.attr("src", "data:image/png;base64," + xmlHttpRequest.responseText);
                        $img.attr({width: "150", height: "100"});


                        $("#img_preview").append($img);
                    });
                }
                }
             	xmlHttpRequest.open("GET", "addPhoto/"+id, true);
             	xmlHttpRequest.send();

}
function addToTable(employee){
           $("#added-articles").append($('<tr/>')
                                .append($('<td/>').html("id: "+employee.id+" name: "+employee.name+" role:  "+employee.role+" address:  "+employee.address))
                                .append('<td><input type="submit" onclick="deleteEmployee('+employee.id+')" value="delete"> </td>')
                                .append('<form action = "modify" method="get"><input type="submit" value="update"/><input type="hidden" name="id" value ="'+employee.id+'"></form>')
                               );
}




