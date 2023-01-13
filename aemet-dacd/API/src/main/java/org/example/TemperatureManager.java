package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;

public class TemperatureManager implements TemperatureService {
    private final DatabaseManager databaseManager;

    public TemperatureManager(DatabaseManager databaseManager) {

        this.databaseManager = databaseManager;
    }

    public List<APITemperature> getPlacesWithMaxAPITemperature(String from, String to) throws SQLException {
        String sql = "SELECT date, place, value FROM tempMax WHERE date BETWEEN ? AND ?";
        return databaseManager.getAPITemperatures(from, to, sql);
    }

    public List<APITemperature> getPlacesWithMinAPITemperature(String from, String to) throws SQLException {
        String sql = "SELECT date, place, value FROM tempMin WHERE date BETWEEN ? AND ?";
        return databaseManager.getAPITemperatures(from, to, sql);
    }

    public String toJson(List<APITemperature> APITemperatures) {
        // Se crea el objeto JSON que se va a devolver
        JSONObject json = new JSONObject();
        // Se crea una lista de objetos JSON que representan a las temperaturas
        JSONArray jsonAPITemperatures = new JSONArray();
        for (APITemperature APITemperature : APITemperatures) {
            // Se crea un objeto JSON para cada temperatura
            JSONObject jsonAPITemperature = new JSONObject();
            // Se añaden los campos de la temperatura al objeto JSON
            jsonAPITemperature.put("date", APITemperature.DATE());
            jsonAPITemperature.put("place", APITemperature.PLACE());
            jsonAPITemperature.put("value", APITemperature.VALUE());
            jsonAPITemperatures.put(jsonAPITemperature);
        }
        // Se añade la lista de objetos JSON al objeto principal
        json.put("Places", jsonAPITemperatures);
        // Se devuelve el objeto principal como una cadena de texto en formato JSON
        return json.toString();
    }

}