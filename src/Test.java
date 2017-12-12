import javax.swing.*;
import java.io.IOException;

public class Test {

    public static void main(String[] args)
    {
        try
        {
            Client client = new Client();
            Window window = new Window();
            client.addReceiveEventListener(window);
            window.addSortButtonListener(client);
            window.setVisible(true);

            Integer[] intItems = {1, 6, 3, 9, 4, 9, 11, 90, 324, 65, 23};
            Double[] doubleItems = {0.1, 4.5, 7.8, 32.4, 10.14, 10.0014, 11d};
            String[] stringItems = {"a", "as", "1", "32"};

            System.out.println("Int sort:");
            BubbleSorter.sort(intItems);
            for (Integer intItem : intItems) {
                System.out.print( intItem + " " );
            }

            System.out.println("\nDouble sort:");
            BubbleSorter.sort(doubleItems);
            for (Double doubleItem : doubleItems) {
                System.out.print( doubleItem + " " );
            }

            System.out.println("\nString sort:");
            BubbleSorter.sort(stringItems);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Incorrect argument");
        }
        catch (IOException ioe)
        {
            System.out.println( ioe);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
