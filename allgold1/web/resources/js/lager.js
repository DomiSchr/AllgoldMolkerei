function allLager()
{
alert("findStation");
	var url = "lagerREST.php";
	var method = "action=GET";
	url += "?"+method;

	var request = new XMLHttpRequest();
	request.open("GET", url);
	request.onload = function()
	{
		if(request.status == 200)
		{
			var lagerlist = request.responseText;


                //getTable header for data
				var url2 = "includes/lagertable.json";
	            var request2 = new XMLHttpRequest();
	            request2.open("GET", url2);
	            request2.onload = function()
	            {
		           if(request2.status == 200)
		           {
			           var lagertable = request2.responseText;
								 var listexist = document.getElementById("tabelle");
								 if (listexist == null){
			           listStation(lagerlist,lagertable);
							 } else {
								 listexist.remove();
								 listStation(lagerlist,lagertable);
							 }
		            }
	            };
	            request2.send(null);


		}
	};
	request.send(null);
}


function listStation(lagerlist, lagertable)
{alert(lagerlist+lagertable);

	var list = document.getElementById("list");
	var lager = JSON.parse(lagerlist);
	var lagertable = JSON.parse(lagertable);

	var table = document.createElement("table");
	table.setAttribute("class", "table table-dark");
	table.setAttribute("id", "tabelle");

    //table head
    var tablehead = document.createElement("thead");
    var tableRow = document.createElement("tr");


    //wenn die tabllenid nicht angzeigt werden soll, muss h auf 1 gestetzt werden
    var tableattr = 1;
    for(var h = 0; h<tableattr; h++)
    {
    	var json = lagertable[0]; //in this case only one object exitsts
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
    tablehead.appendChild(tableRow); alert("länge des tableheaders"+ tableattr);



    //table body
	var tablebody = document.createElement("tbody");


	for(var j = 0; j < lager.length; j++)
	{
	    var mycurrentRow = document.createElement("tr");

	    for(var i = 0; i<tableattr; i++)
	    {
	    	var json = lagertable[0];
	    	var jsonval = lager[j];
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


function missLager()
{
alert("findStation");
	var url = "lieferantREST.php";
	var method = "action=GET";
	url += "?"+method;

	var request = new XMLHttpRequest();
	request.open("GET", url);
	request.onload = function()
	{
		if(request.status == 200)
		{
			var lagerlist = request.responseText;


                //getTable header for data
				var url2 = "includes/lagertable.json";
	            var request2 = new XMLHttpRequest();
	            request2.open("GET", url2);
	            request2.onload = function()
	            {
		           if(request2.status == 200)
		           {
			           var lagertable = request2.responseText;
								 var listexist = document.getElementById("tabelle");
								 if (listexist == null){
			           listStation(lagerlist,lagertable);
							 } else {
								 listexist.remove();
								 listStation(lagerlist,lagertable);
							 }
		            }
	            };
	            request2.send(null);


		}
	};
	request.send(null);
}
