<?php
mysql_connect("localhost","root","");
mysql_select_db("bd");
$idcit= $_POST['idcitoyen'];
$idresp= $_POST['idresponsable'];
$reg= $_POST['region'];
$descrip= $_POST['description'];
$date= $_POST['date'];
$url= $_POST['url'];
$long= $_POST['long'];
$lat= $_POST['lat'];
echo $lat;
mysql_query("INSERT INTO declaration VALUES(1,'$idcit', '$idresp', '$reg', '$descrip', '$date','$url','$long','$lat')");
mysql_close();
?>