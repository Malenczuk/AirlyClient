package agh.iisg.lab;

import agh.iisg.lab.airly.AirlyException;
import agh.iisg.lab.airly.Measurement;
import agh.iisg.lab.command.Command;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AirlyClient {
    private static final String BASE_URL = "https://airapi.airly.eu/v1/";
    private Command appCommand;
    private String URL;
    private Map<String, String> HEADERS = new HashMap<>();
    private Map<String, Object> QUERYS = new HashMap<>();
    private HttpResponse<Measurement> response = null;
    //appCommand.apiKey = "4599bb144a9d4211bd254171a1ed88d9";

    public AirlyClient(Command appCommand) throws UnirestException, AirlyException {
        this.appCommand = appCommand;
        setObjectMapper();
        setHeaders();
        setQuerys();
        getHttpResponse();
        checkHttpResponse(response.getStatus(), response.getStatusText());
        if(appCommand.sensorId != null) checkSensor();
    }

    private void checkSensor() throws UnirestException, AirlyException {
        HttpResponse<JsonNode> res = Unirest.get(BASE_URL + "sensors/" + appCommand.sensorId)
                .headers(HEADERS)
                .asJson();
        checkHttpResponse(res.getStatus(), res.getStatusText());
    }


    private void checkHttpResponse(int status, String statusText) throws AirlyException {
        if (status == 401)
            throw new AirlyException(Integer.toString(status) + " Unauthorized.");
        if (status == 403)
            throw new AirlyException(Integer.toString(status) + " Forbidden");
        if (status == 404)
            throw new AirlyException(Integer.toString(status) + " Sensor Not Found");
        if (status == 400)
            throw new AirlyException("Input validation error. " + statusText);
        if (status == 405)
            throw new AirlyException("Unexpected error. " + statusText);
    }

    private void getHttpResponse() throws UnirestException {
        response = Unirest.get(URL)
                .queryString(QUERYS)
                .headers(HEADERS)
                .asObject(Measurement.class);
    }


    private void setQuerys() {
        if (appCommand.sensorId != null) {
            URL = BASE_URL + "sensor/measurements";
            QUERYS.put("sensorId", appCommand.sensorId);
        } else {
            URL = BASE_URL + "mapPoint/measurements";
            QUERYS.put("latitude", appCommand.latitude);
            QUERYS.put("longitude", appCommand.longitude);
        }
    }

    private void setHeaders() {
        HEADERS.put("Accept", "application/json");
        HEADERS.put("apiKey", appCommand.apiKey);
    }

    private void setObjectMapper() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper =
                    new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public HttpResponse<Measurement> getResponse() {
        return response;
    }

    public Command getAppCommand() {
        return appCommand;
    }
}
