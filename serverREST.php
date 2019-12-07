<?php
 
// include necessary classes 
include('server.php');


$station = new server();
$data = array_merge($_GET, $_POST);
$method = $data['action'];
$retlnk = '<br> <a href="index.html"> zur&uuml;ck zur Homeseite </a>';


 // create SQL based on HTTP method
switch ($method) 
{
  case 'GET':

    if(!empty($data['produktID']))
    {
    	$sql = $station->getCoordinates($data['produktID']);
        header('Content-type: application/json; charset=utf-8'); 
        echo json_encode($sql); 
        break;
    }

    if(!empty($data['stationID']))
    {
        $sql = $station->missingProducts($data['stationID']);
        header('Content-type: application/json; charset=utf-8');
        echo json_encode($sql);
        break;
    }

    else
    {
    	$sql = $station->getAllProducts();
        header('Content-type: application/json; charset=utf-8'); 
        echo json_encode($sql);
        break;
    }

    break;

// Einkäufe erfassen:
  case 'POST':
    $sql = $station->addStation($data); 
    echo "Antwort: ".$sql.$retlnk;
    break;

    // Auslieferung: 
  case 'PUT':
    $sql = $station->updateStation($data);
    if($sql == "OK")
    {
    	$send = $station->getAllProducts();
        header('Content-type: application/json; charset=utf-8'); 
        echo json_encode($send);    	
    } 
    else
    {
    	echo $sql;
    }
    break;

  case 'DELETE':
    $sql = $station->removeStation($data['stationID']); 
    echo $sql.$retlnk;
    break;
}



?>

