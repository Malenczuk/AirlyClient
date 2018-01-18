package agh.iisg.lab.airly;

public class AirlyException extends Exception {
    public AirlyException() { super(); }
    public AirlyException(String message) { super(message); }
    public AirlyException(String message, Throwable cause) { super(message, cause); }
    public AirlyException(Throwable cause) { super(cause); }
}

