import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;

public class Server {

    private static Logger log = Logger.getLogger(Server.class.getName());
    private static ExecutorService executeIt = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws InterruptedException {
        try
        {
            Exchanger<String> exchanger = new Exchanger<>();
            ServerSocket server= new ServerSocket(8778);
            log.info("Server started");
            while (!server.isClosed())
            {
                log.info("Message came");
                Socket client = server.accept();
                executeIt.execute(new ClientHandler(client, exchanger));
                System.out.println( Thread.activeCount());
            }
        }
        catch (IOException e)
        {
            System.out.println(e + e.getMessage());
        }
        finally
        {
            executeIt.shutdown();
        }
    }
}
