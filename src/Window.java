import javafx.scene.input.KeyCode;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;


public class Window extends JFrame implements CustomListener{

    JPanel inputPanel;
    JTextField inputTopic;
    JTextField inputField;
    JTextField currentInput;
    JButton sortButton;

    JPanel outputPanel;
    JTextField outputTopic;
    JTextField sortResult;
    JTextField errorField;

    CustomListener listener;

    ArrayList<String> currentNumbers = new ArrayList<>();

    Window() throws IOException, InterruptedException {
        super("Bubble sort");
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
                sortResult.setText("");
                for (int i = 0; i < currentNumbers.size(); i++)
                {
                    sortResult.setText(sortResult.getText() + currentNumbers.get(i) + " ");
                }
                errorField.setText(null);
                listener.raiseEvent( new CustomEvent(currentInput) );
                currentInput.setText(null);
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
        outputPanel.add(outputTopic);
        outputPanel.add(sortResult);
    }

    public void addSortButtonListener(CustomListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void raiseEvent(CustomEvent e) {
        String message = (String) e.getSource();
        System.out.print( "Raise event message : " + message );
        sortResult.setText(message);
    }
}