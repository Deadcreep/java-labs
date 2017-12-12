import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Exchanger;
import java.util.logging.Logger;
import java.util.Arrays;


public class ClientHandler implements Runnable {

    private static Logger log = Logger.getLogger(Server.class.getName());
    private static Socket client;
    private Exchanger<String> exchanger;
    static String message = "";


    ClientHandler(Socket client, Exchanger<String> ex) {
        ClientHandler.client = client;
        exchanger = ex;
    }

    @Override
    public void run() {
        try
        {
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            log.info("Data output stream " + Thread.currentThread().getName() + " created");
            DataInputStream in = new DataInputStream(client.getInputStream());
            log.info("Data input stream " + Thread.currentThread().getName() + "  created");

            while (!client.isClosed())
            {
                /*String entry = in.readUTF();
                entry = entry + " " + exchanger.exchange(entry);
                out.writeUTF(entry);
                log.info(entry);
                if (entry.equalsIgnoreCase("quit")) {
                    log.info("Connection" + Thread.currentThread().getName() + " closed");
                    Thread.sleep(3000);
                    break;
                }
                out.writeUTF( entry );*/
                String temp = in.readUTF();
                log.info( temp );
                changeMessage( temp );
                log.info( message );
                out.writeUTF( message );

                out.flush();
            }
            in.close();
            out.close();
            client.close();
        }
        catch (IOException   e) {
            e.printStackTrace();
        }
    }

    private synchronized static void changeMessage(String addMessage){
        log.info("channgeMesage message: " + message );
        log.info( "channgeMesage addMessage: " + addMessage );
        if(message != "")
        {
            message += " ";
        }
        message += addMessage;
        log.info( "channgeMesage glued message: "+ message );
        String[] temp = message.split( " " );
        ArrayList<Integer> array = new ArrayList<>(  );
        for (String element :
                temp) {
            array.add( new Integer(element));
        }
        BubbleSorter.sort( array );
        String finalResult = Arrays.asList( array).toString()
                                                    .replace( "[", "" )
                                                    .replace( "]", "" )
                                                    .replace( ",", "" );
        message = finalResult;
    }


}
