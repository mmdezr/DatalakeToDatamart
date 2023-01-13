package org.example;

import java.sql.SQLException;

public class Main {
        public static void main(String[] args) {
            try {
                DatabaseManager databaseManager = new DatabaseManager();
                TemperatureManager temperatureManager = new TemperatureManager(databaseManager);
                APIResponse api = new APIResponse(temperatureManager);
                api.start();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

