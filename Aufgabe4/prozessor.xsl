<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">

<html>
  <head>
    <title>
    <!-- Hier evtl. Datenbankname angeben!!-->
    <!-- Hier evtl. iwas AllgoldMolkerei... hinschreiben!-->
        <xsl:value-of select="pma_xml_export/database/@name"/>
    </title>
    
  </head>
  <body>
    <p>
        <h3><b>Allgold Molkerei Produktkatalog</b></h3>
    </p>
    <table border="2">
        <tr>
            <td><b>Produkt ID</b></td>
            <td><b>Bezeichnung</b></td>
            <td><b>Preis</b></td>
            <td><b>Menge</b></td>
        </tr>

        
            <xsl:for-each select="/pma_xml_export/database/table">
            <tr>
                 <td><xsl:value-of select="column[@name='produktID']"/></td>
                <td><xsl:value-of select="column[@name='name']"/></td>
                <td><xsl:value-of select="column[@name='preis']"/></td>
                <td><xsl:value-of select="column[@name='menge']"/></td>
             </tr>

            </xsl:for-each>
        </table>
    <br></br>
    <p><b> Produkte sortiert nach Produktnamen: </b></p>
    <xsl:for-each select="/pma_xml_export/database/table/column[@name='name']">
    <xsl:sort order="ascending"/>
        <xsl:value-of select="../column[@name='name']"/>
        <br></br>
    </xsl:for-each>


      <br></br>
    <p><b> Produkte sortiert nach Höchstpreis: </b></p>
<xsl:for-each select="/pma_xml_export/database/table/column[@name='preis']">
    <xsl:sort order="descending" data-type="number"/>
        <xsl:value-of select="../column[@name='name']"/>: 
        <xsl:value-of select="../column[@name='preis']"/>€
        <br></br>
    </xsl:for-each>

  </body>
</html>
</xsl:template>
</xsl:stylesheet>