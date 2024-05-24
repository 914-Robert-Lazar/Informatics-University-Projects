<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ship Game</title>
    <link rel="stylesheet" href="index.css">
</head>
<body>
<h1>Enter your name to join the game</h1>
<form action="JoinGameServlet" method="post">
    Name: <input type="text" name="playerName" required>
    <input type="submit" value="Join Game">
</form>
</body>
</html>