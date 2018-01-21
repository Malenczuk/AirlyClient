package agh.iisg.lab.console;

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
    private PrettyPrinter prettyPrinter = new PrettyPrinter();


    public Console(Measurement measurement, Command appCommand) {
        this.measurement = measurement;
        this.appCommand = appCommand;
    }


    public String printMeasurements() throws AirlyException {
        return airly + prettyPrinter.prettyPrint(measurement.getCurrentMeasurements(), null, null);
    }

    public String printHistory() throws ParseException, AirlyException {
        for (History history : measurement.getHistory()) {
            if (history.getMeasurements() != null) {
                final SimpleDateFormat parseDate = new SimpleDateFormat("yyyy-MM-DD'T'HH:mm:ss'Z'");
                final SimpleDateFormat formatDate = new SimpleDateFormat("MM.DD HH:mm");
                String From = (formatDate.format(parseDate.parse(history.getFromDateTime())));
                String To = (formatDate.format(parseDate.parse(history.getTillDateTime())));
                stringBuilder.append(prettyPrinter.prettyPrint(measurement.getCurrentMeasurements(), From, To));
            }
        }
        return airly + stringBuilder.toString();
    }
}
