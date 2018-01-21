package agh.iisg.lab;

import agh.iisg.lab.airly.AirlyException;
import agh.iisg.lab.command.Command;
import agh.iisg.lab.command.CommandException;
import agh.iisg.lab.console.Console;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.text.ParseException;

public class Main {

    public static void main(String[] args) {

        try {
            Command appCommand = Command.getCommand(args);
            AirlyClient airlyClient = new AirlyClient(appCommand);
            if (airlyClient.getAppCommand().showHistory)
                System.out.println(new Console(airlyClient.getResponse().getBody(), appCommand).printHistory());
            else System.out.println(new Console(airlyClient.getResponse().getBody(), appCommand).printMeasurements());
        } catch (UnirestException | ParseException | CommandException | AirlyException e) {
            System.out.println(e);
        }
    }
}
