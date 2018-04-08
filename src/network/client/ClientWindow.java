package network.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.net.Socket;
import java.util.Scanner;

public class ClientWindow extends JFrame implements Runnable {

    private Scanner inMessage;
    private PrintWriter outMessage;

    private JTextField jtfMessage;
    private JTextField jtfName;
    private JTextArea jtaTextAreaMessage;
    private JButton jbSendMessage;
    private JLabel jlNumberOfClients;

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8888;

    private Client client;

    // конструктор
    public ClientWindow() {

        try {
            client = new Client(SERVER_HOST, SERVER_PORT);
            Socket socket = client.getSocket();
            inMessage = new Scanner(socket.getInputStream());
            outMessage = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        initializeWindow(null);

        new Thread(this).start();
    }

    public void run()
    {
        try {
            while (true) {
                if (inMessage.hasNext()) {
                    String inMes = inMessage.nextLine();
                    String clientsInChat = "Клиентов в чате = ";
                    if (inMes.indexOf(clientsInChat) == 0) {
                        jlNumberOfClients.setText(inMes);
                    } else {
                        jtaTextAreaMessage.append(inMes);
                        jtaTextAreaMessage.append("\n");
                    }
                }
            }
        } catch (Exception e) {
        }
    }


    private void hangHandlers()
    {
        onJBSendMessage();
        onJTFMessage();
        onJTFName();
        onWindowsClosing();
    }

    // обработчик события нажатия кнопки отправки сообщения
    private void onJBSendMessage()
    {
        jbSendMessage.addActionListener( e -> {
            // если имя клиента, и сообщение непустые, то отправляем сообщение
            if (!jtfMessage.getText().trim().isEmpty() && !jtfName.getText().trim().isEmpty()) {
                client.setName(jtfName.getText());
                sendMsg();
                // фокус на текстовое поле с сообщением
                jtfMessage.grabFocus();
            }
        } );
    }

    // при фокусе поле сообщения очищается
    private void onJTFMessage()
    {
        jtfMessage.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtfMessage.setText("");
            }
        });
    }

    // при фокусе поле имя очищается
    private void onJTFName()
    {
        jtfName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtfName.setText("");
            }
        });
    }

    // добавляем обработчик события закрытия окна клиентского приложения
    private void onWindowsClosing()
    {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    String clientName = client.getName();
                    // здесь проверяем, что имя клиента непустое и не равно значению по умолчанию
                    if (!clientName.isEmpty() && clientName != "Введите ваше имя: ") {
                        outMessage.println(clientName + " вышел из чата!");
                    } else {
                        outMessage.println("Участник вышел из чата, так и не представившись!");
                    }
                    // отправляем служебное сообщение, которое является признаком того, что клиент вышел из чата
                    outMessage.println("##session##end##");
                    outMessage.flush();
                    outMessage.close();
                    inMessage.close();
                    client.getSocket().close();
                } catch (IOException exc) {

                }
            }
        });
    }

    private void initializeWindow(String title)
    {
        // Задаём настройки элементов на форме
        setBounds(600, 300, 600, 500);

        if(title == null) setTitle("Client");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jtaTextAreaMessage = new JTextArea();
        jtaTextAreaMessage.setEditable(false);
        jtaTextAreaMessage.setLineWrap(true);

        JScrollPane jsp = new JScrollPane(jtaTextAreaMessage);
        add(jsp, BorderLayout.CENTER);
        // label, который будет отражать количество клиентов в чате
        jlNumberOfClients = new JLabel("Количество клиентов в чате: ");
        add(jlNumberOfClients, BorderLayout.NORTH);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);
        jbSendMessage = new JButton("Отправить");
        bottomPanel.add(jbSendMessage, BorderLayout.EAST);
        jtfMessage = new JTextField("Введите ваше сообщение: ");
        bottomPanel.add(jtfMessage, BorderLayout.CENTER);
        jtfName = new JTextField("Введите ваше имя: ");
        bottomPanel.add(jtfName, BorderLayout.WEST);

        hangHandlers();

        // отображаем форму
        setVisible(true);
    }

    // отправка сообщения
    public void sendMsg() {
        // формируем сообщение для отправки на сервер
        String messageStr = jtfName.getText() + ": " + jtfMessage.getText();
        // отправляем сообщение
        outMessage.println(messageStr);
        outMessage.flush();
        jtfMessage.setText("");
    }
}

