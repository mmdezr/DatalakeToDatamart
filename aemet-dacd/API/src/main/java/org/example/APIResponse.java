package org.example;

import spark.Spark;
import java.util.List;

public class APIResponse {
    private final TemperatureManager TemperatureManager;

    public APIResponse(TemperatureManager TemperatureManager) {

        this.TemperatureManager = TemperatureManager;
    }

    public void start() {
        Spark.get("/v1/places/with-max-temperature", (request, response) -> {
            String from = request.queryParams("from");
            String to = request.queryParams("to");
            List<APITemperature> APITemperatures = TemperatureManager.getPlacesWithMaxAPITemperature(from, to);
            return TemperatureManager.toJson(APITemperatures);
        });

        Spark.get("/v1/places/with-min-temperature", (request, response) -> {
            String from = request.queryParams("from");
            String to = request.queryParams("to");
            List<APITemperature> APITemperatures = TemperatureManager.getPlacesWithMinAPITemperature(from, to);
            return TemperatureManager.toJson(APITemperatures);
        });
    }
}
