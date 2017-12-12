import org.omg.CORBA.TIMEOUT;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Time;
import java.util.EventObject;
import java.util.logging.Logger;


class Client implements CustomListener {
    private static Logger log = Logger.getLogger(Client.class.getName());
    private Socket socket;
    private DataOutputStream oos;
    private DataInputStream ois;
    private String receiveMessage;
    Thread receiveThread;
    CustomListener windowListener;

    Client() throws IOException, InterruptedException {
        socket = new Socket("localhost", 8778);
        oos = new DataOutputStream(socket.getOutputStream());
        receiveThread = new Thread( () -> {
            try
            {
                log.info("Client connected to server");
                while (!socket.isOutputShutdown())
                {
                    ois = new DataInputStream(socket.getInputStream());
                    Thread.sleep( 100 );
                    if(ois.available() > 0) {
                        receiveMessage = ois.readUTF();
                        log.info( "Receive message: " + receiveMessage );
                        windowListener.raiseEvent(new CustomEvent (receiveMessage));
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        } );
        receiveThread.setName( "Thread" + Thread.activeCount());
        receiveThread.start();
    }

    private void sendMessage(String message){
        try
        {
            oos.writeUTF( message );
            oos.flush();
            log.info( "Client sent message " + message + " to server." );
        }
        catch (IOException ioe)
        {
            log.info( ioe.getMessage() + " " + ioe.getStackTrace());
        }
    }

    public void close() throws IOException {
        oos.writeUTF("quit");
        socket.close();
        try {
            receiveThread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        oos.flush();
        oos.close();
        ois.close();
        log.info("Client closed, check isClosed: " + socket.isClosed());
    }

    public void raiseEvent(CustomEvent e) {
        String message = ((JTextField)e.getSource()).getText();
        sendMessage( message );
    }

    public void addReceiveEventListener(CustomListener listener)
    {
        this.windowListener = listener;
    }
}
