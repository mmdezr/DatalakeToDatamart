package org.example.controller;

import org.example.model.Temperature;

public class DMLTranslator {
    private static final String INSERT_TEMPMAX =
            "INSERT INTO tempMax(date, time, place, station, value) VALUES ('%s', '%s', '%s', '%s', '%f') ON CONFLICT(date) DO NOTHING;";
    private static final String INSERT_TEMPMIN =
            "INSERT INTO tempMin(date, time, place, station, value) VALUES ('%s', '%s', '%s', '%s', '%f') ON CONFLICT(date) DO NOTHING;";

    public static String insertTempMaxStatementOf(Temperature temperature) {
        return String.format(INSERT_TEMPMAX,
                temperature.DATE(),
                temperature.TIME(),
                temperature.PLACE(),
                temperature.STATION(),
                temperature.VALUE());
    }

    public static String insertTempMinStatementOf(Temperature temperature) {
        return String.format(INSERT_TEMPMIN,
                temperature.DATE(),
                temperature.TIME(),
                temperature.PLACE(),
                temperature.STATION(),
                temperature.VALUE());
    }
}
