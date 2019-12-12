<?php
 
// include necessary classes 
include('server.php');


$server = new server();
$data = array_merge($_GET, $_POST);
$method = $data['action'];
$retlnk = '<br> <a href="index.html"> zur&uuml;ck zur Homeseite </a>';


 // create SQL based on HTTP method
switch ($method) 
{
  case 'GET':

    if(!empty($data['produktID']))
    {
    	$sql = $server->salesProduct($data['produktID']);
        header('Content-type: application/json; charset=utf-8'); 
        echo json_encode($sql); 
        break;
    }

    if(!empty($data['stationID']))
    {
        $sql = $server->missingProducts($data['stationID']);
        header('Content-type: application/json; charset=utf-8');
        echo json_encode($sql);
        break;
    }

    //Methode für Umsatz der Station!
    if(!empty($data['stationID2']))
    {
        $sql = $server->salesStation($data['stationID2']);
        header('Content-type: application/json; charset=utf-8');
        echo json_encode($sql);
        //echo $sql;
        break;
    }

    else
    {
    	$sql = $server->getAllProducts();
        header('Content-type: application/json; charset=utf-8'); 
        echo json_encode($sql);
        break;
    }

    break;

// Einkäufe erfassen:
  case 'POST':
    $sql = $server->insertSale($data); 
    echo "".$sql.$retlnk;
    break;

    // Auslieferung: 
  case 'PUT':
    $sql = $server->supplyProducts($data);
    echo "".$sql.$retlnk;    	
    break;
}

?>

