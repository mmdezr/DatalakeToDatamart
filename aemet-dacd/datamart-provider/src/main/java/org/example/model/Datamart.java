package org.example.model;

import org.example.model.Temperature;

import java.sql.SQLException;

public interface Datamart {
    void addMin(Temperature temperature) throws SQLException;

    void addMax(Temperature temperature) throws SQLException;
}
