package types;

import java.util.*;

public class Main {
    public static void main(String[] args)
    {
        //string to something
        try {
            Byte b1 = new Byte("10");
            System.out.println(b1);

            Integer i1 = new Integer("20349");
            System.out.println(i1);
        } catch (NumberFormatException e) {
            System.err.println("Неверный формат строки");
        }

        //autoboxing
        int primitiveInt = 10;
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add( primitiveInt );
        System.out.println( arrayList.get( 0 ).getClass());
        primitiveInt = arrayList.get( 0 );
    }
}


