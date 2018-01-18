package agh.iisg.lab.airly;

import java.util.List;

public class Measurement {

  private CurrentMeasurements currentMeasurements;
  private List<History> history = null;
  private List<Forecast> forecast = null;

  public CurrentMeasurements getCurrentMeasurements() {
    return currentMeasurements;
  }

  public void setCurrentMeasurements(CurrentMeasurements currentMeasurements) {
    this.currentMeasurements = currentMeasurements;
  }

  public List<History> getHistory() {
    return history;
  }

  public void setHistory(List<History> history) {
    this.history = history;
  }

  public List<Forecast> getForecast() {
    return forecast;
  }

  public void setForecast(List<Forecast> forecast) {
    this.forecast = forecast;
  }
}
