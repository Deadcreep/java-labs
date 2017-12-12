import javax.swing.*;
import java.util.EventObject;

interface CustomListener{
    void raiseEvent(CustomEvent e);
}

class CustomEvent extends EventObject {

    public CustomEvent(Object source) {
        super( source );
        System.out.println( "custom event raise from : " + Thread.currentThread().getName() );
    }
}