<?php
//Einfache version ohne Framework, berücksichtigt das die meißten Browser kein PUT und DELETE unterstützen


class server
{
   private $db;

   public function __construct()
   {
      $this->db = new mysqli("localhost","root","", "allgold");

      if (mysqli_connect_errno())
      {
      	die("error while connection to database!:".mysqli_connect_error());
      }

      $this->db->select_db("allgold");

      if($this->db->errno){
      	die ($this->db->error);
      }
   }

  // Produktkatalog anzeigen:
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


   //Fehlende Produkte anzeigen:
   public function missingProducts($station)
   {
        $allStations = array();

      //Gibt u.A. Menge zurück, die zur Erreichung der Sollmenge fehlt!
        $stmt = "SELECT p.produktID, p.name, p.preis, (i.sollMenge - i.aktMenge) AS menge
         FROM product p, inventory i WHERE
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


   //Verkauf erfassen:
   public function insertSale($data)
   {
         //Prüfen, ob genug Waren in Verkaufsstelle sind:
         $ret = array();
         $stmt = "SELECT  aktmenge FROM inventory
         WHERE stationID = '".$data['stationID']."'
         AND produktID = '".$data['produktID']."'
         ;";

         $result = $this->db->query($stmt);

         while ($row = $result->fetch_assoc())
         {
           $ret[] = $row;
         }

         if(implode($ret[0]) < $data['menge']){
            return "Zu wenig Ware in Verkaufsstelle!";
         }


         //Stmt updatet Inventory-Tabelle:
   	   $stmt = "UPDATE inventory SET aktmenge = aktmenge - '".$data['menge']."'
         WHERE stationID = '".$data['stationID']."'
         AND produktID = '".$data['produktID']."'
         ;";

   	   $result = $this->db->query($stmt);

         //Stmt updatet Sale-Tabelle
   	   $stmt = "UPDATE sales SET menge = menge + '".$data['menge']."'
         WHERE stationID = '".$data['stationID']."'
         AND produktID = '".$data['produktID']."'
         ;";

   	   $result = $this->db->query($stmt);

   	   if($result == 1)
   	   {
   	   	 return "Einkauf wurde erfolgreich erfasst.";
   	   }

   	   return "your statment: ".$stmt."<br /> received result:".$result;
   }


//Auslieferung:
   public function supplyProducts($data)
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
   public function salesStation($data)
   {
      $ret = array();
      $stmt = "SELECT SUM(s.menge * p.preis) AS umsatz FROM sales s, product p WHERE
      p.produktID = s.produktID
      AND s.stationID = '".$data."'
      ;";
      $ausg = "";

      $result = $this->db->query($stmt);
      if(!empty($result)){

         while ($row = $result->fetch_assoc())
         {
           $ret[] = $row;
         }

        $ausg .= implode($ret[0]);
      }


      //Gesamtumsatz:
      $stmt2 = "SELECT SUM(s.menge * p.preis) AS umsatz FROM sales s, product p WHERE
      p.produktID = s.produktID
      ;";

      $result2 = $this->db->query($stmt2);
      if(!empty($result2)){

         while ($row2 = $result2->fetch_assoc()) {
            $ret2[] = $row2;
         }

         $ausg .= ", ".implode($ret2[0]);

      }

      return $ausg;
   }


   //Umsatz pro Produkt:
   public function salesProduct($data){
      $ret = array();
      $ausg = "";

      //Umsatz des Produkts:
      $stmt1 = "SELECT SUM(s.menge * p.preis) AS umsatz FROM sales s, product p WHERE
      p.produktID = s.produktID
      AND p.produktID = '".$data."'
      ;";

      $result1 = $this->db->query($stmt1);
      if(!empty($result1)){

         while ($row1 = $result1->fetch_assoc())
         {
           $ret1[] = $row1;
         }


         $ausg .= implode($ret1[0]);

      }

          //Gesamtumsatz:
          $stmt2 = "SELECT SUM(s.menge * p.preis) AS umsatz FROM sales s, product p WHERE
          p.produktID = s.produktID
          ;";

          $result2 = $this->db->query($stmt2);
          if(!empty($result2)){

             while ($row2 = $result2->fetch_assoc())
             {
               $ret2[] = $row2;
             }

             $ausg .= ", ".implode($ret2[0]);

          }

      return $ausg;
   }
}

?>
