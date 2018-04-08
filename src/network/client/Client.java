package network.client;

import java.io.IOException;
import java.net.Socket;

public class Client {

    private String Name;
    private Socket socket;

    public Client(String serverHost, int serverPort) throws  IOException
    {
        socket = new Socket(serverHost, serverPort);
    }

    public Client(String name, String serverHost, int serverPort) throws IOException
    {
        Name = name;
        socket = new Socket(serverHost, serverPort);
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String name)
    {
        Name = name;
    }

    public Socket getSocket() {
        return socket;
    }

}
