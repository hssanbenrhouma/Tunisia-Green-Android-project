<?php
error_reporting(E_WARNING);
// array for JSON response
$response = array();

// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();


    $email = $_GET['email'];

   
    $result = mysql_query("SELECT * FROM user WHERE email = '".$email."'");
	 if (mysql_num_rows($result) > 0) {

            $resu = mysql_fetch_array($result);
			$resultDec = mysql_query("SELECT * FROM declaration WHERE region = '".$resu["region"]."'")or die(mysql_error());
if(mysql_num_rows($resultDec) > 0)
{
	    $response["declarations"] = array();

            while ($row = mysql_fetch_array($resultDec)) {
        $dec = array();
		$r=mysql_query("SELECT * FROM user WHERE id = '".$row["idcitoyen"]."'");
		            $res = mysql_fetch_array($r);

        $dec["id"] = $row["id"];
        $dec["emailcit"] = $res["email"];
        $dec["description"] = $row["description"];
        $dec["region"] = $row["region"];
        $dec["date"] = $row["date"];
		$dec["urlimage"] = $row["urlimage"];
		$dec["long"] = $row["longitude"];
        $dec["lat"] = $row["latitude"];

        // push single product into final response array
        array_push($response["declarations"], $dec);
    }
            // success
            $response["success"] = 1;

            // echoing JSON response
            echo json_encode($response);
        } else {
            $response["success"] = 0;
            $response["message"] = "No declaraion found";

            // echo no users JSON
            echo json_encode($response);
        }
}
?>
