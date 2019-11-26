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

  // R ead 

   public function getAllProducts()
   {
      $allProducts = array();
      $stmt = "SELECT * FROM produkt ;";
      $result = $this->db->query($stmt);

        if(empty($result))
        {
           return "your statement: ".$stmt."<br /> received result:".$result;
        }

      while ($row = $result->fetch_assoc()) 
      {
        $allStations[] = $row;
      }

      return  $allProducts;
   }
}

?>

