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
        $stmt = "SELECT * FROM product p, inventory i WHERE
        p.produktID = i.produktID
        AND i.aktMenge < i.minMenge
        AND i.stationID = '".$station."';";

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
      
         //TODO: Vor erfasstem Einkauf muss geprüft werden, ob genug Waren da sind!!

         //Stmt updatet Inventory-Tabelle:
   	   $stmt = "UPDATE inventory SET aktmenge = aktmenge - '".$data['menge']."'
         WHERE stationID = '".$data['stationID']."'
         AND produktID = '".$data['produktID']."'
         ;";

   	   $result = $this->db->query($stmt);

   	   if($result == 1)
   	   {
   	   	
   	   }

         //Stmt updatet Sale-Tabelle
   	   $stmt = "UPDATE sales SET menge = menge + '".$data['menge']."'
         WHERE stationID = '".$data['stationID']."'
         AND produktID = '".$data['produktID']."'
         ;";

   	   $result = $this->db->query($stmt);

   	   if($result == 1)
   	   {
   	   	 return "Sale succesfully inserted.";
   	   }

   	   return "your statment: ".$stmt."<br /> received result:".$result;
   }


//Update:
//Auslieferung:
   public function updateStation($data)
   {
     // Funktioniert, ReturnScreen ist hässlich!!
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

