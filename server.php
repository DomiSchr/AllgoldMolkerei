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
   
     $stmt = "UPDATE inventory SET aktMenge = '".$data['menge']."'
                             WHERE stationID = ".$data['stationID']." 
                             AND produktID = '".$data['produktID']."'
     ;";
 
     //commit db request
     $result = $this->db->query($stmt);
   
     if($result == 1)
     {
       return "Die Produkte wurden erfolgreich ausgeliefert!";
     }
 
     return "your statement: ".$stmt."<br /> received result:".$result;
   }


   //Umsatz pro Station ausgeben:
   public function umsatzStation($data)
   {
      $ret = array();
      $stmt = "SELECT SUM(s.menge * p.preis) AS umsatz FROM sales s, product p WHERE 
      p.produktID = s.produktID
      AND s.stationID = '".$data."'
      ;";

      $result = $this->db->query($stmt);
      if(!empty($result)){

         while ($row = $result->fetch_assoc()) 
         {
           $ret[] = $row;
         }

         return  $ret[0];



      }

      return "Error";
   }


   //Umsatz pro Produkt:
   //geht noch nicht:
   public function umsatzProdukt($data){
      $ret = array();
      $ausg = "";

      //Umsatz des Produkts:
      $stmt1 = "SELECT SUM(s.menge * p.preis) AS umsatz FROM sales s, product p WHERE 
      p.produktID = s.produktID
      AND p.produktID = '".$data."'
      ;";

      $result = $this->db->query($stmt1);
      if(!empty($result)){

         while ($row = $result->fetch_assoc()) 
         {
           $ret[] = $row;
         }

         $ausg += $ret[0];

      }

          //Gesamtumsatz:
          $stmt1 = "SELECT SUM(s.menge * p.preis) AS umsatz FROM sales s, product p WHERE 
          p.produktID = s.produktID
          AND p.produktID = '".$data."'
          ;";
    
          $result = $this->db->query($stmt1);
          if(!empty($result)){
    
             while ($row = $result->fetch_assoc()) 
             {
               $ret[] = $row;
             }
    
             $ausg += $ret[0];
    
          }

      return $ausg;
   }

}

?>

