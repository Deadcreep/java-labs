package network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    static final int PORT = 8888;
    private ArrayList<ClientHandler> clients = new ArrayList<>();

    private static final Logger logger = Logger.getLogger( Server.class.getName() );

    public Server() {
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket( PORT );
            logger.info( "Server is on" );

            while (true) {
                clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler( clientSocket, this );
                addClient( client );
                new Thread( client ).start();
            }
        } catch (IOException ex) {
            logger.log( Level.WARNING, "Exception ", ex );
        } finally {
            try {
                clientSocket.close();
                logger.info( "Server is off" );
                serverSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                logger.log( Level.WARNING, "Exception ", ex );
            }
        }
    }

    public synchronized void sendBroadcastMessage(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg( msg );
        }
    }

    public void addClient(ClientHandler client) {
        synchronized (clients) {
            clients.add( client );
        }
    }

    public synchronized void removeClient(ClientHandler client) {

        clients.remove( client );
    }

}
