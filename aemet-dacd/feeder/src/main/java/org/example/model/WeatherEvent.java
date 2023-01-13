package org.example.model;

import java.util.Objects;

public record WeatherEvent(String timestamp, String stationName, String stationPlace, double temperature) {

    public boolean equals(Object o) { //Se sobreescriben los métodos equals y hashCode
        if (this == o) return true;   // para que a la hora de aplicar contains(), sea necesario que para que devuelva true,
        if (o == null || getClass() != o.getClass()) return false; //todos los campos sean iguales
        WeatherEvent that = (WeatherEvent) o;
        return Double.compare(that.temperature, temperature) == 0 &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(stationName, that.stationName) &&
                Objects.equals(stationPlace, that.stationPlace);
    }
    public int hashCode() {
        return Objects.hash(timestamp, stationName, stationPlace, temperature);
    }
}
