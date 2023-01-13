package org.example;

import java.sql.*;

public class SQLiteStore implements Datamart {
    private final Connection connection;

    public SQLiteStore() throws SQLException {
        connection = DriverManager.getConnection("jdbc:Sqlite:Datamart.db");
        initDataBase();
    }

    private static final String INIT =
            "CREATE TABLE IF NOT EXISTS tempMax (" +
                    "date PRIMARY KEY , " +
                    "time TEXT, " +
                    "place TEXT, " +
                    "station TEXT, " +
                    "value NUMBER)";
    private static final String INIT2 =
            "CREATE TABLE IF NOT EXISTS tempMin (" +
                    "date PRIMARY KEY, " +
                    "time TEXT, " +
                    "place TEXT, " +
                    "station TEXT, " +
                    "value NUMBER)";

    private void initDataBase() throws SQLException {
        connection.createStatement().execute(INIT);
        connection.createStatement().execute(INIT2);
    }

    public void addMax(Temperature temperature) {
        try {
            connection.createStatement().execute(DMLTranslator.insertTempMaxStatementOf(temperature));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMin(Temperature temperature) {
        try {
            connection.createStatement().execute(DMLTranslator.insertTempMinStatementOf(temperature));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
