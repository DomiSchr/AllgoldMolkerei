<?php
//Einfache version ohne Framework, berücksichtigt das die meißten Browser kein PUT und DELETE unterstützen


class server
{
   private $db;

   public function __construct()
   {
      $this->db = new mysqli("localhost","root","", "AllgoldMolkerei"); // Funktioniert nicht hier!!

      if (mysqli_connect_errno())
      {
      	die("error while connection to database!:".mysqli_connect_error());
      }

      $this->db->select_db("AllgoldMolkerei");

      if($this->db->errno)
      {
      	die ($this->db->error);
      }
   }

   public function test(){
      
   }

  // Read 

   public function getAllProducts()
   {
      $allProducts = array();
      $stmt = "SELECT * FROM product;";
      $result = $this->db->query($stmt);

        if(empty($result))
        {
           return "your statement: ".$stmt."<br /> received result:".$result;
        }

      while ($row = $result->fetch_assoc()) 
      {
        $allProducts[] = $row;
      }

      return  $allProducts;
   }

   public function missingProducts($station)
   {
        $allStations = array();
        //SQL Query anpassen!! Nur Test!
   	  $stmt = "SELECT * FROM Product WHERE ProduktID = '".$station."';";
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

   // Create

   public function addStation($data)
   {
         //create insert string
         //Passt nicht, nur für Testzwecke!
   	   $stmt = "INSERT INTO produkt ( 
   	   produktID,
   	   name,
         preis,
         menge,
   	   ) VALUES (
   	   ".$data['produktID'].",
   	   '".$data['stationID']."',
         '".$data['menge']."',
         '".$data['produktID']."'
   	   );";

       //commit db request
   	   $result = $this->db->query($stmt);

   	   if($result == 1)
   	   {
   	   	 return "Product succesfully inserted.";
   	   }

   	   return "your statment: ".$stmt."<br /> received result:".$result;
   }


//Update:
//Auslieferung:
   public function updateStation($data)
   {
     // Funktioniert, ReturnScreen passt ist hässlich!!
     $stmt = "UPDATE inventory SET aktMenge = '".$data['menge']."'
                             WHERE stationID = ".$data['stationID']." 
                             AND produktID = '".$data['produktID']."'
     ;";
 
     //commit db request
     $result = $this->db->query($stmt);
   
     if($result == 1)
     {
       return "OK";
     }
 
     return "your statement: ".$stmt."<br /> received result:".$result;
   }


}

?>

