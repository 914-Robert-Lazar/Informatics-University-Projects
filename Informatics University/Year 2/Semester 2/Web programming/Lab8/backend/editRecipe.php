<?php
    header("Access-Control-Allow-Origin: *");

    $id = $_GET["id"];
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

    $sql = "UPDATE recipe SET Author = ?, Name = ?, Type = ?, Description = ? WHERE RecipeID = ?";

    $stmt = $conn->prepare($sql);
    $stmt->bind_param("ssssi", $author, $name, $type, $description, $id);
    $stmt->execute();

    $data = new stdClass();
    $data->id = $id;
    $data->author = $author;
    $data->name = $name;
    $data->type = $type;
    $data->description = $description;

    echo json_encode($data);

    $stmt->close();
	$conn->close();
?>