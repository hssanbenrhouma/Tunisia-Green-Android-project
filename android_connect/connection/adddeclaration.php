<?php
error_reporting(E_WARNING);
header('Content-type=application/json; charset=utf-8');
/*
 * Following code will create a new user row
 * All user details are read from HTTP Post Request
 */

// array for JSON response
$response = array();
// check for required fields
if (isset($_GET['email']) && isset($_GET['region']) && isset($_GET['description'])&& isset($_GET['date'])&&isset($_GET['long'])&& isset($_GET['lat'])) 
{

    $email = $_GET['email'];
    $region = $_GET['region'];
    $description = $_GET['description'];
    $date = $_GET['date'];
    $long = $_GET['long'];
    $lat = $_GET['lat'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();
		$resultgetidcit = mysql_query("SELECT id from user where email='".$email."'");
		$idcitoy=mysql_fetch_array($resultgetidcit);
	$resultgetidresp = mysql_query("SELECT id from user where ((region='".$region."')and(type='RESPONSABLE'))");
  $idresp=mysql_fetch_array($resultgetidresp);
  echo $idresp["id"];
	$resultverif = mysql_query("SELECT * from declaration");
	$no_of_rows = mysql_num_rows($resultverif);
	if($no_of_rows>0)
	{
	$resultverif1 = mysql_query("SELECT * from declaration where id=(select max(id) from declaration)");
				$ligne = mysql_fetch_array($resultverif1);
				$idimage=$ligne["id"]+1;	
				
	}
	else
	{
	$idimage=1;
	}
$result = mysql_query("INSERT INTO declaration VALUES ('".$idimage."', '".$idcitoy["id"]."', '".$idresp["id"]."', '".$region."', '".$description."','".$date."','".$sidimage."','".$long."','".$lat."')");

    // check if row inserted or not
    if ($result) {
        $response["success"] = $idimage;

        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;

        // echoing JSON response
        echo json_encode($response);
    }
} 
	else {
    // required field is missing
    $response["success"] = 0;
    // echoing JSON response
  echo  json_encode($response);
}
?>
			