package com.example.battleships;

import com.example.battleships.asset.DatabaseManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Objects;

@WebServlet("/BombServlet")
public class BombServlet extends HttpServlet {
    boolean shipHit(JSONObject gridJSON, int positionRow, int positionColumn) {
        return Objects.equals(gridJSON.getJSONArray("grid").getJSONArray(positionRow).getString(positionColumn), "S");
    }

    void setGrid(JSONObject gridJSON, int positionRow, int positionColumn, Connection conn, String gridName, int playerId, String value) throws SQLException {
        JSONArray gridArrayJSON = new JSONArray(gridJSON.getJSONArray("grid"));
        JSONArray rowJSON = new JSONArray(gridArrayJSON.getJSONArray(positionRow));
        rowJSON.put(positionColumn, value);
        gridArrayJSON.put(positionRow, rowJSON);
        gridJSON.put("grid", gridArrayJSON);

        String gridString = gridJSON.toString();
        String updateGrid = "";
        if (gridName.equals("grid")) {
            updateGrid = "UPDATE players SET grid = ? WHERE player_id = ?";
        }
        else {
            updateGrid = "UPDATE players SET hitGrid = ? WHERE player_id = ?";
        }
        PreparedStatement pstmt = conn.prepareStatement(updateGrid);
        pstmt.setString(1, gridString);
        pstmt.setInt(2, playerId);
        pstmt.executeUpdate();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer playerId = (Integer) session.getAttribute("player_id");

        if (playerId == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String position = request.getParameter("bombedShipPos");

        try {
            DatabaseManager databaseManager = new DatabaseManager();
            Connection conn = databaseManager.conn;
            String gameQuery = "SELECT * FROM game_state WHERE (player1_id = ? OR player2_id = ?) AND game_status = 'in-progress'";
            PreparedStatement pstmt = conn.prepareStatement(gameQuery);
            pstmt.setInt(1, playerId);
            pstmt.setInt(2, playerId);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Game not found.");
                return;
            }

            int currentTurn = rs.getInt("current_turn");
            int opponentId = (playerId.equals(rs.getInt("player1_id"))) ? rs.getInt("player2_id") : rs.getInt("player1_id");

            if (currentTurn != playerId) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Not your turn.");
                return;
            }

            String checkGrid = "SELECT grid FROM players WHERE player_id = ?";
            String checkHitGrid = "SELECT hitGrid FROM players WHERE player_id = ?";
            pstmt = conn.prepareStatement(checkGrid);
            pstmt.setInt(1, opponentId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                JSONObject gridJSON = new JSONObject(rs.getString("grid"));
                int positionRow = position.charAt(0) - '0';
                int positionColumn = position.charAt(2) - '0';
                pstmt = conn.prepareStatement(checkHitGrid);
                pstmt.setInt(1, playerId);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    JSONObject hitGridJSON = new JSONObject(rs.getString("hitGrid"));
                    if (shipHit(gridJSON, positionRow, positionColumn)) {
                        this.setGrid(gridJSON, positionRow, positionColumn, conn, "grid", opponentId, "H");
                        this.setGrid(hitGridJSON, positionRow, positionColumn, conn, "hitGrid", playerId, "H");

                        int currentScore = (int) session.getAttribute("hit_score") + 1;
                        String updateScore = "UPDATE players SET hitScore = ? WHERE player_id = ?";
                        pstmt = conn.prepareStatement(updateScore);
                        pstmt.setInt(1, currentScore);
                        pstmt.setInt(2, playerId);

                        session.setAttribute("hit_score", currentScore);
                        if (currentScore == 9) {
                            String updateStatus = "UPDATE game_state SET game_status = ? WHERE (player1_id = ? OR player2_id = ?)";
                            pstmt = conn.prepareStatement(updateStatus);
                            pstmt.setString(1, "over");
                            pstmt.setInt(2, playerId);
                            pstmt.setInt(3, playerId);
                            pstmt.executeUpdate();

                            session.setAttribute("status", "won");
                            session.setAttribute("hit_grid", hitGridJSON.toString());
                            response.sendRedirect("game.jsp");
                            return;
                        }
                    }
                    else {
                        this.setGrid(hitGridJSON, positionRow, positionColumn, conn, "hitGrid", playerId, "M");
                    }
                    session.setAttribute("hit_grid", hitGridJSON.toString());
                }
            }

            // Switch turn
            String updateTurn = "UPDATE game_state SET current_turn = ? WHERE (player1_id = ? OR player2_id = ?)";
            pstmt = conn.prepareStatement(updateTurn);
            pstmt.setInt(1, opponentId);
            pstmt.setInt(2, playerId);
            pstmt.setInt(3, playerId);
            pstmt.executeUpdate();

            session.setAttribute("turn_player", opponentId);

            response.sendRedirect("game.jsp");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}