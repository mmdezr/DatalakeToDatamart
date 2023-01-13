package org.example.model;

import org.example.model.APITemperature;

import java.sql.SQLException;
import java.util.List;

public interface TemperatureService {
    List<APITemperature> getPlacesWithMaxAPITemperature(String from, String to) throws SQLException;

    List<APITemperature> getPlacesWithMinAPITemperature(String from, String to) throws SQLException;

    String toJson(List<APITemperature> temperatures);
}
