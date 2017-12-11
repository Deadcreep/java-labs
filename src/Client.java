import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;


public class Client {
    private static Logger log = Logger.getLogger(Client.class.getName());
    Socket socket;
    BufferedReader br;
    DataOutputStream oos;
    DataInputStream ois;
    String currentMessage;
    String receiveMessage;


    public class CustomListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public Client() throws IOException, InterruptedException {
        socket = new Socket("localhost", 8778);
        br = new BufferedReader(new InputStreamReader(System.in));
        oos = new DataOutputStream(socket.getOutputStream());
        ois = new DataInputStream(socket.getInputStream());
        listening();

    }

    public void sendMessage(String message) throws IOException {
        oos.writeUTF(message);
        oos.flush();
        log.info("Client sent message " + message + " to server.");
    }

    public void close() throws IOException {
        oos.writeUTF("quit");
        socket.close();
        br.close();
        oos.flush();
        oos.close();
        ois.close();
        log.info("Client closed, check isClosed: " + socket.isClosed());
    }

    public void listening() throws InterruptedException {
        try
        {
            log.info("Client connected to server");
            while (!socket.isOutputShutdown())
            {
                receiveMessage = ois.readUTF();
                System.out.print(receiveMessage);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
