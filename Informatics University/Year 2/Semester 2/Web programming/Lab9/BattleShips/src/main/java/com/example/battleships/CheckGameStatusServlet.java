package com.example.battleships;
import com.example.battleships.asset.DatabaseManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

@WebServlet("/CheckGameStatusServlet")
public class CheckGameStatusServlet extends HttpServlet {
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
            String gameQuery = "SELECT game_status, current_turn FROM game_state WHERE (player1_id = ? OR player2_id = ?) AND game_status = 'ship-placement'";
            PreparedStatement pstmt = conn.prepareStatement(gameQuery);
            pstmt.setInt(1, playerId);
            pstmt.setInt(2, playerId);
            ResultSet rs = pstmt.executeQuery();

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();

            if (rs.next()) {
                String status = rs.getString("game_status");
                session.setAttribute("turn_player", rs.getInt("current_turn"));
                out.print("{\"status\": \"" + status + "\"}");
            } else {
                out.print("{\"status\": \"waiting\"}");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
