<%--
  Created by IntelliJ IDEA.
  User: Robert
  Date: 5/23/2024
  Time: 3:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Waiting page</title>
    <link rel="stylesheet" href="index.css">
    <script>
        function checkGameStatus() {
            fetch('CheckGameStatusServlet', {
                contentType: "text"
            })
                .then(response => response.json())
                .then(data => {
                    console.log(data.status);
                    if (data.status === 'ship-placement') {

                        window.location.href = 'game.jsp?player_name=${sessionScope.player_name}';
                    }
                })
                .catch(error => console.error('Error:', error));
        }

        setInterval(checkGameStatus, 1000);
    </script>
</head>
<body>
    <h1>Ship Game</h1>
    <h2>Welcome ${requestScope.player_name}</h2>
    <h3>Waiting for other player to join...</h3>
</body>
</html>
