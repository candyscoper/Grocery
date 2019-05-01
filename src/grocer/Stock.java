package grocer;

import javax.swing.*;
import java.io.*;
import java.util.Date;
import java.util.Vector;

/**
 * Created by Yemudan on 2017-07-18.
 */
class Stock {
    private Vector<Item> itemList;
    private Vector<ItemOrder> pendingOrders;
    private Logger logger;
    private String stockName;


    Stock(){
        itemList = new Vector<Item>();
        pendingOrders = new Vector<ItemOrder>();
        logger = new Logger();
    }

    Stock(String name){
        itemList = new Vector<Item>();
        pendingOrders = new Vector<ItemOrder>();
        logger = new Logger();
        stockName = name;
    }

    String getStockName(){return stockName;}

    void addItem(Item item){

        int index = itemList.indexOf(item);

        if (index>=0 ){
            //if item is already in stock
            itemList.get(index).addStock(item.getAmount());
        }
        else{
            itemList.add(item);
        }


    }

    void addItemsToStock(Vector<Item> items){
        while (!items.isEmpty()){
            Item cur = items.lastElement();
            int index = itemList.indexOf(cur);

            if (index>=0 ){
                //if item is already in stock
                itemList.get(index).addStock(cur.getAmount());
            }
            else{
                itemList.add(cur);
            }
            logger.logImport(cur.getUPC(), cur.getName(), cur.getAmount());
            items.remove(cur);

        }
    }

    Vector<Item> getItemList(){
        return itemList;
    }

    void printStock(){
        System.out.println("In grocer.Stock: ");
        for (Item item: itemList){
            item.printItem();
        }
    }

    void addOrder(ItemOrder order){
        pendingOrders.add(order);
    }

    void reOrder(){
        Date date = new Date();
        for (Item cur: itemList){
            if (cur.getStockAmount() < cur.getThreshold() && !cur.ordered){
                cur.ordered = true;
                pendingOrders.add(new ItemOrder(cur, cur.getThreshold()+cur.getMax(), date));
            }

        }
    }

    void printOrder(){
        for (int i=0; i<pendingOrders.size(); i++){
            pendingOrders.get(i).printOrder();
        }
    }

    void writeOrder(){
            try{
                FileWriter f = new FileWriter("orders.txt", true);
                BufferedWriter b = new BufferedWriter(f);
                PrintWriter writer = new PrintWriter(b);
                for (int i=0; i<pendingOrders.size(); i++){
                    pendingOrders.get(i).writeOrder(writer);
                }

                writer.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog( null,"IO exception");
                // do something
            }
    }

    void writeStock(PrintWriter writer){

        for (int i=0; i<itemList.size(); i++) {
            itemList.get(i).writeItem(writer);
        }

    }

    Item findItemByUPC(String upc){
        for (Item item : itemList){
            if (item.getUPC().equals(upc)){
                return item;
            }
        }
        return null;
    }

}
