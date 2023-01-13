package org.example;

import org.example.controller.AemetDownloader;
import org.example.controller.DatalakeGenerator;
import org.example.model.WeatherEvent;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // descarga los datos de la AEMET
                AemetDownloader downloader = new AemetDownloader();
                Set<WeatherEvent> events;
                Set<WeatherEvent> newEvents;
                File dataLakeDirectory = new File("datalake");
                try {
                    events = downloader.dataReader(dataLakeDirectory);
                    newEvents = downloader.downloadData();
                    events.addAll(newEvents);
                } catch (IOException e) {
                    System.err.println("Error al descargar los datos de la AEMET: " + e.getMessage());
                    return;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                DatalakeGenerator generator = new DatalakeGenerator();
                try {
                    generator.generate(newEvents, new Date());
                } catch (IOException e) {
                    System.err.println("Error al generar el datalake: " + e.getMessage());
                }
            }
        };
        // crea un timer que ejecutará la tarea cada hora
        Timer timer = new Timer();
        timer.schedule(task, 0, 60 * 60 * 1000);
    }
}

