<?php
error_reporting(E_WARNING);
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

    // mysql update row with matched pid
    $result = mysql_query("UPDATE user SET password = '".$password."' WHERE email = '".$email."'");

    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;

        // echoing JSON response
        echo json_encode($response);
    } else {
$response["success"] = 0;
        echo json_encode($response);

    }
} else {
    // required field is missing
    $response["success"] = 0;

    // echoing JSON response
    echo json_encode($response);
}
?>
