package grocer;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

class User {

    //String identity;
    public double revenue;
    public double cost;
    public double profit;
    public Logger logger;

    Vector<Store> myStores;
    Vector<Stock> myStocks;

    User() {
        myStores = new Vector<>();
        myStocks = new Vector<>();
        logger = new Logger();

    }

    void readStock(){
        String sCurrentLine;
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader("stock.txt"));
            sCurrentLine = br.readLine();
            while (sCurrentLine != null) {
                //read item info
                String[] curLine = sCurrentLine.split("/");
                Vector<Section> sections = new Vector<>();
                String storeName = curLine[10];
                for (int i = 10; i < curLine.length; i++) {
                    sections.add(new Section(curLine[i]));
                }

                Item it = new Item(curLine[0], curLine[1], (double) Double.valueOf(curLine[2]), (double) Double.valueOf(curLine[3]), (int) Integer.valueOf(curLine[4]), (int) Integer.valueOf(curLine[5]), (int) Integer.valueOf(curLine[6]), sections);
                it.transferStock(Integer.valueOf(curLine[8]), false);
                if (!curLine[9].equals(curLine[3])) {
                    it.startSale(Double.valueOf(curLine[9]));
                }
                it.setAisle(Integer.valueOf(curLine[7]));

                sCurrentLine = br.readLine();
                //read item history
                it.history = new History(it);

                if (sCurrentLine != "no history") {
                    curLine = sCurrentLine.split(",");

                    for (int i = 0; i < curLine.length; i++) {
                        if (curLine[i] != "no history") {
                            String[] curEntry = curLine[i].split("/");
                            it.history.getHistory().add(curEntry);
                        }
                    }
                }
                boolean added = false;

                for (Stock stock : myStocks) {
                    if (stock.getStockName().equals(storeName) && !added) {
                        stock.addItem(it);
                        added = true;
                    }
                }


                if (!added){
                    Stock newStock = new Stock(storeName);
                    newStock.addItem(it);
                    myStocks.add(newStock);
                }


                sCurrentLine = br.readLine();

            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    void importOrders(){

        String sCurrentLine;
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("arrivedOrders.txt"));
            sCurrentLine = br.readLine();
            while (sCurrentLine  != null) {
                String[] i = sCurrentLine.split("/");
                Vector<Section> secNames = new Vector<>();
                String storeName = i[7];
                for (int j=7; j<i.length; j++){
                    secNames.add(new Section(i[j]));
                }
                Item it = new Item(i[0], i[1], (double) Double.valueOf(i[2]), (double) Double.valueOf(i[3]), (int) Integer.valueOf(i[4]), (int) Integer.valueOf(i[5]), (int) Integer.valueOf(i[6]), secNames);
                it.history.newStock(it.getAmount());
                boolean added = false;
                for (Stock stock : myStocks) {
                    if (stock.getStockName().equals(storeName)&&!added){
                        stock.addItem(it);
                        added = true;
                    }
                }
                if (!added){
                    Stock newStock = new Stock(storeName);
                    newStock.addItem(it);
                    myStocks.add(newStock);
                    Store newStore = new Store(storeName);
                    myStores.add(newStore);
                }

                logger.logImport(it.getUPC(),it.getName(),it.getAmount());

                sCurrentLine = br.readLine();

            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void assignSections(){
        for (Stock stock : myStocks) {
            Store store = new Store (stock.getStockName());
            Vector<Item> itemlist = stock.getItemList();

            for (int i = 0; i < itemlist.size(); i++) {
                Item item = itemlist.get(i);
                Vector<Section> secNames = item.getSection();
                Vector<String> names = new Vector<>();
                for (int j=1; j<secNames.size(); j++){
                    names.add(secNames.get(j).getSectionName());
                }

                String lastSec = secNames.get(secNames.size() - 1).getSectionName();
                store.newSection(names);
                Section sec = store.findSection(lastSec);
                if (sec == null) {
                    JOptionPane.showMessageDialog( null,"something went wrong in assign sections");
                    return;
                }
                sec.addItem(item);

            }
            myStores.add(store);
        }
    }

    void readAisleInfo(){
        String sCurrentLine;
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("aisleInfo.txt"));
            sCurrentLine = br.readLine();
            while (sCurrentLine != null) {

                String[] curLine = sCurrentLine.split("/");
                Vector<String> sections = new Vector<>();
                for (int i=0; i<curLine.length-1;i++){
                    sections.add(curLine[i]);
                }
                setAisle(sections, Integer.valueOf(curLine[2]), false);
                sCurrentLine = br.readLine();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    void sellItem(String upc, int amount){
        for (Store store : myStores) {
            Item item = store.findItemByUPC(upc);
            if (item != null) {
                if (store.sellItem(upc, amount)) {
                    revenue += item.getPrice() * amount;
                    cost += item.getBoughtPrice() * amount;

                    //profit = revenue - cost;
                    return;

                }
            }
        }
        JOptionPane.showMessageDialog( null,"item not found");


    }

    Store findStore(String name){
        for (Store store : myStores){
            if (store.getStoreName().equals(name)){
                return store;
            }
        }
        return null;
    }

    void reStockItems(){//goes through all of them and fills them back to threshold
        for (Stock stock : myStocks) {
            Store store = findStore(stock.getStockName());
            Vector<Item> itemlist = stock.getItemList();
            for (int i = 0; i < itemlist.size(); i++) {
                store.shelfItem(itemlist.get(i));
            }
        }
    }

    void setAisle(Vector<String> names, int num, boolean log){

        String storeName = names.get(0);
        Store store = findStore(storeName);
        if (store == null){
            store = new Store(storeName);
        }
        String sectionName = names.get(names.size()-1);
        names.remove(0);
        store.newSection(names);

        Section sec = store.findSection(sectionName);

        sec.setAisle(num);

        if (log) {
            logger.logAisle(sectionName, num);
        }


    }

    void startSale(String item, double price){
        for (Store store : myStores) {
            Item curItem = store.findItemByUPC(item);
            if (curItem != null) {
                curItem.startSale(price);
                JOptionPane.showMessageDialog( null,"Item sale started");
                return;
            }
        }
        JOptionPane.showMessageDialog( null,"Item does not exist");

    }

    void endSale(String item){
        for (Store store : myStores) {
            Item curItem = store.findItemByUPC(item);
            if (curItem != null) {
                curItem.endSale();
                JOptionPane.showMessageDialog( null,"item sale ended");
                return;
            }
        }
        JOptionPane.showMessageDialog( null,"item does not exist");
    }

    void reOrder(){
        for (Stock stock : myStocks) {
            stock.reOrder();
            stock.writeOrder();
        }
    }

    void viewOrder(){
        JOptionPane.showMessageDialog( null,"see orders.txt");
    }

    public String getIdentity(){
        return "none";
    }

    //public void setIdentity(String id) { identity = id;}

    void getProfit(){
        profit = revenue-cost;
        JOptionPane.showMessageDialog( null,"Revenue: $" + String.format( "%.2f",revenue)
                + "\nCost: $" + String.format( "%.2f", cost )
                + "\nProfit: $" + String.format( "%.2f", profit ));
    }

    void readRevenue(){
        String sCurrentLine;
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader("revenue.txt"));
            sCurrentLine = br.readLine();
            while (sCurrentLine != null) {
                String[] curLine = sCurrentLine.split(" ");
                if (curLine[0].equals("revenue")){
                    revenue = Double.valueOf(curLine[1]);
                }
                else if (curLine[0].equals("cost")){
                    cost = Double.valueOf(curLine[1]);
                }
                else if (curLine[0].equals("profit")){
                    profit = Double.valueOf(curLine[1]);
                }
                sCurrentLine = br.readLine();

            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void writeRevenue(){

        profit = revenue - cost;
        try{
            PrintWriter writer = new PrintWriter(new FileOutputStream("revenue.txt", false));
            writer.println("revenue " + String.format( "%.2f",revenue));
            writer.println("cost " + String.format( "%.2f", cost ));
            writer.println("profit " + String.format( "%.2f", profit ));

            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog( null,"IO exception");
            // do something
        }
    }

    void writeStock(){
        try{
            PrintWriter writer = new PrintWriter(new FileOutputStream("stock.txt", false));

            for (Stock stock : myStocks){
                stock.writeStock(writer);
            }
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog( null,"IO exception");
            // do something
        }

    }

    void writeAisles(){
        try{
            PrintWriter writer = new PrintWriter(new FileOutputStream("aisleInfo.txt", false));

            for (Store store : myStores){
                store.writeAisles(writer);
            }

            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog( null,"IO exception");
            // do something
        }
    }

    ArrayList<String[]> getItemHistory(String upc){
        Item item = null;
        for (Store store : myStores){
            item = store.findItemByUPC(upc);
            if (item != null){
                return item.history.getHistory();
            }
        }
        JOptionPane.showMessageDialog( null,"Item not found");
        return null;

    }

    public Vector<Item> itemLookUp(String name){
        Vector<Item> endList = new Vector<>();
        for (Stock stock : myStocks) {
            Vector<Item> allItems = stock.getItemList();
            for(Item i : allItems){
                if(i.getName().toLowerCase().contains(name.toLowerCase()) || name == ""){
                    endList.add(i);
                }
            }
        }

        return endList;
    }

    public void setUp(){
        readStock();
        assignSections();
        readAisleInfo();
        readRevenue();
    }

    public void saveData(){
        reOrder();
        writeStock();
        writeAisles();
        writeRevenue();
    }

    public Item findItemByUPC(String upc){
        for(Store store : myStores){
            Item item = store.findItemByUPC(upc);
            if(item != null){
                return item;
            }
        } return null;
    }



}