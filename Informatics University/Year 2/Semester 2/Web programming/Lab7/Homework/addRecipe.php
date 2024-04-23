<?php
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

    $sql = "INSERT INTO recipe(Author, Name, Type, Description) VALUES (?, ?, ?, ?)";

    $stmt = $conn->prepare($sql);
    $stmt->bind_param("ssss", $author, $name, $type, $description);
    $stmt->execute();

    $stmt->close();
	$conn->close();
?>