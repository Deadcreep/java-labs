import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Exchanger;
import java.util.logging.Logger;
import java.util.concurrent.Exchanger.*;

public class ClientHandler implements Runnable {

    private static Logger log = Logger.getLogger(Server.class.getName());
    private static Socket client;
    Exchanger<String> exchanger;

    public ClientHandler(Socket client, Exchanger<String> ex) {
        this.client = client;
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
                String entry = in.readUTF();
                entry = entry + exchanger.exchange(entry);
                out.writeUTF(entry);
                log.info(entry);
                if (entry.equalsIgnoreCase("quit")) {
                    log.info("Connection closed");
                    Thread.sleep(3000);
                    break;
                }
                out.flush();
            }
            in.close();
            out.close();
            client.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
