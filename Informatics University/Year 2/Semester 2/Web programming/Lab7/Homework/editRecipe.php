<?php
    $id = $_POST["id"];
    $author = $_POST["author"];
    $name = $_POST["name"];
    $type = $_POST["type"];
    $description = $_POST["description"];

    $serverName = "localhost";
    $username = "root";
    $password = "";
    $dbName = "food recipes";

    $conn = new mysqli($serverName, $username, $password, $dbName);

    if ($conn->connect_error) {
        die("Connection failed: ".$conn->connect_error);
    }

    $sql = "UPDATE recipe SET Author = ?, Name = ?, Type = ?, Description = ? WHERE RecipeID = ?";

    $stmt = $conn->prepare($sql);
    $stmt->bind_param("ssssi", $author, $name, $type, $description, $id);
    $stmt->execute();

    $stmt->close();
	$conn->close();
?>