function allVerkaeufe()
{
alert("findStation");
	var url = "verkaufREST.php";
	var method = "action=GET";
	url += "?"+method;

	var request = new XMLHttpRequest();
	request.open("GET", url);
	request.onload = function()
	{
		if(request.status == 200)
		{
			var verkauflist = request.responseText;


                //getTable header for data
				var url2 = "includes/verkauftable.json";
	            var request2 = new XMLHttpRequest();
	            request2.open("GET", url2);
	            request2.onload = function()
	            {
		           if(request2.status == 200)
		           {
			           var verkauftable = request2.responseText;
								 var listexist = document.getElementById("tabelle");
								 if (listexist == null){
			           listStation(verkauflist,verkauftable);
							 } else {
								 listexist.remove();
								 listStation(verkauflist,verkauftable);
							 }
		            }
	            };
	            request2.send(null);


		}
	};
	request.send(null);
}


function listStation(verkauflist, verkauftable)
{alert(verkauflist+verkauftable);

	var list = document.getElementById("list");
	var verkaeufe = JSON.parse(verkauflist);
	var verkaeufetable = JSON.parse(verkauftable);

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
    	var json = verkaeufetable[0]; //in this case only one object exitsts
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


	for(var j = 0; j < verkaeufe.length; j++)
	{
	    var mycurrentRow = document.createElement("tr");

	    for(var i = 0; i<tableattr; i++)
	    {
	    	var json = verkaeufetable[0];
	    	var jsonval = verkaeufe[j];
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

function getStationTable()
{

	var url = "includes/verkauftable.json";
	var request = new XMLHttpRequest();
	alert(url);
	request.open("GET", url);
	request.onload = function()
	{
		if(request.status == 200)
		{
			var erg = request.responseText;
			return erg;
		}
	};
	request.send(null);
}

var _selectProdukt = 1;
var _selectAnzahl = 1;

function erweitern()
{
var einfuegeelement = document.getElementById("erweiterung");

var form = document.createElement("div");
form.setAttribute("class", "form-inline justify-content-md-end");
form.setAttribute("id", "x1");
var row = document.createElement("div");
row.setAttribute("class", "form-group mx-sm-3 mb-2");
var input = document.createElement("input");
input.setAttribute("class", "form-control");
input.setAttribute("type", "text");
input.setAttribute("name", "productID"+_selectProdukt++);
input.setAttribute("placeholder", "ProduktID");


einfuegeelement.appendChild(form);
form.appendChild(row);
row.appendChild(input);


var row2 = document.createElement("div");
row2.setAttribute("class", "form-group mx-sm-3 mb-2");
var input2 = document.createElement("input");
input2.setAttribute("class", "form-control");
input2.setAttribute("type", "text");
input2.setAttribute("name", "anzahl"+_selectAnzahl++);
input2.setAttribute("placeholder", "Anzahl");

form.appendChild(row2);
row2.appendChild(input2);


};
