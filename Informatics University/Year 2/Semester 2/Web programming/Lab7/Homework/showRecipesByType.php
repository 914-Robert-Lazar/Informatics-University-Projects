<?php
    $typeToShow = $_GET["type"];
    echo "<p id=prevType>" .$typeToShow . "</p>";

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

    echo "<table border='1'>
        <tr>
            <th>ID</th>
            <th>Author</th>
            <th>Name</th>
            <th>Type</th>
            <th>Description</th>
        </tr>";

    while($stmt->fetch() ) {
        echo "<tr>";
		echo "<td>" . $id . "</td>";
		echo "<td>" . $author . "</td>";
		echo "<td>" . $name . "</td>";
		echo "<td>" . $type . "</td>";
        echo "<td>" . $description . "</td>";
		echo "</tr>";
    }

    echo "</table>";

    $stmt->close();
	$conn->close();
?>