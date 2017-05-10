<?php
error_reporting(E_WARNING);
/*
 * Following code will create a new user row
 * All user details are read from HTTP Post Request
 */

// array for JSON response
$response = array();
// check for required fields
if (isset($_GET['username']) && isset($_GET['email']) && isset($_GET['region']) && isset($_GET['password'])) {

    $username = $_GET['username'];
    $email = $_GET['email'];
    $region = $_GET['region'];
    $password = $_GET['password'];
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql inserting a new row
	
	$resultverif = mysql_query("SELECT email from user WHERE email = '".$email."'");
        $no_of_rows = mysql_num_rows($resultverif);
        if ($no_of_rows > 0) {
            $response["success"] = 0;
        $response["message"] = "Oops! invalid credentials";
		        echo json_encode($response);

        } else {
            // user not existed
			
			$result = mysql_query("INSERT INTO user (id, username, email, type, region, password) VALUES (NULL, '".$username."', '".$email."', 'CITOYEN', '".$region."', '".$password."')");

    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "User successfully created.";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";

        // echoing JSON response
        echo json_encode($response);
    }
} 
			
        }
	else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
  echo  json_encode($response);
}
	
	
?>
