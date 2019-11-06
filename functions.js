function getProducts()
{
alert("getProducts");
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
				var url2 = "includes/stationtable.json";
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