package grocer;

import javax.swing.*;
import java.io.*;
import java.util.Date;
import java.util.Vector;

class Manager extends User{//They may want to view a list of pending orders,
    // view daily total sales (revenue),
    // and view daily total profit.
    //modify prices

    Manager() {
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
    public double viewRevenue(){
        return super.revenue;
    }//
    public double todayProfit(){
        return super.profit;
    }
    public double todayCost(){
        return super.cost;
    }

    public void changePrice(String upc, double newPrice){
        findItemByUPC(upc).updatePrice(newPrice);//carries
    }

    public void AddOrder(String upc, int quantity, String dateStr) {//This will be added to the pending order list
        Item item;
        for (Store store : super.myStores){
            item = store.findItemByUPC(upc);
            if (item != null){
                ItemOrder order = new ItemOrder(item, quantity, new Date());
                String storeName = item.getSectionNames().get(0);
                for (Stock stock : super.myStocks){
                    if (stock.getStockName().equals(storeName)){
                        stock.addOrder(order);
                        JOptionPane.showMessageDialog( null,"added order");
                        return;
                    }
                }
            }
        }

        JOptionPane.showMessageDialog( null,"Item not found");
        return;
    }

    public grocer.History itemPriceHistory(Item item){
        return item.history;
    }

    public void putOnSale(String upc, double price) {
        Item item = findItemByUPC(upc);
        item.startSale(price);

        JOptionPane.showMessageDialog( null,"Item sale started");
    }

    public void takeOffSale(String upc){
        Item item = findItemByUPC(upc);
        item.endSale();
        JOptionPane.showMessageDialog( null,"Item sale ended");

    }

    public void newSection(String section, int aisle){
        String[] input = section.split("/");
        Vector<String> sections = new Vector<>();
        for (int i=1; i<input.length; i++){
            sections.add(input[i]);
        }
        for (Store store : super.myStores){
            if (store.getStoreName().equals(input[0])){
                String secName = sections.get(sections.size()-1);
                store.newSection(sections);
                store.findSection(secName).setAisle(aisle);
                return;
            }
        }
        JOptionPane.showMessageDialog( null,"Store not found");

    }

    public void newStore(String name){
        for (Store store : super.myStores){
            if (store.getStoreName().equals(name)){
                JOptionPane.showMessageDialog( null,"Store already exists");
                return;
            }
        }
        Store newStore = new Store(name);
        super.myStores.add(newStore);
    }

    protected void setThresMax(String upc, int thres, int max){
        for (Stock stock : myStocks){
            Item item = stock.findItemByUPC(upc);
            if (item!= null){
                item.setThreshold(thres);
                item.setMaximum(max);
                return;
            }
        }
    }


    @Override
    void sellItem(String upc, int amount){
        JOptionPane.showMessageDialog( null,"Sorry, you must be a cashier to sell");
    }

    @Override
    void reStockItems(){
        JOptionPane.showMessageDialog( null,"Sorry, you must be a reshelver to reshelf items");
    }

    @Override
    void importOrders(){
        JOptionPane.showMessageDialog( null,"Sorry, you must be a reciever to import orders");
    }

    public String getIdentity(){
        return "manager";
    }


}
