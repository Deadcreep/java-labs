package oop;

public class Test {
    public static void main(String[] args){
        Animal animal = new Animal();
        Cat cat = new Cat();
        Dog dog = new Dog();
        Animal mutant = new Dog();
        Dog elder = (Dog) new Animal();
        try {
            animal.makeSomeNoise();
            cat.makeSomeNoise();
            dog.makeSomeNoise();
            mutant.makeSomeNoise();
            elder.makeSomeNoise();
        }
        catch (ClassCastException e){
            System.out.println( e );
        }
    }

}
