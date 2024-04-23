<?php
    $serverName = "localhost";
    $username = "root";
    $password = "";
    $dbName = "food recipes";

    $conn = new mysqli($serverName, $username, $password, $dbName);

    if ($conn->connect_error) {
        die("Connection failed: ".$conn->connect_error);
    }

    $sql = "SELECT DISTINCT Type FROM recipe";

    $stmt = $conn->prepare($sql);
    $stmt->execute();
    $stmt->bind_result($type);

    $types = array();

    while($stmt->fetch() ) {
        array_push($types, $type);
    }

    echo json_encode($types);

    $stmt->close();
	$conn->close();
?>