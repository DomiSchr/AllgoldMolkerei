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

function fehlProdukte(){

	var url = "serverREST.php";
	var method = "action=GET";

	var stationID = document.getElementById("stationID").value;
	
	url += "?"+method+"&"+"stationID"+"="+stationID;

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
			           listStation(stationlist,stationtable);
		            }
	            };
	            request2.send(null);

			
		}
	};
	request.send(null);
}

function umsatzStation(){

	var url = "serverREST.php";
	var method = "action=GET";

	var stationID2 = document.getElementById("stationID").value;
	
	url += "?"+method+"&"+"stationID2"+"="+stationID2;

	var request = new XMLHttpRequest();
	request.open("GET", url);
	request.onload = function()
	{
		if(request.status == 200)
		{
			//Prüfen, ob ungültige Verkauffstelle eingegeben:
			if(request.responseText.indexOf(",") == 2){

				document.getElementById("insert1").textContent = "ungültige Verkaufsstelle eingegeben!";

			} else {
				var stat = request.responseText.substring(2, request.responseText.indexOf(","));
				var ges = request.responseText.substring(request.responseText.indexOf(",") + 1, request.responseText.length - 2);
				document.getElementById("insert1").textContent = "Umsatz Verkaufsstelle " + stationID2 + " : " + stat + "€ entspricht: " + (Math.round((stat/ges)*10000))/100 + "%";
				document.getElementById("insert2").textContent = "Gesamtumsatz: " + ges + "€";

			}
		}
	};
	request.send(null);
}


function umsatzProdukt(){

	var url = "serverREST.php";
	var method = "action=GET";

	var produktID = document.getElementById("produktID").value;
	
	url += "?"+method+"&"+"produktID"+"="+produktID;

	var request = new XMLHttpRequest();
	request.open("GET", url);
	request.onload = function()
	{
		if(request.status == 200)
		{
			if(request.responseText.indexOf(",") == 2){
				document.getElementById("insert3").textContent = "Ungültiges Produkt eingegeben!";

			} else {
				var prod = request.responseText.substring(2, request.responseText.indexOf(","));
				var ges = request.responseText.substring(request.responseText.indexOf(",") + 1, request.responseText.length - 2);
				document.getElementById("insert3").textContent = "Umsatz Produkt " + produktID + " : " + prod + "€ entspricht: " + (Math.round((prod/ges)*10000))/100 + "%";
				document.getElementById("insert4").textContent = "Gesamtumsatz: " + ges + "€";
			}
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