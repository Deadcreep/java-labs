package network.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {
		
		// экземпляр нашего сервера
    private Server server;
		// исходящее сообщение
    private PrintWriter outMessage;
		// входящее собщение
    private Scanner inMessage;

		// клиентский сокет
    private Socket clientSocket = null;
		// количество клиента в чате, статичное поле
    private static int clients_count = 0;
    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());

    private static final Client client = new Client();

		// конструктор, который принимает клиентский сокет и сервер
    public ClientHandler(Socket socket, Server server) {
        try {
            clients_count++;
            this.server = server;
            this.clientSocket = socket;
            this.outMessage = new PrintWriter(socket.getOutputStream());
            this.inMessage = new Scanner(socket.getInputStream());
            logger.info("New client is accepted for handling");
        } catch (IOException ex) {
            logger.log(Level.INFO, "Exception ", ex);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
								// сервер отправляет сообщение
                String msg = "Новый участник вошёл в чат!";
                server.sendBroadcastMessage(msg);
                logger.info("Server send broadcast message" + msg);
                msg = "Клиентов в чате = " + clients_count;
                server.sendBroadcastMessage(msg);
                logger.info("Server send broadcast message" + msg);
                break;
            }

            while (true) {
                // Если от клиента пришло сообщение
                if (inMessage.hasNext()) {
                    String clientMessage = inMessage.nextLine();
										// если клиент отправляет данное сообщение, то цикл прерывается и 
										// клиент выходит из чата
                    if (clientMessage.equalsIgnoreCase("##session##end##")) {
                        logger.info(client.Id + " closed connection");
                        break;
                    }
										// выводим в консоль сообщение (для теста)
                    logger.info(client.Id + ": " + clientMessage);
										// отправляем данное сообщение всем клиентам
                    server.sendBroadcastMessage(clientMessage);
                }
								// останавливаем выполнение потока на 100 мс
                Thread.sleep(100);
            }
        }
        catch (InterruptedException ex) {
            logger.log(Level.INFO, "Exception ", ex);
        }
        finally {
            this.close();
        }
    }
		// отправляем сообщение
    public void sendMsg(String msg) {
        try {
            outMessage.println(msg);
            outMessage.flush();
        } catch (Exception ex) {
            logger.log(Level.INFO, "Exception ", ex);
        }
    }
		// клиент выходит из чата
    public void close() {
				// удаляем клиента из списка
        logger.info("Remove client: " + client.Id);
        server.removeClient(this);
        clients_count--;
        server.sendBroadcastMessage("Клиентов в чате = " + clients_count);
    }
}
