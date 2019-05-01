package grocer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

class Reshelver extends User {//Reshelver -- They may need to access the location

    // of a product, order history, and the current quantity in store.


    public Reshelver() {
        super();
        try (FileWriter f = new FileWriter("log.txt", true);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter p = new PrintWriter(b);) {
            Date date = new Date();
            p.println(date + ": " + "A Reshelver has been initialized");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    /*
    public String findLocation(Item item) {
        return item.getSection() + " in subsection" + item.getSubSection();
    }
    */

    @Override
    ArrayList<String[]> getItemHistory(String upc){
        return null;
    }

    public int getStock(String upc) {
        Item item = findItemByUPC(upc);
        if (item!= null){
            return item.getStockAmount();
        }
        return 0;
    }

    public int getShelf(String upc) {
        Item item = findItemByUPC(upc);
        if (item!= null){
            return item.getStoreAmount();
        }
        return 0;
    }

    public void fillShelf(String upc) {
        Item item = findItemByUPC(upc);
        if (item!= null){
            item.restock();
        }
        //return 0;
    }


    @Override
    void sellItem(String upc, int amount){
        System.out.println("Sorry, you must be a cashier to sell");
    }

    @Override
    void startSale(String item, double price){
        System.out.println("Sorry, you must be the manager to start a sale");
    }

    @Override
    void importOrders(){
        System.out.println("Sorry, you must be a reciever to import orders");
    }

    @Override
    void endSale(String item){
        System.out.println("Sorry, you must be the manager to end a sale");
    }

    @Override
    void viewOrder(){
        System.out.println("Sorry, you must be the manager to view the orders");
    }




    public String getIdentity(){
        return "reshelver";
    }
}
