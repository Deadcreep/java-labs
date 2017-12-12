public class Precision {
    public static void main(String[] args){
        float f1 = 0.00000001f;
        float f2 = 0.1f;

        float f3 = f1 + f2;
        double d1 = f1 + f2;

        boolean bool = f1 + f2 == 0.10000001;

        double f4 = d1 - f1;

        System.out.println( bool ); //false
        System.out.println(  "f3: " + f3 + "    d1: " + d1 ); // f3: 0.10000001    d1: 0.10000000894069672
        System.out.println( f3 == d1 );
        System.out.println( f4 == f2 );

    }

}
