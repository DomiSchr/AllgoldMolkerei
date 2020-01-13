<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">

<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">

    <fo:layout-master-set>
        <fo:simple-page-master master-name="Katalog" page-height="297mm" page-width="21cm" margin-left = "2.5cm" margin-right="20mm" margin-top="20mm" margin-bottom="20mm">


        <fo:region-body margin-top="2cm" margin-bottom="2cm" />
        <fo:region-before extent ="2cm"/>
        <fo:region-after extent="2cm"/>

        </fo:simple-page-master>
  
        <fo:simple-page-master master-name="template" page-height="21cm" page-width="297mm" margin-left = "2.5cm" margin-right="20mm" margin-top="20mm" margin-bottom="20mm">


        <fo:region-body margin-top="2cm" margin-bottom="2cm" />
        <fo:region-before extent ="2cm"/>
        <fo:region-after extent="2cm"/>

        </fo:simple-page-master>
    </fo:layout-master-set>

      <fo:page-sequence master-reference="Katalog">
        <fo:flow flow-name="xsl-region-body">
          <fo:block background-image="background.jpg" background-repeat="no-repeat" content-width="160mm" content-height="60mm">
            <fo:block font-size="36px" space-before="4cm" space-after="4cm" text-align="center" margin-top="4cm" color="white" font-family="verdana">
              Allgold Datenbank Katalog
            </fo:block>
          </fo:block>
          <fo:block id="inhalt" font-size="16px" space-before="1.5cm" space-after="0.5cm" text-align="center" text-transform="uppercase">
            <fo:inline text-decoration="underline" font-weight="bold">
              Inhaltsverzeichnis
              <fo:footnote>
                <fo:inline font-size="8pt" baseline-shift ="super">
                  1
                </fo:inline>
              
              <fo:footnote-body>
                <fo:block text-align-last="justify">
                  <fo:leader leader-length="100%" rule-thickness="0.5pt" leader-pattern="rule"/>
                </fo:block>
                <fo:block font-size="10pt" text-transform="none">
                  <fo:inline font-size="8pt" baseline-shift="super">1
                  </fo:inline>
                  Verfügbare Tabellen der Allgold Datenbank, für Details, bitte auf Namen klicken.
                </fo:block>
              </fo:footnote-body>
            </fo:footnote>  
            </fo:inline>
            
            </fo:block>
              <xsl:for-each select="/mysqldump/database/table_data">
              <xsl:variable name="currLink" select="@name" />
                <fo:block space-before="0.5cm" font-size="14px" text-align-last="justify">
                  <fo:basic-link internal-destination="{$currLink}" show-destination="replace">
                    <xsl:number/>
                    <xsl:value-of select="@name" />
                      <fo:leader leader-pattern="space"/> Seite
                        <fo:page-number-citation ref-id="{$currLink}"/>
                    </fo:basic-link>
                  </fo:block>
              </xsl:for-each>
          </fo:flow>
        </fo:page-sequence>



<!-- _______________________________________________________________________________ -->

  <xsl:for-each select="/mysqldump/database/table_data">
    <fo:page-sequence master-reference="template">

    <fo:static-content flow-name="xsl-region-after">
      <fo:block text-align="center"> &#2014; Seite <fo:page-number/> &#2014; </fo:block>
    </fo:static-content>

        <fo:flow flow-name="xsl-region-body">
          <xsl:variable name="currLink" select="@name"/>

            <fo:block font-size="18px" text-align="center" space-before="0.5cm" space-after="0.5cm">
              <fo:inline text-decoration="underline">
                <fo:block id="{$currLink}"> <xsl:value-of select="@name"/> </fo:block>
                </fo:inline>
            </fo:block>
          <xsl:choose>
            <xsl:when test="count(descendant::row)=0">
              <fo:block> Keine Datenbankeinträge aus der Tabelle verfügbar <xsl:value-of select="../@name"/>
              </fo:block>
            </xsl:when>
          <xsl:otherwise>


            <fo:block>
              <fo:table background-color="#B8DB7D" border-style="solid" border-width="1px" border-color="green">
                <fo:table-header color="green" border-style="dotted" border-width="1px" border-color="green">
                  <fo:table-row>


                    <xsl:variable name="countrow" select="count(descendant::row)"/>
                    <xsl:variable name="countfield" select="count(descendant::field)"/>
                    <xsl:variable name="startpos" select="count(preceding::field)"/>
                    <xsl:variable name="endpos" select="$startpos + $countfield div $countrow"/>

                    <xsl:for-each select="/mysqldump/database/table_data/row/field">

                      <xsl:if test="../../@name= $currLink">
                        <xsl:variable name="curpos" select="count(preceding::field)"/>
                        <xsl:if test="$curpos &lt; $endpos">


                  <fo:table-cell>
                      <fo:block> 
                        <xsl:value-of select="@name"/>
                      </fo:block>
                    </fo:table-cell>
                    </xsl:if>
                    </xsl:if>
                    </xsl:for-each>

                </fo:table-row>
             </fo:table-header>


             <fo:table-footer font-size = "8px" color="green" border-style="dotted" border-width="1px" border-color="green">
               <fo:table-row>
                    
                    <xsl:variable name="countrow" select="count(descendant::row)"/>
                    <xsl:variable name="countfield" select="count(descendant::field)"/>
                    <xsl:variable name="numbcol" select="$countfield div $countrow"/>

                <fo:table-cell number-columns-spanned="{$numbcol}">
                 <fo:block text-align="center"> © Allgold GmbH </fo:block>
                 </fo:table-cell>
                </fo:table-row>
               </fo:table-footer>

               <fo:table-body>
                <xsl:for-each select="/mysqldump/database/table_data/row">
                <xsl:if test="../@name=$currLink">

                    <xsl:variable name="countfield" select="count(descendant::field)"/>
                    <xsl:variable name="startpos" select="count(preceding::field)"/>
                    <xsl:variable name="endpos" select="$startpos + $countfield"/>


               <fo:table-row>
                <xsl:for-each select="//field">
                  <xsl:variable name="currfield" select="@name"/>
                  <xsl:if test="../../@name = $currLink">
                    <xsl:variable name="curpos" select="count(preceding::field)"/>
                    <xsl:if test="$startpos &lt;=$curpos and $curpos &lt; $endpos">

                  <fo:table-cell>
                     <fo:block><xsl:value-of select="../field[@name=$currfield]"/></fo:block>
                    </fo:table-cell>
                    </xsl:if>
                    </xsl:if>
                    </xsl:for-each>

                </fo:table-row>
                </xsl:if>
                </xsl:for-each>
             </fo:table-body>



        </fo:table>
    </fo:block>
  </xsl:otherwise>
  </xsl:choose>
    <fo:block font-size="18px" space-before="0.5cm" space-after="0.5cm" text-align="center">
      <fo:inline text-decoration="underline">
        <fo:basic-link internal-destination="inhalt" show-destination="replace">
          Zurück zum Inhaltsverzeichnis
        </fo:basic-link>
      </fo:inline>
    </fo:block>
        </fo:flow>
    </fo:page-sequence>
    </xsl:for-each>
</fo:root>
</xsl:template>
</xsl:stylesheet>