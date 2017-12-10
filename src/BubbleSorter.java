import java.util.ArrayList;

public class BubbleSorter{

    public static <T> void sort(T[] items) throws IllegalArgumentException {
        if(items[0] instanceof Number)
        {
            for(int i =1; i< items.length; i++){
                for(int j = 0; j < (items.length - i); j ++){
                    if((((Comparable) (items[j])).compareTo(items[j+1])) > 0){
                        T tmp = items[j];
                        items[j] = items[j + 1];
                        items[j + 1] = tmp;
                    }
                }
            }
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public static <T> void sort(ArrayList<T> items)throws IllegalArgumentException, NumberFormatException {
        if(items.get(0) instanceof String)
        {
            for(int i =1; i< items.size(); i++){
                for(int j = 0; j < (items.size() - i); j ++){
                        if ((Double.parseDouble((String) items.get(j))) > (Double.parseDouble((String) items.get(j + 1)))) {
                            T tmp = items.get(j);
                            items.set(j, items.get(j + 1));
                            items.set(j + 1, tmp);
                        }

                }
            }
        }
        else if(items.get(0) instanceof Number)
        {
            for(int i =1; i< items.size(); i++){
                for(int j = 0; j < (items.size() - i); j ++){
                    if((((Comparable) (items.get(j))).compareTo(items.get(j+1))) > 0){
                        T tmp = items.get(j);
                        items.set(j, items.get(j+1));
                        items.set(j+1, tmp);
                    }
                }
            }
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }
}





