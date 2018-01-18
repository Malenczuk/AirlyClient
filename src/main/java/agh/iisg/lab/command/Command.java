package agh.iisg.lab.command;

import picocli.CommandLine;

public class Command {
    @CommandLine.Option(
            names = {"--latitude"},
            description = "Latitude."
    )
    public Double latitude = null;

    @CommandLine.Option(
            names = {"--longitude"},
            description = "Longitude."
    )
    public Double longitude = null;

    @CommandLine.Option(
            names = {"-s", "--sensor-id"},
            description = "Sensor ID."
    )
    public Integer sensorId = null;

    @CommandLine.Option(
            names = {"-k", "--api-key"},
            description = "API KEY. Not needed if API_KEY environmental variable is present."
    )
    public String apiKey = null;

    @CommandLine.Option(
            names = {"--history"},
            description = "Shows shortened data for previous 24h."
    )
    public Boolean showHistory = false;

    @CommandLine.Option(
            names = {"-h", "--help"},
            description = "Show help and exit.")
    public Boolean helpRequested = false;

    public static Command getCommand(String[] args) throws CommandException {
        Command appCommand = CommandLine.populateCommand(new Command(), args);
        if (appCommand.helpRequested || args.length == 0) {
            CommandLine.usage(appCommand, System.out);
            System.exit(0);
        }
        new CheckCommand(appCommand);
        return appCommand;
    }
}
