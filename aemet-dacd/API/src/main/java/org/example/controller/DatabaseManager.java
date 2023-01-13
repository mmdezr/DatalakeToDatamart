package org.example.controller;

import org.example.model.APITemperature;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private final Connection connection;

    public DatabaseManager() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:Datamart.db");
    }
    public List<APITemperature> getAPITemperatures(String from, String to, String sql) throws SQLException {
        List<APITemperature> places = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, from);
        stmt.setString(2, to);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            places.add(new APITemperature(resultSet.getString("date"),
                    resultSet.getString("place"),
                    resultSet.getDouble("value")));
        }
        return places;
    }
}