package grocer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by Yemudan on 2017-07-22.
 */
 class ItemOrder {
    private int orderAmount;
    private double boughtPrice;
    private String name;
    private String upc;
    private Date orderDate;

    ItemOrder(Item item, int amount, Date date){
        item.ordered = true;
        orderAmount = amount;
        boughtPrice = item.getBoughtPrice();
        name = item.getName();
        upc = item.getUPC();
        orderDate = date;
    }

    void writeOrder(PrintWriter writer){
        writer.println("UPC: " + upc + ", name: " + name + ", cost: $" + String.format( "%.2f", boughtPrice )+ ", order amount: " + orderAmount + ", ordered date: " + orderDate);
    }

    void printOrder(){
        System.out.println(upc + ", " + name + ", cost: $" + String.format( "%.2f", boughtPrice )+ ", order amount: " + orderAmount + ", ordered date: " + orderDate);
    }
}
