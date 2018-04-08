package network.server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Client {

    public static String Id;

    public Client()
    {
        initializeId();
    }

    public void initializeId()
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String s = (new Date()).toString();
            md.update(s.getBytes(), 0, s.length());
            Id = md.digest().toString();
        }
        catch(NoSuchAlgorithmException ex) {
            Id = ""+(Math.random()*10000d);
        }

    }
}
