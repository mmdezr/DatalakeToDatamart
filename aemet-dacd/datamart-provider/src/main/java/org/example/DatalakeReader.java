package org.example;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class DatalakeReader{
        public Set<Set<Temperature>> read(File directory) throws IOException, NullPointerException {
            Set<Set<Temperature>> allEvents = new HashSet<>();
        for (File file: Objects.requireNonNull(directory.listFiles())){// Recorremos los archivos que se encuentran en el datalake
            Set<Temperature> events = new HashSet<>();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line= br.readLine())!=null){
                JsonObject jsonObject = new Gson().fromJson(line, JsonObject.class);
                String instant = jsonObject.getAsJsonPrimitive("timestamp").getAsString();
                // Cogemos la fecha y la hora del instant
                String date = instant.substring(0, 10);
                String time = instant.substring(11, 19);
                String stationName = jsonObject.getAsJsonPrimitive("stationName").getAsString();
                String stationPlace = jsonObject.getAsJsonPrimitive("stationPlace").getAsString();
                double temp = jsonObject.getAsJsonPrimitive("temperature").getAsDouble();
                Temperature event = new Temperature(date, stationName, time, stationPlace, temp);
                events.add(event);
            }
            allEvents.add(events);
        }
        return allEvents;
    }
    public Temperature selectMax(Set<Temperature> allEvents) {
        Temperature maxEvent = null;
        for (Temperature event : allEvents) {
            if (maxEvent == null) {
                maxEvent = event;
            } else if (event.VALUE() > maxEvent.VALUE()) {
                maxEvent = event;
            }
        }
        return maxEvent;
    }

    public Temperature selectMin(Set<Temperature> events) {
        Temperature minEvent = null;
        for (Temperature event : events) {
            if (minEvent == null) {
                minEvent = event;
            } else if (event.VALUE() < minEvent.VALUE()) {
                minEvent = event;
            }
        }
        return minEvent;
    }
    public void processEvents() throws IOException, SQLException, InterruptedException {
        File directory = new File("datalake");
        Set<Set<Temperature>> allEvents = read(directory);
        SQLiteStore sqLiteStore = new SQLiteStore();
        for (Set<Temperature> events : allEvents) {
            Temperature maxEvent = selectMax(events);
            Temperature minEvent = selectMin(events);
            if (minEvent != null) {
                sqLiteStore.addMin(minEvent);
                System.out.println(minEvent);
            }
            if (maxEvent != null) {
                System.out.println(maxEvent);
                sqLiteStore.addMax(maxEvent);
            }
        }
    }
}
