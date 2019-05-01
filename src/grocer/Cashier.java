package grocer;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class Cashier extends User {

    //Cashier -- They will scan to take item out of inventory,
    // changes the quantity stored, and check for start and end
    // dates of any sale that applies to a scanned item.


    public Cashier() {
        super();
        try (FileWriter f = new FileWriter("log.txt", true);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter p = new PrintWriter(b);) {
            Date date = new Date();
            p.println(date + ": " + "A cashier has been initialized");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void sell(String upc, int amount) {
        sellItem(upc, amount);
    }

    @Override
    void startSale(String item, double price){
        JOptionPane.showMessageDialog( null,"Sorry, you must be the manager to start a sale");
    }

    @Override
    void endSale(String item){
        JOptionPane.showMessageDialog( null,"Sorry, you must be the manager to end a sale");
    }

    @Override
    void importOrders(){
        JOptionPane.showMessageDialog( null,"Sorry, you must be a reciever to import orders");
    }

    @Override
    void reStockItems(){
        JOptionPane.showMessageDialog( null,"Sorry, you must be a reshelver to reshelf items");
    }

    @Override
    void viewOrder(){
        JOptionPane.showMessageDialog( null,"Sorry, you must be the manager to view the orders");
    }



    public String getIdentity(){
        return "cashier";
    }

}







