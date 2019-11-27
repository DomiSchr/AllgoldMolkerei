function test(){
	var request = new XMLHttpRequest();

	request.onreadystatechange = function(){
		if(request.readyState == 4){
			if(request.status == 200){
				document.getElementById("insert").textContent = request.responseText;
			}

			if(request.status == 404){
				//Keine Ressource!
			}
		}
	};

	request.open("GET", "test.txt", true);
	request.send(); 
}


function getAllProducts()
{
	
	var url = "serverREST.php";
	
	var method = "action=GET";
	url += "?"+method;

	var request = new XMLHttpRequest();
	request.open("GET", url);

	request.onload = function()
	{
		if(request.status == 200)
		{
			var stationlist = request.responseText;


                //getTable header for data
				var url2 = "products.json";
	            var request2 = new XMLHttpRequest();
	            request2.open("GET", url2);
	            request2.onload = function()
	            {
		           if(request2.status == 200)
		           {
					   var stationtable = request2.responseText;
					   //Noch gelassen für Ausgabe!
			           listStation(stationlist,stationtable);
		            }
	            };
	            request2.send(null);

			
		}
	};
	request.send(null);
}

//braucht evt. gar kein JavaScript!
function einkaufErfassen(){
	var station = document.getElementById("stationID").value;
	var produkt = document.getElementById("produktID").value;
	var menge = document.getElementById("menge").value;


	alert(station +  produkt + menge);

}


//einfach nur kopiert, passt noch nicht!
function fehlProdukte(){
	var station = document.getElementById("stationID").value;

	var url = "serverREST.php";
	
	var method = "action=GET";
	url += "?"+method;

	var request = new XMLHttpRequest();
	request.open("GET", url);

	request.onload = function()
	{
		if(request.status == 200)
		{
			var stationlist = request.responseText;


                //getTable header for data
				var url2 = "products.json";
	            var request2 = new XMLHttpRequest();
	            request2.open("GET", url2);
	            request2.onload = function()
	            {
		           if(request2.status == 200)
		           {
					   var stationtable = request2.responseText;
					   //Noch gelassen für Ausgabe!
			           listStation(stationlist,stationtable);
		            }
	            };
	            request2.send(null);

			
		}
	};
	request.send(null);
}

function listStation(stationlist, getstationtable)
{

	var list = document.getElementById("list");
	var stations = JSON.parse(stationlist);
	var stationtable = JSON.parse(getstationtable);


	var table = document.createElement("table");
	table.setAttribute("class", "test");

    //table head
    var tablehead = document.createElement("thead");
    var tableRow = document.createElement("tr");

    //wenn die tabllenid nicht angzeigt werden soll, muss h auf 1 gestetzt werden
    var tableattr = 1;
    for(var h = 0; h<tableattr; h++)
    {
    	var json = stationtable[0]; //in this case only one object exitsts
    	var key = "td"+h;
    	var tableval = json[key]; 
    	if(tableval != undefined)
    	{
    		var tableCell = document.createElement("td");
    	    var cellContent = document.createTextNode(tableval);
    	    tableCell.appendChild(cellContent);
    	    tableRow.appendChild(tableCell);

    	    tableattr++; 
    	}
    }
    tablehead.appendChild(tableRow);



    //table body
	var tablebody = document.createElement("tbody");


	for(var j = 0; j < stations.length; j++)
	{
	    var mycurrentRow = document.createElement("tr");

	    for(var i = 0; i<tableattr; i++)
	    {
	    	var json = stationtable[0];
	    	var jsonval = stations[j];
	    	var key = "td" + i;
	    	var value = json[key];
	        var tableval = jsonval[value];
	        if(tableval != undefined)
	        {
	            var mycurrentCell = document.createElement("td");
		        var mycurrentText = document.createTextNode(tableval);
		        mycurrentCell.appendChild(mycurrentText);
		        mycurrentRow.appendChild(mycurrentCell);
	        }
	    }
        tablebody.appendChild(mycurrentRow);
    }
	


	table.appendChild(tablehead);
	table.appendChild(tablebody);
	list.appendChild(table);
}