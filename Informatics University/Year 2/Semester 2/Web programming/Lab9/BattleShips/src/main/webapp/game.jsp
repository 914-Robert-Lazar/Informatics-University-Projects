<%--
  Created by IntelliJ IDEA.
  User: Robert
  Date: 5/23/2024
  Time: 2:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ship Game</title>
    <link rel="stylesheet" href="index.css">
    <script src="jquery-3.7.1.min.js"></script>
    <script src="gameHandler.js"></script>
</head>
<body>
    <h1>Ship Game</h1>
    <h2>Welcome ${sessionScope.player_name}</h2>
    <div>
        <!-- Display grid and game status -->
        <div class="parts">
            <div class="own">
                <div id="ownGrid" ></div>
                <% if (session.getAttribute("status") == "ship-placement") { %>
                    <p>Place down your ships</p>
                <% } %>
            </div>
            <div class="opponent">
                <div id="opponentGrid"></div>
                <% if (session.getAttribute("status") == "in-progress") {
                    if (session.getAttribute("turn_player").equals(session.getAttribute("player_id"))) { %>
                    <p>Your turn: sink your opponent's ships!</p>

                <% } else { %>
                    <p>Waiting for opponent to bomb a cell...</p>
                <% }
                }%>
            </div>
        </div>
        <form method="post" action="ShipPlacementServlet" id="shipForm">
            <input type="hidden" id="firstShipPos1" name="firstShipPos1">
            <input type="hidden" id="firstShipPos2" name="firstShipPos2">
            <input type="hidden" id="secondShipPos1" name="secondShipPos1">
            <input type="hidden" id="secondShipPos2" name="secondShipPos2">
            <input type="hidden" name="playerName" value=${sessionScope.player_name}>
            <input type="hidden" name="status" id="status" value=${sessionScope.status}>
            <input type="hidden" id="ownTurn" value=${sessionScope.player_id == sessionScope.turn_player}>
            <input type="hidden" id="gridString" value=${sessionScope.grid}>
            <input type="hidden" id="hitGridString" value=${sessionScope.hit_grid}>

        </form>
        <form method="post" action="BombServlet" id="bombForm">
            <input type="hidden" id="bombedShipPos" name="bombedShipPos">
        </form>
        <form method="post" action="CheckBombedServlet" id="checkBombForm">
            <input type="hidden" id="player_id" name="player_id" value=${sessionScope.player_id}>
            <input type="hidden" id="turn_player" value=${sessionScope.turn_player}>
        </form>
    </div>
    <% if (session.getAttribute("status") == "won") { %>
        <h3>Congratulations, you won!</h3>
    <% } else if (session.getAttribute("status") == "lost") {%>
        <h3>You lost!</h3>
    <% } %>

    <script>
        generateGrid("ownGrid", "ownCell", $("#gridString").val());
        generateGrid("opponentGrid", "opponentCell", $("#hitGridString").val());
    </script>
</body>
</html>
