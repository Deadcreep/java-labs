import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.*;


public class Window extends JFrame {

    JPanel inputPanel;
    JTextField inputTopic;
    JTextField inputField;
    JTextField currentInput;
    JButton sortButton;

    JPanel outputPanel;
    JTextField outputTopic;
    JTextField sortResult;

    ArrayList<String> currentNumbers = new ArrayList<>();

    Window(){
        super("Bubble sort");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createInputPanel();
        createOutputPanel();

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.SOUTH);
        pack();
        //setSize(650, 400);
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

                currentNumbers.add(inputField.getText());
                currentInput.setText(currentInput.getText() + inputField.getText() + " ");

                inputField.setCaretPosition(0);
                inputField.setText(null);
            }
        });
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char temp = e.getKeyChar();
                System.out.print(temp + " ");
                super.keyTyped(e);
                if(!Character.isDigit(temp))
                {
                    if(temp != '.')
                    {
                        e.consume();
                    }
                    if(temp == '.' && (inputField.getText().contains(".") || inputField.getText().equals("")))
                    {
                        e.consume();
                    }
                }
            }
        });


        sortButton = new JButton("Sort");
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                BubbleSorter.sort(currentNumbers);
                sortResult.setText("");
                for (int i = 0; i < currentNumbers.size(); i++)
                {
                    sortResult.setText(sortResult.getText() + currentNumbers.get(i) + " ");
                }
            }
        });

        currentInput = new JTextField("", 10);

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
        outputPanel.add(outputTopic);
        outputPanel.add(sortResult);
    }
}