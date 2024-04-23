<?php
    $serverName = "localhost";
    $username = "root";
    $password = "";
    $dbName = "food recipes";

    $conn = new mysqli($serverName, $username, $password, $dbName);

    if ($conn->connect_error) {
        die("Connection failed: ".$conn->connect_error);
    }

    $sql = "SELECT * FROM recipe";

    $stmt = $conn->prepare($sql);
    $stmt->execute();
    $stmt->bind_result($id, $author, $name, $type, $description);

    echo "<table border='1'>
    <thead>
        <tr>
            <th>ID</th>
            <th>Author</th>
            <th>Name</th>
            <th>Type</th>
            <th>Description</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>";

    while($stmt->fetch() ) {
        echo "<tr id='row" . $id . "'>";
		echo "<td>" . $id . "</td>";
		echo "<td>" . $author . "</td>";
		echo "<td>" . $name . "</td>";
		echo "<td>" . $type . "</td>";
        echo "<td>" . $description . "</td>";
        echo "<td>
                <button data-id='row" . $id . "' class='edit'>Edit</button>
                <button data-id='row" . $id . "' class='delete'>Delete</button>
              </td>";
		echo "</tr>";
    }

    echo "<tbody/>
    </table>";

    $stmt->close();
	$conn->close();
?>