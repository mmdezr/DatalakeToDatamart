package org.example;

import java.sql.SQLException;

public interface Datamart {
    void addMin(Temperature temperature) throws SQLException;

    void addMax(Temperature temperature) throws SQLException;
}
