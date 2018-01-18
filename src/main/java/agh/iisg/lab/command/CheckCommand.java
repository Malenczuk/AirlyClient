package agh.iisg.lab.command;

public class CheckCommand {
    private final Command appCommand;

    CheckCommand(Command appCommand) throws CommandException {
        this.appCommand = appCommand;
        setApiKey();
        checkApiKey();
        checkSensorAndOrCoordinates();
    }

    private void setApiKey() {
        if (appCommand.apiKey != null && System.getenv().get("API_KEY") != null)
            appCommand.apiKey = System.getenv().get("API_KEY");
    }

    private void checkApiKey() throws CommandException {
        if (appCommand.apiKey == null) {
            throw new CommandException("You must provide API_KEY via env variable or --api-key option.");
        }
    }

    private void checkSensorAndOrCoordinates() throws CommandException {
        if ((appCommand.latitude == null && appCommand.longitude != null)
                || (appCommand.latitude != null && appCommand.longitude == null))
            throw new CommandException("You must provide both of latitude and longitude.");
        else if (appCommand.sensorId != null && appCommand.longitude != null && appCommand.latitude != null)
            throw new CommandException("Too much options. " +
                    "You must provide either latitude and longitude or sensor id.");
        else if (appCommand.sensorId == null && appCommand.longitude == null && appCommand.latitude == null)
            throw new CommandException("You must provide one option group. Provide SensorID or coordinates");
    }
}
