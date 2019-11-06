<?php
//Einfache version ohne Framework, berücksichtigt das die meißten Browser kein PUT und DELETE unterstützen


class server
{
   private $db;

   public function __construct()
   {
      $this->db = new mysqli("192.168.101.221","grp101","ITTgrp101");

      if (mysqli_connect_errno())
      {
      	die("error while connection to database!:".mysqli_connect_error());
      }

      $this->db->select_db("grp115_IKS");

      if($this->db->errno)
      {
      	die ($this->db->error);
      }
   }


  // R ead 

   public function getAllStations()
   {
      $allStations = array();
      $stmt = "SELECT * FROM station;";
      $result = $this->db->query($stmt);

        if(empty($result))
        {
           return "your statement: ".$stmt."<br /> received result:".$result;
        }

      while ($row = $result->fetch_assoc()) 
      {
        $allStations[] = $row;
      }

      return  $allStations;
   }

   public function getCoordinates($stationID)
   {
   	  $allStations = array();
   	  $stmt = "SELECT * FROM station WHERE stationID = ".$stationID.";";
   	  $result = $this->db->query($stmt);

        if(empty($result))
        {
           return "your statement: ".$stmt."<br /> received result:".$result;
        }

      while ($row = $result->fetch_assoc()) 
      {
        $allStations[] = $row;
      }

      return $allStations;
   	  //return $row = $result->fetch_assoc(); 
   }


   public function findByLocation($location)
   {
   	  $allStations = array();
   	  $stmt = "SELECT * FROM station WHERE location = '".$location."';";
      $result = $this->db->query($stmt);

        if(empty($result))
        {
           return "your statement: ".$stmt."<br /> received result:".$result;
        }

      while ($row = $result->fetch_assoc()) 
      {
        $allStations[] = $row;
      }

      return $allStations;
   }



// U pdate
    
  public function updateStation($data)
  {
    //create insert string
    $stmt = "UPDATE station SET coordsA = '".$data['coordsA']."',
                                coordsL = '".$data['coordsL']."',
                               location = '".$data['location']."',
                                   type = '".$data['type']."',
                            description =  '".$data['description']."'
                            WHERE stationID = ".$data['stationID']." ;";

    //commit db request
    $result = $this->db->query($stmt);

    if($result == 1)
    {
      return "OK";
    }

    return "your statement: ".$stmt."<br /> received result:".$result;
  }


// D elete

   public function removeStation($stationID)
   {
      $allSupplies = array();
      $stmt = "DELETE FROM station WHERE stationID = ".$stationID.";";
      $result = $this->db->query($stmt);

       if($result == 1)
       {
         return "station succesfully deleted.";
       }

       return "your statment: ".$stmt."<br /> received result:".$result;
   }
}

?>

