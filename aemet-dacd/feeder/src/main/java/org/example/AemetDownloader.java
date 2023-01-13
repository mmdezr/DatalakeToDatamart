package org.example;

import com.google.gson.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class AemetDownloader implements DataDownloader {
    String url = "https://opendata.aemet.es/opendata/api/observacion/convencional/todas";
    Set<WeatherEvent> events = new HashSet<>();

    public String get(String url) throws IOException {
        String apiKey = "Your apiKey";
        return Jsoup.connect(url)
                .validateTLSCertificates(false)
                .timeout(15000)
                .ignoreContentType(true)
                .header("accept", "application/json")
                .header("api_key", apiKey)
                .method(Connection.Method.GET)
                .maxBodySize(0).execute().body();
    }

    public Set<WeatherEvent> downloadData() throws IOException, InterruptedException {
        String response = get(url);
        Gson gson = new Gson();
        String urlResponse = gson.fromJson(response, JsonObject.class).get("datos").getAsString();
        String dataResponse = get(urlResponse);
        return processResponse(dataResponse);
    }

    public Set<WeatherEvent> dataReader(File dataLakeDirectory) throws IOException {
        if (dataLakeDirectory.exists()) {
            for (File file : Objects.requireNonNull(dataLakeDirectory.listFiles())) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                List<String> lines = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
                reader.close();
                // Itera a través de los eventos del archivo
                for (String lin : lines) {
                    // Convierte el elemento en un objeto WeatherEvent
                    WeatherEvent event = new Gson().fromJson(lin, WeatherEvent.class);
                    events.add(event);
                }
            }
        }
        return events;
    }

    public Set<WeatherEvent> processResponse(String dataResponse) {
        Gson gson = new Gson();
        Set<WeatherEvent> newEvents = new HashSet<>();
        LocalDate currentDate = LocalDate.now();
        JsonArray weatherStations = gson.fromJson(dataResponse, JsonArray.class);        // obtiene el array de estaciones meteorológicas
        // itera sobre cada estación meteorológica
        for (JsonElement weatherStation : weatherStations) {
            // obtiene el objeto de la estación meteorológica
            JsonObject station = weatherStation.getAsJsonObject();
            double length = station.get("lon").getAsDouble();
            double latitude = station.get("lat").getAsDouble();
            if (-16 < length && length < -15 && 27.5 < latitude && latitude < 28.4) {
                String timestamp = station.get("fint").getAsString();                    // obtiene el timestamp de la medición
                LocalDate eventDate = LocalDate.parse(timestamp.substring(0, 10));
                String stationName = station.get("idema").getAsString();                    // obtiene el nombre de la estación
                String stationPlace = station.get("ubi").getAsString();                    // obtiene el lugar de la estación
                double temperature;
                if (station.has("ta")) {
                    temperature = station.get("ta").getAsDouble();                    // obtiene la temperatura del aire
                } else {
                    continue;
                }

                if (eventDate.isEqual(currentDate)) {// verifica si el evento ocurrió en el día actual
                    WeatherEvent event = new WeatherEvent(timestamp, stationName, stationPlace, temperature);// crea un evento con los datos obtenidos
                    if (!events.contains(event)) {
                        newEvents.add(event);
                    }
                }
            }
        }
        return newEvents;
    }

}
