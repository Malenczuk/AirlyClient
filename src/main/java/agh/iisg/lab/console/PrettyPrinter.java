package agh.iisg.lab.console;

import agh.iisg.lab.airly.Measurements;
import agh.iisg.lab.airly.SensorData;

public class PrettyPrinter {
    private StringBuilder stringBuilder;
    private static final String RESET = "\033[0m";
    private static final String BOLD = "\033[1m";
    public static final String VERYLOW = "\033[38;5;46;1m";
    public static final String LOW = "\033[38;5;226;1m";
    public static final String MEDIUM = "\033[38;5;214;1m";
    public static final String HIGH = "\033[38;5;202;1m";
    public static final String VERYHIGH = "\033[38;5;196;1m";
    public static final String EXTREME = "\033[38;5;124;1m";

    private static final String prettyCaqi0 = "│        " + BOLD + "Great air here today!" + RESET + "        │" +
            "\n" + "│   Don’t hesitate to go out today.   │" + "\n";
    private static final String prettyCaqi25 = "│         " + BOLD + "Air is quite good." + RESET + "          │" +
            "\n" + "│  Good day for outdoor activities.   │" + "\n";
    private static final String prettyCaqi50 = "│       " + BOLD + "Well… It’s been better." + RESET + "       │" +
            "\n" + "│    Don’t do sport outside today.    │" + "\n";
    private static final String prettyCaqi75 = "│          " + BOLD + "Poor air quality!" + RESET + "          │" +
            "\n" + "│ If possible, avoid going out today. │" + "\n";
    private static final String prettyCaqi85 = "│          " + BOLD + "Poor air quality!" + RESET + "          │" +
            "\n" + "│ If possible, avoid going out today. │" + "\n";
    private static final String prettyCaqi100 = "│        " + BOLD + "Very high pollution!" + RESET + "         │" +
            "\n" + "│         Stay today at home.         │" +
            "\n" + "│ The air is threatening your health. │" + "\n";
    private static final String prettyCaqi = "├─────────────────────────────────────┤" + "\n";
    private static final String caqiFst = "              ┌─────────┐              " + "\n" + "┌─────────────┤";
    private static final String caqiSnd = "├─────────────┐" + "\n";
    private static final String caqiTrd = "│             └─────────┘             │" + "\n";
    private static final String caqiHist = " └─────────┘ ";
    private static final String noCaqi = "┌─────────────────────────────────────┐" + "\n";
    private static final String begin = "│ ";
    private static final String end = " │" + "\n";
    private static final String last = "└─────────────────────────────────────┘" + "\n";

    public String prettyPrint(Measurements measurements, String From, String To) {
        stringBuilder = new StringBuilder();
        Double value;
        SensorData sensorData = SensorData.AirQualityIndex;
        if ((value = measurements.getAirQualityIndex()) != null) {
            int caqi = (int) Math.round(value);
            stringBuilder.append(caqiFst);
            setColor(caqi);
            stringBuilder.append("CAQI");
            addSpace(5 - Integer.toString(caqi).length());
            stringBuilder.append(caqi)
                    .append(caqiSnd);
            if (From == null || To == null) {
                stringBuilder.append(caqiTrd);
                addCaqiText(caqi);
            } else {
                stringBuilder.append(begin)
                        .append(From)
                        .append(caqiHist)
                        .append(To)
                        .append(end);
            }
            stringBuilder.append(prettyCaqi);
        } else stringBuilder.append(noCaqi);

        while ((sensorData = sensorData.next()) != null) {
            if ((value = sensorData.getValue(measurements)) != null) {
                int len = Integer.toString((int) Math.round(value)).length();
                int per = sensorData.getPercentage((int) Math.round(value));
                String percent = sensorData.hasPercentage() ? Integer.toString(per) + " %" : "";
                stringBuilder.append(begin)
                        .append(sensorData.getName())
                        .append(Math.round(value))
                        .append(sensorData.getUnit());
                addSpace(19 - len - sensorData.getUnit().length() - percent.length());
                stringBuilder.append(percent).append(end);
            }
        }
        stringBuilder.append(last);
        return stringBuilder.toString();
    }

    private void setColor(int caqi) {
        if (caqi > 100) stringBuilder.append(EXTREME);
        else if (caqi > 85) stringBuilder.append(VERYHIGH);
        else if (caqi > 75) stringBuilder.append(HIGH);
        else if (caqi > 50) stringBuilder.append(MEDIUM);
        else if (caqi > 25) stringBuilder.append(LOW);
        else stringBuilder.append(VERYLOW);
    }

    private void addSpace(int n) {
        while (n-- > 0)
            stringBuilder.append(" ");
    }

    private void addCaqiText(int caqi) {
        if (caqi > 100) stringBuilder.append(prettyCaqi100);
        else if (caqi > 85) stringBuilder.append(prettyCaqi85);
        else if (caqi > 75) stringBuilder.append(prettyCaqi75);
        else if (caqi > 50) stringBuilder.append(prettyCaqi50);
        else if (caqi > 25) stringBuilder.append(prettyCaqi25);
        else stringBuilder.append(prettyCaqi0);
    }
}
