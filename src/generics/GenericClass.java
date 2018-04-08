package generics;

class GenericClass<T>
{
    private T value;

    public GenericClass(T value) {
        this.value = value;
    }

    public String toString() {
        return  value.toString();
    }

    public T getValue() {
        return value;
    }
}