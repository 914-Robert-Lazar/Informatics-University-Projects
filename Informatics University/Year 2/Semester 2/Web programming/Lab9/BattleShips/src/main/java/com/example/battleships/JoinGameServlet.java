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
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;



@WebServlet("/JoinGameServlet")
public class JoinGameServlet extends HttpServlet {

    String createGridString() {
        StringBuilder gridString = new StringBuilder("{grid: [");
        for (int i = 0; i < 8; ++i) {
            gridString.append("[");
            gridString.append("\"O\",".repeat(8));
            gridString.append("],");
        }
        gridString.append("]}");
        return gridString.toString();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String playerName = request.getParameter("playerName");
        HttpSession session = request.getSession();


        try {
            DatabaseManager databaseManager = new DatabaseManager();
            Connection conn = databaseManager.conn;
            Statement stmt = conn.createStatement();
            String checkQuery = "SELECT COUNT(*) AS player_count FROM players WHERE active = 1";
            ResultSet rs = stmt.executeQuery(checkQuery);
            rs.next();
            int playerCount = rs.getInt("player_count");

            if (playerCount >= 2) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Game already has 2 players.");
                return;
            }

            String sessionId = session.getId();
            JSONObject gridJSON = new JSONObject(this.createGridString());
            String gridString = gridJSON.toString();

            String insertPlayer = "INSERT INTO players (player_name, play_session_id, grid, hitGrid, hitScore) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertPlayer, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, playerName);
            pstmt.setString(2, sessionId);
            pstmt.setString(3, gridString);
            pstmt.setString(4, gridString);
            pstmt.setInt(5, 0);
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            int playerId = -1;
            if (rs.next()) {
                playerId = rs.getInt(1);
                session.setAttribute("player_id", playerId);
            }

            if (playerCount == 1) {
                // Start game
                String getPlayer1 = "SELECT TOP 1 player_id FROM players WHERE active = 1";
                rs = stmt.executeQuery(getPlayer1);
                if (rs.next()) {
                    int player1Id = rs.getInt("player_id");
                    String insertGame = "INSERT INTO game_state (player1_id, player2_id, current_turn, game_status) VALUES (?, ?, ?, 'ship-placement')";
                    pstmt = conn.prepareStatement(insertGame);
                    pstmt.setInt(1, player1Id);
                    pstmt.setInt(2, playerId);
                    pstmt.setInt(3, player1Id);
                    pstmt.executeUpdate();

                    session.setAttribute("turn_player", player1Id);
                }
            }

            session.setAttribute("player_name", playerName);
            session.setAttribute("grid", gridString);
            session.setAttribute("hit_grid", gridString);
            session.setAttribute("status", "ship-placement");
            session.setAttribute("hit_score", 0);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("waiting.jsp");
            requestDispatcher.forward(request, response);
            response.sendRedirect("waiting.jsp");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}