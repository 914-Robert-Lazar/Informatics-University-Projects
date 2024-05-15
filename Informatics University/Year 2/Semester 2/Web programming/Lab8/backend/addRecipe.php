<?php
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Allow-Headers: *");

    $author = $_GET["author"];
    $name = $_GET["name"];
    $type = $_GET["type"];
    $description = $_GET["description"];

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

    $data = new stdClass();
    $data->id = -1;
    $data->author = $author;
    $data->name = $name;
    $data->type = $type;
    $data->description = $description;

    echo json_encode($data);

    $stmt->close();
	$conn->close();
?>