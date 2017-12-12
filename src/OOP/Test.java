package OOP;

public class Test {
    public static void main(String[] args){
        Animal animal = new Animal();
        Cat cat = new Cat();
        Dog dog = new Dog();
        Animal mutant = new Dog();

        animal.makeSomeNoise();
        cat.makeSomeNoise();
        dog.makeSomeNoise();
        mutant.makeSomeNoise();
    }

}
