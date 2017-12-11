import javafx.scene.input.KeyCode;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;


public class Window extends JFrame {

    JPanel inputPanel;
    JTextField inputTopic;
    JTextField inputField;
    JTextField currentInput;
    JButton sortButton;

    JPanel outputPanel;
    JTextField outputTopic;
    JTextField sortResult;
    JButton resetButton;
    JTextField errorField;
    Client client;

    ArrayList<String> currentNumbers = new ArrayList<>();

    Window() throws IOException, InterruptedException {
        super("Bubble sort");
        client = new Client();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createInputPanel();
        createOutputPanel();

        errorField = new JTextField("");
        errorField.setBackground(this.getBackground());
        errorField.setForeground(Color.RED);
        errorField.setPreferredSize(new Dimension(50, 20));
        errorField.setEditable(false);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);
        add(errorField, BorderLayout.SOUTH);
        pack();

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    client.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                finally {
                    System.exit(0);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    private void createInputPanel(){
        inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.setMinimumSize(new Dimension(400, 400));
        inputPanel.setBorder(new EtchedBorder());

        inputTopic = new JTextField("Enter numbers");
        inputTopic.setEditable(false);
        inputTopic.setFont(new Font("SansSerif", Font.BOLD, 20));
        inputTopic.setBackground(this.getBackground());

        inputField = new JTextField("", 3);
        inputField.setPreferredSize(new Dimension(100, 20));
        inputField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(inputField.getText().isEmpty()))
                {
                    currentNumbers.add(inputField.getText());
                    currentInput.setText(currentInput.getText() + inputField.getText() + " ");
                    inputField.setCaretPosition(0);
                    inputField.setText(null);
                }
            }
        });
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char temp = e.getKeyChar();
                super.keyTyped(e);
                errorField.setText(null);
                if(!Character.isDigit(temp))
                {
                    if(temp != '.')
                    {
                        errorField.setText("Incorrect input");
                        e.consume();
                    }
                    if(temp == '.' && (inputField.getText().contains(".") || inputField.getText().equals("")))
                    {
                        errorField.setText("Incorrect input");
                        e.consume();
                    }
                }
            }
        });

        sortButton = new JButton("Sort");
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    BubbleSorter.sort(currentNumbers);
                    client.sendMessage(String.join(" ", currentNumbers));
                }
                catch (NumberFormatException nfe)
                {
                    errorField.setText("Incorrect input");
                }
                catch (IOException ioe)
                {

                }
                sortResult.setText("");
                for (int i = 0; i < currentNumbers.size(); i++)
                {
                    sortResult.setText(sortResult.getText() + currentNumbers.get(i) + " ");
                }
                errorField.setText(null);
            }
        });


        currentInput = new JTextField("", 10);
        currentInput.setEditable(false);
        currentInput.setBackground(Color.white);

        inputPanel.add(inputTopic);
        inputPanel.add(inputField);
        inputPanel.add(currentInput);
        inputPanel.add(sortButton);
    }

    private void createOutputPanel(){

        outputPanel = new JPanel();
        outputPanel.setMinimumSize(new Dimension(400, 400));
        outputPanel.setBorder(new EtchedBorder());
        outputTopic = new JTextField("Sort result");
        outputTopic.setBackground(this.getBackground());
        outputTopic.setEditable(false);
        outputTopic.setFont(new Font("SansSerif", Font.BOLD, 20));
        sortResult = new JTextField("", 20);
        sortResult.setEditable(false);
        sortResult.setBackground(Color.white);
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentNumbers = new ArrayList<>();
                currentInput.setText(null);
                sortResult.setText(null);
            }
        });
        outputPanel.add(outputTopic);
        outputPanel.add(sortResult);
        outputPanel.add(resetButton);
    }
}