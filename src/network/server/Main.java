package network.server;

import java.io.IOException;
import java.util.logging.LogManager;

public class Main {

    public static void main(String[] args) {
        try {
            LogManager.getLogManager().readConfiguration(
                    Main.class.getResourceAsStream("log.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }
        Server server = new Server();
    }
}