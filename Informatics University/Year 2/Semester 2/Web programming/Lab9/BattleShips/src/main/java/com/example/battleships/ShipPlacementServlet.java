package com.example.battleships;

import com.example.battleships.asset.DatabaseManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashSet;
import java.util.Objects;


@WebServlet("/ShipPlacementServlet")
public class ShipPlacementServlet extends HttpServlet {

    String createGridWithShips(HashSet<String> positions) {
        StringBuilder gridString = new StringBuilder("{grid: [");
        for (int i = 0; i < 8; ++i) {
            gridString.append("[");
            for (int j = 0; j < 8; ++j) {
                if (positions.contains(i + "-" + j)) {
                    gridString.append("\"S\",");
                }
                else {
                    gridString.append("\"O\",");
                }
            }
            gridString.append("],");
        }
        gridString.append("]}");
        return gridString.toString();
    }

    HashSet<String> getShipPositions(String shipEnd1, String shipEnd2) {
        int end1Row = (int) shipEnd1.charAt(0) - '0';
        int end1Column = (int) shipEnd1.charAt(2) - '0';
        int end2Row = (int) shipEnd2.charAt(0) - '0';
        int end2Column = (int) shipEnd2.charAt(2) - '0';

        HashSet<String> positions = new HashSet<>();

        if (end1Row == end2Row) {
            if (end1Column < end2Column) {
                for (int i = end1Column; i <= end2Column; ++i) {
                    positions.add(end1Row + "-" + i);
                }
            }
            else {
                for (int i = end2Column; i <= end1Column; ++i) {
                    positions.add(end1Row + "-" + i);
                }
            }
        }
        else {
            if (end1Row < end2Row) {
                for (int i = end1Row; i <= end2Row; ++i) {
                    positions.add(i + "-" + end1Column);
                }
            }
            else {
                for (int i = end2Row; i <= end1Row; ++i) {
                    positions.add(i + "-" + end1Column);
                }
            }
        }

        return positions;
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstShipPos1 = request.getParameter("firstShipPos1");
        String firstShipPos2 = request.getParameter("firstShipPos2");
        String secondShipPos1 = request.getParameter("secondShipPos1");
        String secondShipPos2 = request.getParameter("secondShipPos2");

        HttpSession session = request.getSession();
        Integer playerId = (Integer) session.getAttribute("player_id");

        HashSet<String> shipPositions = getShipPositions(firstShipPos1, firstShipPos2);
        HashSet<String> secondShipPositions = getShipPositions(secondShipPos1, secondShipPos2);
        shipPositions.addAll(secondShipPositions);
        JSONObject gridJSON = new JSONObject(createGridWithShips(shipPositions));
        String gridString = gridJSON.toString();

        try {
            DatabaseManager databaseManager = new DatabaseManager();
            Connection conn = databaseManager.conn;

            String sessionId = session.getId();
            String updateGrid = "UPDATE players SET grid = ?, ready = ? WHERE play_session_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(updateGrid);
            pstmt.setString(1, gridString);
            pstmt.setString(2, "1");
            pstmt.setString(3, sessionId);
            pstmt.executeUpdate();

            Statement stmt = conn.createStatement();
            String checkQuery = "SELECT COUNT(*) AS player_count FROM players WHERE ready = 1";
            ResultSet rs = stmt.executeQuery(checkQuery);
            if (rs.next()) {
                if (Objects.equals(rs.getString("player_count"), "2")) {
                    String updateStatus = "UPDATE game_state SET game_status = ? WHERE (player1_id = ? OR player2_id = ?)";
                    pstmt = conn.prepareStatement(updateStatus);
                    pstmt.setString(1, "in-progress");
                    pstmt.setInt(2, playerId);
                    pstmt.setInt(3, playerId);
                    pstmt.executeUpdate();
                }
            }

            session.setAttribute("status", "in-progress");
            session.setAttribute("grid", gridString);

            response.sendRedirect("game.jsp");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
