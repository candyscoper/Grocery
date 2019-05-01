package grocer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Yemudan on 2017-07-24.
 */
 class Logger {

     Logger(){

    }
    void logSell(String upc, String name, int amount, double price){
        try (FileWriter f = new FileWriter("log.txt", true);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter p = new PrintWriter(b);) {
            Date date = new Date();
            p.println(date + ": " + "sold " + amount + " copies of item " + upc + " " + name + " for $" + String.format( "%.2f", price ));
            p.close();
            b.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    void logStartSale(String upc, String name, double price){
        try (FileWriter f = new FileWriter("log.txt", true);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter p = new PrintWriter(b);) {
            Date date = new Date();
            p.println(date + ": " + "item " + upc + " " + name +" on sale for $" + String.format( "%.2f", price ));
            p.close();
            b.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    void logEndSale(String upc, String name){
        try (FileWriter f = new FileWriter("log.txt", true);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter p = new PrintWriter(b);) {
             Date date = new Date();
            p.println(date + ": " + "item " + upc + " " + name +" sale ends");
            p.close();
            b.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    void logImport(String upc, String name, int amount){
        try (FileWriter f = new FileWriter("log.txt", true);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter p = new PrintWriter(b);) {
            Date date = new Date();
            p.println(date + ": " + amount + " copies of item " + upc + " " + name +" imported");
            p.close();
            b.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    void logReshelf(String upc, String name, int amount){
        try (FileWriter f = new FileWriter("log.txt", true);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter p = new PrintWriter(b);) {
            Date date = new Date();
            p.println(date + ": " + amount + " copies of item " + upc + " " + name +" shelved");
            p.close();
            b.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    void logAisle(String name, int aisle){
        try (FileWriter f = new FileWriter("log.txt", true);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter p = new PrintWriter(b);) {
            Date date = new Date();
            p.println(date + ": " + "change aisle number of " + name + " section to " + aisle);
            p.close();
            b.close();
        } catch (IOException i) {
            i.printStackTrace();
        }


    }

}
