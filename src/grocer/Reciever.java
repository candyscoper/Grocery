package grocer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class Reciever extends User{//Receiver -- They will scan in new items
// into inventory and also
// new units of a pre-existing item;
// view location, cost, price history,
// and current price of a product.


  public Reciever() {
    super();
    try (FileWriter f = new FileWriter("log.txt", true);
         BufferedWriter b = new BufferedWriter(f);
         PrintWriter p = new PrintWriter(b);) {
      Date date = new Date();
      p.println(date + ": " + "A reciever has been initialized");
    } catch (IOException i) {
      i.printStackTrace();
    }

  }

  public double itemPrice(String upc){
    return findItemByUPC(upc).getPrice();

  }
  public History itemHistory(String upc){
    return findItemByUPC(upc).history;
  }
  public double itemCost(Item item){
    return item.getPrice();

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
  void endSale(String item){
    System.out.println("Sorry, you must be the manager to end a sale");
  }
  @Override
  void reStockItems(){
    System.out.println("Sorry, you must be a reshelver to reshelf items");
  }

  @Override
  void viewOrder(){
    System.out.println("Sorry, you must be the manager to view the orders");
  }

@Override
ArrayList<String[]> getItemHistory(String upc){
    return null;
}



    public String getIdentity(){
        return "reciever";
    }

}





