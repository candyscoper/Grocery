package grocer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

public class Customer extends User {

    public Customer() {
        super();
        try (FileWriter f = new FileWriter("log.txt", true);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter p = new PrintWriter(b);) {
            String timeStamp = new SimpleDateFormat("yyyy_MM_dd").format(Calendar.getInstance().getTime());
            p.println(timeStamp + ": A customer has been initialized");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public String getIdentity(){
        return "CUSTOMER";
    }


}
