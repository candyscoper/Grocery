package grocer;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class History implements Serializable{

  private ArrayList<String[]> history;
  private Item item;
  Date iDate;

   History(Item item){
    this.item = item;
    this.iDate = new Date();
    this.history = new ArrayList<String[]>();

  }

   void newStock(int amount){
    String[] entry = new String[3];
    Date date = new Date();
    entry[0] = "Stock";
    entry[1] = date.toString();
    entry[2] = Integer.toString(amount);
    this.history.add(entry);

  }

  public ArrayList<String[]> getStockHis() {
    ArrayList<String[]> his = new ArrayList<String[]>();
    for (String[] i : history) {
      if (i[0] == "Stock"){
        his.add(i);
      }
    }
    return his;
  }

   void sales(int q){
    String[] entry = new String[3];
    Date date = new Date();
    entry[0] = "Sell";
    entry[1] = date.toString();
    entry[2] = Integer.toString(q);
    this.history.add(entry);
  }

  public void transfer(int amount){
    String[] entry = new String[3];
    Date date = new Date();
    entry[0] = "Transfer";
    entry[1] = date.toString();
    entry[2] = Integer.toString(amount);
    this.history.add(entry);

  }

   void newPrice(double price){
    String[] entry = new String[3];
    Date date = new Date();
    entry[0] = "Price";
    entry[1] = date.toString();
    entry[2] = Double.toString(price);
    this.history.add(entry);

  }

   void onSale(double price){
    String[] entry = new String[3];
    Date date = new Date();
    entry[0] = "On Sale";
    entry[1] = date.toString();
    entry[2] = Double.toString(price);
    this.history.add(entry);
  }

   void offSale(){
    String[] entry = new String[2];
    Date date = new Date();
    entry[0] = "Off Sale";
    entry[1] = date.toString();
    this.history.add(entry);
  }

  public String getProduct(){
    return item.getName();
  }

  public ArrayList<String> getDatesFromPrice(double price) {
    ArrayList<String> dates = new ArrayList<String>();
    String p = Double.toString(price);
    for (String[] i : history) {
      if (i[0].equals("Price")){
        if (i[2].equals(p)){
          dates.add(i[1]);
        }
      }
    }
    return dates;
  }

  ArrayList<Double> getPrice(Date date){
    ArrayList<Double> prices = new ArrayList<Double>();
    for (String[] i : history) {
      if(i[0].equals("Price")){
        if (i[1].equals(date.toString())){
          prices.add(Double.valueOf(i[2]));
        }
      }
    } return prices;
  }

  private Double getTotalSale(Date date){
    Double prices = 0.0;
    for (String[] i : history) {
      if(i[0].equals("Sell")){
        if(i[1].equals(date.toString())){
          prices += Integer.valueOf(i[2]);

        }
      }
    } return prices;
  }

  public Double getAllTotalSale(){
    Double prices = 0.0;
    for (String[] i : history) {
      if(i[0].equals("Sell")){
        prices += Integer.valueOf(i[2]);
      }
    } return prices;
  }

  private int getDailySaleNum(Date date){
    int num = 0;
    for (String[] i : history){
      if(i[1].equals(date.toString())){
        if (i[0].equals("Sell")){
          num += 1;
        }

      }

    } return num;

  }

  public double getLastPrice(){
    for (String[] i : history){
      if(i[0].equals("Price")){
        return (double) Double.valueOf(i[2]);
      }
    } return 0.0;
  }


  public Double getDailyProfit(Date date){
    Double sale = getTotalSale(date);
    Double profit = 0.0;
    profit = item.boughtPrice * getDailySaleNum(date);
    profit -= sale;
    return profit;

  }

  ArrayList<String[]> getHistory(){
    return history;
  }

  void writeHistory(PrintWriter writer){
    if (history.size() == 0){
      writer.println("no history");
      return;
    }
    for (String[] i : history){
      for (String j: i){
        writer.print(j);
        if (!j.equals(i[i.length-1])){
          writer.print("/");
        }

      }
      if (i!=history.get(history.size()-1)) {
        //if i isnt the last element
        writer.print(",");
      }
    }
    writer.println();
  }

  @Override
  public String toString(){

    StringBuilder str = new StringBuilder();
    for (String[] i : history){
      for (String j: i){
        str.append(j);
        str.append(" ");
      }
      str.append("\n");
    }

    return str.toString();

  }

}
