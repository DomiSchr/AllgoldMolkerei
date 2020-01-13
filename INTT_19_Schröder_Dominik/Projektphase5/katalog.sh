# Daten aus Datenbank holen
mysqldump --xml -u grp101 -pITTgrp101 -h 192.168.101.221 grp101_IKS > dbkatalog.xml

# XML-Transformation
java -cp ./saxon9he.jar net.sf.saxon.Transform -o:katalog.fo dbkatalog.xml katalog.xsl

# FO-Transformation
fop katalog.fo katalog.pdf

# PDF anzeigen
xdg-open katalog.pdf