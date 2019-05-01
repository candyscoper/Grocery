package grocer;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Yemudan on 2017-07-18.
 */
public class Item {

    private String name;
    private int amount;
    private int storeAmount;
    private int stockAmount;
    private String upc;
    private Vector<Section> sections;
    private int threshold;
    private int maximum;

    private double price;
    double boughtPrice;
    History history;

    boolean ordered;
    protected double saleP;
    protected boolean onSale = false;

    private Logger logger;


    Item(String code, String itemName, double boughtP, double cost, int amt, int max, int thresh, Vector<Section> sec){
        upc = code;
        name = itemName;
        amount = amt;
        stockAmount = amt;
        storeAmount = 0;
        sections = sec;
        threshold = thresh;
        maximum = max;
        price = cost;
        boughtPrice = boughtP;
        this.history = new History(this);
        ordered = false;
        logger = new Logger();
    }


    void printItem(){
        System.out.print(getUPC() + " " + getName() + " , bought price: $" + String.format( "%.2f", boughtPrice )+ " , price: $" + String.format( "%.2f", getPrice() ) + ", aisle: " + getAisle() + ", in store: " + getStoreAmount() + ", in stock: " + getStockAmount()+ ", max: " + getMax()+ ", threshold: " + getThreshold());
        System.out.print(", sections:");
        for (Section i : sections){
            System.out.print(" " + i.getSectionName());
        }
        System.out.println();
    }

    void writeItem(PrintWriter writer){
        //need to store sale price later
        if (saleP != 0.0){
            writer.print(getUPC() + "/" + getName() + "/" + boughtPrice+ "/" + price + "/"  + getAmount() + "/" + getMax()+ "/" + getThreshold()+ "/" + getAisle() + "/" + getStoreAmount() + "/" + saleP);

        }else{
            writer.print(getUPC() + "/" + getName() + "/" + boughtPrice+ "/" + price + "/"  + getAmount() + "/" + getMax()+ "/" + getThreshold()+ "/" + getAisle() + "/" + getStoreAmount() + "/" + price);

        }
        for (Section i : sections){
            writer.print("/" + i.getSectionName());
        }
        writer.println();
        this.history.writeHistory(writer);


    }


    protected boolean isOnSale(){
        return onSale;
    }

    String getName(){
        return name;
    }

    String getUPC(){
        return upc;
    }

    double getPrice() {
        if (onSale){
            return saleP;
        }
        return price;
    }

    int getAmount(){
        return amount;
    }
    int getStockAmount(){
        return stockAmount;
    }
    int getStoreAmount(){
        return storeAmount;
    }

    int getThreshold(){
        return threshold;
    }

    Vector<Section> getSection(){
        return sections;
    }

    Vector<String> getSectionNames(){
        Vector<String> strs = new Vector<>();
        for (Section sec : sections){
            strs.add(sec.getSectionName());
        }
        return strs;
    }

    int getMax(){
        return maximum;
    }

    int getAisle(){
        return sections.get(0).getAisle();
    }

    void addStock(int addition){
        stockAmount += addition;
        amount += addition;
    }

    void restock(){

        int added = getMax() - getStoreAmount();
        if (added >0) {
            if (getStockAmount() >= added) {
                transferStock(added, true);
                return;
            }
            transferStock(getStockAmount(), true);
        }


    }

    boolean transferStock(int amt, boolean log){
        if (amt > stockAmount){
            return false;
        }
        if (amt > maximum-storeAmount){
            amt = maximum-storeAmount;
        }
        stockAmount -= amt;
        storeAmount += amt;
        history.transfer(amt);
        if (amt>0 && log) {
            logger.logReshelf(upc, name, amt);
        }

        return true;
    }

    boolean sellAmount(int amt){
        if (amt > storeAmount){
            //try to restock it
            if (amt > maximum ){
                return false;
            }
            if (amt > storeAmount + stockAmount){
                return false;
            }
            this.transferStock(stockAmount, true);
        }
        storeAmount -= amt;
        history.sales(amt);
        logger.logSell(upc, name, amt, getPrice());
        return true;
    }


    protected void startSale(double p){
        onSale = true;
        saleP = p;
        this.history.onSale(price);
        logger.logStartSale(upc, name, p);
    }


    protected void endSale() {
        onSale = false;
        saleP = 0.0;
        this.price = history.getLastPrice();
        this.history.offSale();
        logger.logEndSale(upc, name);
    }


    @Override
    public boolean equals(Object item){
        if (item == null){
            return false;
        }
        if (item instanceof Item) {
            return this.getUPC().equals(((Item)item).getUPC());
        }
        return false;
    }

    public ArrayList<Double> getInitialPrice(){
        return history.getPrice(history.iDate);
    }

     void updatePrice(double price){
        this.price = price;
        this.history.newPrice(price);
    }

    public void setAisle(int i){
         for(Section sec : sections){
             sec.setAisle(i);
         }
    }


    public String toString(){
        return name;
    }

    public String cusGetInformation(){
        return ("Name: "+ name + "; Aisle: " + getAisle() + "; Price: " + price + "; Quantity: " + getStockAmount());
    }

    public String empGetInformation(){
        return ("Name: "+ name + "; Aisle: " + getAisle() + "; Price: " + price + "; grocer.Stock: " + stockAmount + "; Floor Quantity:" + storeAmount + "; UPC: "+ upc);
    }

    public void setBoughtPrice(double price){
        this.boughtPrice = price;
    }
    double getBoughtPrice(){
        return this.boughtPrice;
    }

    public void setThreshold(int thresh){
        this.threshold = thresh;
    }
    public void setMaximum(int max){
        this.maximum = max;
    }

}
