package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

public class DatalakeGenerator {
    private OutputStream createFile(Date date) throws IOException {
        File directory = new File("datalake");
        if (!directory.exists()) {
            boolean mkdirsResult = directory.mkdirs();//Si no existe el directorio, lo crea
            if (!mkdirsResult) {
                System.out.println("No se pudo crear el directorio");
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        formatter.setTimeZone(TimeZone.getTimeZone("Atlantic/Canary"));
        String filename = formatter.format(date) + ".events";
        File file = new File(directory, filename);
        return new FileOutputStream(file, true);
    }

    public void writeData(Set<WeatherEvent> events, OutputStream outputStream) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        for (WeatherEvent event : events) {
            String json = gson.toJson(event);
            outputStream.write(json.getBytes());
            outputStream.write("\n".getBytes());
        }
    }

    public void generate(Set<WeatherEvent> events, Date date) throws IOException {
        OutputStream outputStream = createFile(date);
        writeData(events, outputStream);
        outputStream.close();
    }
}
