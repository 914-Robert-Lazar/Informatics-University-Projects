<?php
    $id = $_GET["id"];

    $serverName = "localhost";
    $username = "root";
    $password = "";
    $dbName = "food recipes";

    $conn = new mysqli($serverName, $username, $password, $dbName);

    if ($conn->connect_error) {
        die("Connection failed: ".$conn->connect_error);
    }

    $sql = "SELECT Author, Name, Type, Description FROM recipe WHERE RecipeID = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("s", $id);
    $stmt->execute();
    $stmt->bind_result($author, $name, $type, $description);

    $stmt->fetch();

    $recipe = new stdClass();
    $recipe->author = $author;
    $recipe->name = $name;
    $recipe->type = $type;
    $recipe->description = $description;
    
    echo json_encode($recipe);

    $stmt->close();
	$conn->close();
?>