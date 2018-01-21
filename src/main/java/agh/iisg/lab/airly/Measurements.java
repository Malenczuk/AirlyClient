package agh.iisg.lab.airly;

public class Measurements {

  private Double airQualityIndex;
  private Double pm1;
  private Double pm25;
  private Double pm10;
  private Double pressure;
  private Double humidity;
  private Double temperature;
  private Double pollutionLevel;
  private Double windDirection;
  private Double windSpeed;

  public Double getAirQualityIndex() {
    return airQualityIndex;
  }

  public void setAirQualityIndex(Double airQualityIndex) {
    this.airQualityIndex = airQualityIndex;
  }

  public Double getPm1() {
    return pm1;
  }

  public void setPm1(Double pm1) {
    this.pm1 = pm1;
  }

  public Double getPm25() {
    return pm25;
  }

  public void setPm25(Double pm25) {
    this.pm25 = pm25;
  }

  public Double getPm10() {
    return pm10;
  }

  public void setPm10(Double pm10) {
    this.pm10 = pm10;
  }

  public Double getPressure() {
    return pressure;
  }

  public void setPressure(Double pressure) {
    this.pressure = pressure;
  }

  public Double getHumidity() {
    return humidity;
  }

  public void setHumidity(Double humidity) {
    this.humidity = humidity;
  }

  public Double getTemperature() {
    return temperature;
  }

  public void setTemperature(Double temperature) {
    this.temperature = temperature;
  }

  public Double getPollutionLevel() {
    return pollutionLevel;
  }

  public void setPollutionLevel(Double pollutionLevel) { this.pollutionLevel = pollutionLevel; }

  public Double getWindDirection() { return windDirection; }

  public void setWindDirection(Double windDirection) { this.windDirection = windDirection; }

  public Double getWindSpeed() { return windSpeed; }

  public void setWindSpeed(Double windSpeed) { this.windSpeed = windSpeed; }
}
