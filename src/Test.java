import javax.swing.*;

public class Test {

    public static void main(String[] args)
    {
        Integer[] intItems = {1, 6, 3, 9, 4, 9, 11, 90, 324, 65, 23};
        Double[] doubleItems = {0.1, 4.5, 7.8, 32.4, 10.14, 10.0014, 11d};
        String[] stringItems = {"a", "as", "1", "32"};

        JFrame myWindow = new Window();
        myWindow.setVisible(true);

        try
        {
            System.out.println("Int sort:");
            BubbleSorter.sort(intItems);
            for (int i = 0; i < intItems.length; i++) {
                System.out.print(intItems[i] + " ");
            }

            System.out.println("\n\nDouble sort:");
            BubbleSorter.sort(doubleItems);
            for (int i = 0; i < doubleItems.length; i++) {
                System.out.print(doubleItems[i] + " ");
            }

            System.out.println("\n\nString sort:");
            BubbleSorter.sort(stringItems);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Incorrect argument");
        }
    }
}
