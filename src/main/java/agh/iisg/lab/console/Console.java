package agh.iisg.lab.console;

import agh.iisg.lab.airly.*;
import agh.iisg.lab.command.Command;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Console {
    private final Measurement measurement;
    private final Command appCommand;
    private final String airly =
                    "\033[31m ______             ___                \n" +
                    "\033[31m/\\  _  \\  __       /\\_ \\               \n" +
                    "\033[33m\\ \\ \\L\\ \\/\\_\\  _ __\\//\\ \\    __  __    \n" +
                    "\033[33m \\ \\  __ \\/\\ \\/\\`'__\\\\ \\ \\  /\\ \\/\\ \\   \n" +
                    "\033[32m  \\ \\ \\/\\ \\ \\ \\ \\ \\/  \\_\\ \\_\\ \\ \\_\\ \\  \n" +
                    "\033[36m   \\ \\_\\ \\_\\ \\_\\ \\_\\  /\\____\\\\/`____ \\ \n" +
                    "\033[34m    \\/_/\\/_/\\/_/\\/_/  \\/____/ `/___/> \\\n" +
                    "\033[35m                                 /\\___/\n" +
                    "\033[35m                                 \\/__/ \033[0m\n\n";
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
        boolean data = false;
        for (History history : measurement.getHistory()) {
            if (history.getMeasurements() != null) {
                data = true;
                final SimpleDateFormat parseDate = new SimpleDateFormat("yyyy-MM-DD'T'HH:mm:ss'Z'");
                final SimpleDateFormat formatDate = new SimpleDateFormat("MM.DD HH:mm");
                String From = (formatDate.format(parseDate.parse(history.getFromDateTime())));
                String To = (formatDate.format(parseDate.parse(history.getTillDateTime())));
                stringBuilder.append(prettyPrinter.prettyPrint(history.getMeasurements(), From, To));
            }
        }
        if(!data) throw new AirlyException("No Data Found");
        return airly + stringBuilder.toString();
    }
}
