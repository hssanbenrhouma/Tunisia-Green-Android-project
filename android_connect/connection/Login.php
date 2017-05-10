<?php
error_reporting(E_WARNING);
/*
 * Following code will create a new user row
 * All user details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_GET['email']) && isset($_GET['password'])) {

  
    $email = $_GET['email'];
    $password = $_GET['password'];
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql inserting a new row
	
	$resultverif = mysql_query("SELECT * from user WHERE ((email = '".$email."')and(password='".$password."'))");
        $no_of_rows = mysql_num_rows($resultverif);
        if ($no_of_rows > 0) {
           
$result = mysql_query("SELECT type from user WHERE ((email = '".$email."')and(password='".$password."'))");
        // exist
		$ligne = mysql_fetch_array($result);
		
        $response["success"] = 1;
		
        $response["role"] =$ligne["type"] ;

        // echoing JSON response
        echo json_encode($response);
		
        } else {
			 $response["success"] = 0;
        $response["role"] = "Oops! invalid credentials";
		        echo json_encode($response);
				
        }
}
else{
	 $response["success"] = 0;
        $response["role"] = "Oops! error ";
echo json_encode($response);
}
?>
