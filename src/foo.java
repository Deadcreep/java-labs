import java.util.ArrayList;
import java.util.Arrays;

public class foo {
    public static void main(String[] args){
        Integer[] intItems = {1, 6, 3, 9, 4, 9, 11, 90, 324, 65, 23};
        System.out.println( Arrays.asList( intItems).toString().replace( "[", "" ).replace( "]", "" ) );
    }
}
