<?php
    $id = $_POST["id"];

    $serverName = "localhost";
    $username = "root";
    $password = "";
    $dbName = "food recipes";

    $conn = new mysqli($serverName, $username, $password, $dbName);

    if ($conn->connect_error) {
        die("Connection failed: ".$conn->connect_error);
    }

    $sql = "DELETE FROM recipe WHERE RecipeID = ?";

    $stmt = $conn->prepare($sql);
    $stmt->bind_param("i", $id);
    $stmt->execute();

    $stmt->close();
	$conn->close();
?>