package generics;

public class Test {
    public static void main(String[] args)
    {
        GenericClass<Integer> temp = new GenericClass<Integer>(new Integer(10));
        System.out.println(temp.getValue() instanceof Integer);

        GenericClass<String> tempTwo = new GenericClass<String>("Hello world");
        System.out.println(tempTwo.getValue() instanceof String);
        //Integer intValue = tempTwo.getValue();
    }
}
