package agh.iisg.lab;

import agh.iisg.lab.airly.*;
import agh.iisg.lab.command.Command;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Console {
    private final Measurement measurement;
    private final Command appCommand;
    private final String airly =
            " ______             ___                \n" +
                    "/\\  _  \\  __       /\\_ \\               \n" +
                    "\\ \\ \\L\\ \\/\\_\\  _ __\\//\\ \\    __  __    \n" +
                    " \\ \\  __ \\/\\ \\/\\`'__\\\\ \\ \\  /\\ \\/\\ \\   \n" +
                    "  \\ \\ \\/\\ \\ \\ \\ \\ \\/  \\_\\ \\_\\ \\ \\_\\ \\  \n" +
                    "   \\ \\_\\ \\_\\ \\_\\ \\_\\  /\\____\\\\/`____ \\ \n" +
                    "    \\/_/\\/_/\\/_/\\/_/  \\/____/ `/___/> \\\n" +
                    "                                 /\\___/\n" +
                    "                                 \\/__/ \n\n";
    private StringBuilder stringBuilder = new StringBuilder();


    public Console(Measurement measurement, Command appCommand) {
        this.measurement = measurement;
        this.appCommand = appCommand;
    }


    public String printMeasurements() throws AirlyException {
        buildSensorData(measurement.getCurrentMeasurements(), true);
        checkData();
        return airly + stringBuilder.toString();
    }

    public String printHistory() throws ParseException, AirlyException {
        for (History history : measurement.getHistory()) {
            if (history.getMeasurements() != null) {
                final SimpleDateFormat parseDate = new SimpleDateFormat("yyyy-MM-DD'T'HH:mm:ss'Z'");
                final SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm");
                stringBuilder
                        .append("\nFrom: ")
                        .append(formatDate.format(parseDate.parse(history.getFromDateTime())))
                        .append("\nTo: ")
                        .append(formatDate.format(parseDate.parse(history.getTillDateTime())));
                buildSensorData(history.getMeasurements(), false);
            }
        }
        checkData();
        return airly + stringBuilder.toString();
    }

    private void checkData() throws AirlyException {
        if (stringBuilder.toString().length() == 0) {
            if (appCommand.sensorId != null)
                throw new AirlyException("Couldn't fetch data by given sensor. Check params and your Internet connection.");
            throw new AirlyException("Couldn't fetch data by given location. Check params and your Internet connection.");
        }
    }

    public void buildSensorData(Measurements measurements, Boolean isCurrent) {
        SensorData sensorData = SensorData.AirQualityIndex;
        Double value;

        if ((value = measurements.getAirQualityIndex()) != null) {
            String color = getColorAndOrText(measurements, isCurrent);
            stringBuilder
                    .append("\n")
                    .append(color)
                    .append(sensorData.getName())
                    .append(Math.round(value))
                    .append(SensorData.RESET);
        }
        while ((sensorData = sensorData.next()) != null) {
            if ((value = sensorData.getValue(measurements)) != null) {
                stringBuilder
                        .append("\n")
                        .append(sensorData.getName())
                        .append(SensorData.BOLD)
                        .append(Math.round(value))
                        .append(SensorData.RESET)
                        .append(sensorData.getUnit());
                if (sensorData == SensorData.PM10 || sensorData == SensorData.PM25) {
                    stringBuilder
                            .append("   \t")
                            .append(SensorData.BOLD)
                            .append(Math.round(value / (sensorData == SensorData.PM10 ? 0.5 : 0.25)))
                            .append(" %")
                            .append(SensorData.RESET);
                }
            }
        }
        stringBuilder.append("\n");
    }

    private String getColorAndOrText(Measurements measurements, Boolean isCurrent) {
        long caqi = Math.round(measurements.getAirQualityIndex());
        String color;
        if (caqi > 100) {
            if (isCurrent) stringBuilder.append(SensorData.caqi100);
            color = SensorData.EXTREME;
        } else if (caqi > 85) {
            if (isCurrent) stringBuilder.append(SensorData.caqi85);
            color = SensorData.VERYHIGH;
        } else if (caqi > 75) {
            if (isCurrent) stringBuilder.append(SensorData.caqi75);
            color = SensorData.HIGH;
        } else if (caqi > 50) {
            if (isCurrent) stringBuilder.append(SensorData.caqi50);
            color = SensorData.MEDIUM;
        } else if (caqi > 25) {
            if (isCurrent) stringBuilder.append(SensorData.caqi25);
            color = SensorData.LOW;
        } else {
            if (isCurrent) stringBuilder.append(SensorData.caqi0);
            color = SensorData.VERYLOW;
        }
        return color;
    }
}
