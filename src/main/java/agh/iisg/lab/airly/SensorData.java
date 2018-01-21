package agh.iisg.lab.airly;

public enum SensorData {
    AirQualityIndex,
    PM1,
    PM25,
    PM10,
    Pressure,
    Humidity,
    Temperature,
    PollutionLevel;


    /**
     * Great air here today! Don’t hesitate to go out today
     * Air is quite good. Good day for outdoor activities
     * Well… It’s been better. Don’t do sport outside today
     * Poor air quality! If possible, avoid going out today
     * Poor air quality! If possible, avoid going out today
     * Very high pollution! Stay today at home.The air is threatening your health
     **/

    public static final String caqi0 = "\033[1mGreat air here today!\033[0m Don’t hesitate to go out today";
    public static final String caqi25 = "\033[1mAir is quite good.\033[0m Good day for outdoor activities";
    public static final String caqi50 = "\033[1mWell… It’s been better.\033[0m Don’t do sport outside today";
    public static final String caqi75 = "\033[1mPoor air quality!\033[0m If possible, avoid going out today";
    public static final String caqi85 = "\033[1mPoor air quality!\033[0m If possible, avoid going out today";
    public static final String caqi100 = "\033[1mVery high pollution!\033[0m Stay today at home.The air is threatening your health";

    public SensorData next() {
        switch (this) {
            case AirQualityIndex:
                return PM1;
            case PM1:
                return PM25;
            case PM25:
                return PM10;
            case PM10:
                return Pressure;
            case Pressure:
                return Humidity;
            case Humidity:
                return Temperature;
            case Temperature:
                return PollutionLevel;
            default:
                return null;
        }
    }

    public Double getValue(Measurements measurements) {
        switch (this) {
            case AirQualityIndex:
                return measurements.getAirQualityIndex();
            case PM1:
                return measurements.getPm1();
            case PM25:
                return measurements.getPm25();
            case PM10:
                return measurements.getPm10();
            case Pressure:
                return measurements.getPressure() == null ? null : measurements.getPressure() / 100;
            case Humidity:
                return measurements.getHumidity();
            case Temperature:
                return measurements.getTemperature();
            case PollutionLevel:
                return measurements.getPollutionLevel();
            default:
                return null;
        }

    }

    public String getUnit() {
        switch (this) {
            case PM1:
            case PM25:
            case PM10:
                return " μg/m³";
            case Humidity:
                return " %";
            case Pressure:
                return " hPa";
            case Temperature:
                return " °C";
            default:
                return "";
        }

    }

    public String getName() {
        switch (this) {
            case AirQualityIndex:
                return "CAQI            ";
            case PM1:
                return "PM1             ";
            case PM25:
                return "PM2.5           ";
            case PM10:
                return "PM10            ";
            case Pressure:
                return "Pressure        ";
            case Humidity:
                return "Humidity        ";
            case Temperature:
                return "Temperature     ";
            case PollutionLevel:
                return "PollutionLevel  ";
            default:
                return null;
        }
    }

    public boolean hasPercentage(){
        return this == PM25 || this == PM10;
    }

    public int getPercentage(int value){
        if (this == PM25)
            return value * 4;
        else if (this == PM10)
            return value * 2;
        return 0;
    }
}
