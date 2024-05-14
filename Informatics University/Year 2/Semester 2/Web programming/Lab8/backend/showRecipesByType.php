<?php
    header("Access-Control-Allow-Origin: *");
    
    $typeToShow = $_GET["type"];

    $serverName = "localhost";
    $username = "root";
    $password = "";
    $dbName = "food recipes";

    $conn = new mysqli($serverName, $username, $password, $dbName);

    if ($conn->connect_error) {
        die("Connection failed: ".$conn->connect_error);
    }

    $sql = "SELECT * FROM recipe WHERE type = ?";

    $stmt = $conn->prepare($sql);
    $stmt->bind_param("s", $typeToShow);
    $stmt->execute();
    $stmt->bind_result($id, $author, $name, $type, $description);

    $recipes = array();
    
    while($stmt->fetch() ) {
        $recipe = new stdClass();
        $recipe->id = $id;
        $recipe->author = $author;
        $recipe->name = $name;
        $recipe->type = $type;
        $recipe->description = $description;

        array_push($recipes, $recipe);
    }

    echo json_encode($recipes);

    $stmt->close();
	$conn->close();
?>