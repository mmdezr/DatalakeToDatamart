package org.example.model;

import org.example.model.WeatherEvent;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public interface DataDownloader {
    String get(String url) throws IOException;

    Set<WeatherEvent> dataReader(File dataLakeDirectory) throws IOException;

    Set<WeatherEvent> downloadData() throws IOException, InterruptedException;

    Set<WeatherEvent> processResponse(String dataResponse) throws IOException;
}
