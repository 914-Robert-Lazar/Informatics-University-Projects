package com.example.battleships;

import com.example.battleships.asset.DatabaseManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Objects;

@WebServlet("/CheckBombedServlet")
public class CheckBombedServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer playerId = (Integer) session.getAttribute("player_id");

        if (playerId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not authorized");
            return;
        }

        try {
            DatabaseManager databaseManager = new DatabaseManager();
            Connection conn = databaseManager.conn;
            String gameQuery = "SELECT current_turn, game_status FROM game_state WHERE (player1_id = ? OR player2_id = ?)";
            PreparedStatement pstmt = conn.prepareStatement(gameQuery);
            pstmt.setInt(1, playerId);
            pstmt.setInt(2, playerId);
            ResultSet rs = pstmt.executeQuery();

//            response.setContentType("application/json");
            PrintWriter out = response.getWriter();

            if (rs.next()) {
                String status = rs.getString("game_status");
                if (status.equals("ship-placement")) {
                    out.print("{\"status\": \"waiting\"}");
                }
                else {
                    if (Objects.equals(rs.getString("current_turn"), playerId.toString()) || status.equals("over")) {
                        String checkQuery = "SELECT grid FROM players WHERE player_id = ?";
                        pstmt = conn.prepareStatement(checkQuery);
                        pstmt.setInt(1, playerId);
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                            session.setAttribute("grid", rs.getString("grid"));
                        }
                        if (status.equals("over")) {
                            session.setAttribute("status", "lost");

                            Statement stmt = conn.createStatement();
                            String deletePlayers = "DELETE FROM players";
                            stmt.execute(deletePlayers);
                            String deleteGameState = "DELETE FROM game_state";
                            stmt.execute(deleteGameState);

                            out.print("{\"status\": \"lost\"}");
                        }
                        else {
                            session.setAttribute("turn_player", playerId);
                            out.print("{\"status\": \"switched\"}");
                        }
                    } else {
                        out.print("{\"status\": \"waiting\"}");
                    }

                }
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
