package com.example.battleships.asset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    public Connection conn;

    public String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=BattleShips;user=Robertusos;password=pokember2;encrypt=true;trustServerCertificate=true;";


    public DatabaseManager(){
        connect();
    }

    public void connect(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.conn = DriverManager.getConnection(this.connectionString);
            System.out.println(this.conn);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error connecting to DB");
            e.printStackTrace();
        }
    }
}
