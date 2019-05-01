package grocer;

//import oracle.jvm.hotspot.jfr.JFR;

import kotlin.reflect.jvm.internal.impl.serialization.jvm.JvmProtoBufUtil;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Date;
import javax.swing.*;

public class EmployeeGUI implements ActionListener {

    private Grocer grocer;
    public String name = "EMPLOYEE";
    private JFrame frame;
    private String person;

    private JLabel newStock = new JLabel("Add New Stock");
    private JLabel info = new JLabel("Item Info");

    public EmployeeGUI(Grocer g, String person) {
        this.grocer = g;
        this.person = person;
    }

    public JPanel cashier() throws Exception{
        Cashier cashier = new Cashier();
        cashier.setUp();
        JPanel t = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        JTextField upc = new JTextField("UPC");
        upc.setColumns(8);
        t.add(upc, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        JTextField amt = new JTextField("Amount");
        t.add(amt, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        JButton enter = new JButton("SCAN");
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cashier.findItemByUPC(upc.getText())!=null) {
                    cashier.sell(upc.getText(), Integer.valueOf(amt.getText()));
                    cashier.writeStock();
                    cashier.writeRevenue();
                } else {
                    JOptionPane.showMessageDialog(null, "Error!");
                }
            }
        });
        t.add(enter, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        t.add(priceHisUPC(cashier), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        JButton but = grocer.reminder();
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddReminder.writeReminder();

            }
        });
        t.add(but, c);

        findTodaysReminder();
        cashier.saveData();

        return t;
    }


    public JPanel receiver() throws Exception{
        Reciever reciever = new Reciever();
        reciever.setUp();
        JPanel t = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        JPanel p = grocer.findLocation(reciever);
        t.add(p, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        JButton but = grocer.reminder();
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddReminder.writeReminder();

            }
        });
        t.add(but, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        JTextField upc = new JTextField("UPC");
        upc.setColumns(8);
        t.add(upc, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        JLabel price = new JLabel("Price: ");
        t.add(price, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        JButton enter = new JButton("PRICE");
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(reciever.findItemByUPC(upc.getText()) != null) {
                    price.setText("Price: " + reciever.itemPrice(upc.getText()));
                } else {
                    JOptionPane.showMessageDialog(null, "Error!");
                }
            }
        });
        t.add(enter, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        JPanel pp = priceHisUPC(reciever);
        t.add(pp, c);

        findTodaysReminder();

        return t;
    }

    public JPanel manager() throws Exception{
        Manager manager = new Manager();
        manager.setUp();
        JPanel t = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Making a Section
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        JTextField sec = new JTextField("Section Name");
        t.add(sec, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        JTextField aisle = new JTextField("Aisle");
        t.add(aisle, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        JButton enter = new JButton("MAKE");
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                manager.newSection(sec.getText(), Integer.valueOf(aisle.getText()));
                manager.writeAisles();
            }
        });
        t.add(enter, c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        t.add(priceHisUPC(manager), c);

        // Making an order
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        JTextField order = new JTextField("UPC");
        order.setColumns(8);
        t.add(order, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        JTextField q = new JTextField("Amount");
        t.add(q, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 3;
        DateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        JFormattedTextField date = new JFormattedTextField(format);
        String daate = new Date().toString();
        DateFormat i = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        Date d = null;
        try {
            d = i.parse(daate);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Error!");
            e.printStackTrace();
        }
        date.setValue(d);
        t.add(date, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 3;
        JButton click = new JButton("ORDER");
        click.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (manager.findItemByUPC(order.getText())!= null) {
                    manager.AddOrder(order.getText(), Integer.valueOf(q.getText()), date.getText());
                    manager.reOrder();
                } else {
                    JOptionPane.showMessageDialog(null, "Error!");
                }
            }
        });
        t.add(click, c);

        // Information about growth
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        JLabel rev = new JLabel("Revenue: "+String.valueOf(manager.viewRevenue()));
        t.add(rev, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        JLabel profit = new JLabel("Profit: "+String.valueOf(manager.todayProfit()));
        t.add(profit, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 4;
        JLabel cost = new JLabel("Cost: "+String.valueOf(manager.todayCost()));
        t.add(cost, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 4;
        JButton renew = new JButton("RENEW");
        renew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rev.setText("Revenue: " + String.valueOf(manager.viewRevenue()));
                profit.setText("Profit: " + String.valueOf(manager.todayProfit()));
                cost.setText("Cost: " + String.valueOf(manager.todayCost()));
            }
        });
        t.add(renew, c);

        // Changing the price
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        JTextField itemP = new JTextField("UPC");
        order.setColumns(8);
        t.add(itemP, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 5;
        JTextField price = new JTextField("Price");
        t.add(price, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 5;
        JButton change = new JButton("CHANGE");
        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (manager.findItemByUPC(itemP.getText())!= null) {
                    for (Store store : manager.myStores) {
                        Item item = store.findItemByUPC(itemP.getText());
                        if (item != null) {
                            item.updatePrice(Double.valueOf(price.getText()));
                            manager.writeStock();
                            JOptionPane.showMessageDialog(null, "Price changed to: " +price.getText() );

                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error!");
                }

            }
        });
        t.add(change, c);

        // Add product
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 5;
        t.add(addProd(manager), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        JTextField upc = new JTextField("UPC");
        upc.setColumns(8);
        t.add(upc, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 7;
        JTextField max = new JTextField("Max");
        t.add(max, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 7;
        JTextField threshold = new JTextField("Threshold");
        t.add(threshold, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 7;
        JButton set = new JButton("SET");
        set.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (manager.findItemByUPC(upc.getText()) != null) {
                    manager.setThresMax(upc.getText(), Integer.valueOf(max.getText()), Integer.valueOf(threshold.getText()));
                    manager.writeStock();
                } else {
                    JOptionPane.showMessageDialog(null, "Error!");
                }
            }
        });
        t.add(set, c);


        // Sale stuff
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        JTextField product = new JTextField("UPC");
        product.setColumns(8);
        t.add(product, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 8;
        JTextField p = new JTextField("Price");
        t.add(p, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 8;
        JButton sale = new JButton("SALE");
        sale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.putOnSale(product.getText(), Double.valueOf(p.getText()));
                manager.writeStock();
                manager.saveData();
            }
        });
        t.add(sale, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 8;
        JButton offSale = new JButton("OFF SALE");
        offSale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (manager.findItemByUPC(product.getText()).isOnSale()){
                    manager.takeOffSale(product.getText());
                    manager.writeStock();
                } else {
                    JOptionPane.showMessageDialog(null, "It's not on sale!");
                }
            }
        });
        t.add(offSale, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 10;
        c.gridwidth = 5;
        JButton but = grocer.reminder();
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AddReminder.writeReminder();

            }
        });
        t.add(but, c);

        findTodaysReminder();

        return t;

    }

    public JPanel reshelver() throws Exception{
        JPanel t = new JPanel(new GridBagLayout());
        Reshelver reshelver = new Reshelver();
        reshelver.setUp();
        // get stock,get shelf,fill shelf, order history find location
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        JTextField upc = new JTextField("UPC");
        t.add(upc, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        JLabel stockn = new JLabel("Stock Has: ");
        t.add(stockn, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        JButton stock = new JButton("Stock Amount");
        stock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (reshelver.findItemByUPC(upc.getText())!=null) {
                    stockn.setText("Stock Has: " + String.valueOf(reshelver.getStock(upc.getText())));
                } else {
                    JOptionPane.showMessageDialog(null, "Error!");
                }
            }
        });
        t.add(stock,c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        JLabel shelf = new JLabel("Shelf Has: ");
        t.add(shelf, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        JButton shelf_items = new JButton("Shelf Amount");
        shelf_items.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (reshelver.findItemByUPC(upc.getText()) != null) {
                    shelf.setText("Shelf Has: " + String.valueOf(reshelver.getShelf(upc.getText())));
                } else {
                    JOptionPane.showMessageDialog(null, "Error!");
                }
            }
        });
        t.add(shelf_items,c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        JButton fill_shelf = new JButton("Fill Shelf");
        fill_shelf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (reshelver.findItemByUPC(upc.getText()) != null ) {
                    reshelver.fillShelf(upc.getText());
                    reshelver.writeStock();
                } else {
                    JOptionPane.showMessageDialog(null, "Error!");
                }
            }
        });
        t.add(fill_shelf,c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        JLabel aisle = new JLabel("Aisle: ");
        t.add(aisle, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        JButton find_location = new JButton("Location");
        find_location.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Item item = reshelver.findItemByUPC(upc.getText());
                if (item != null) {
                    aisle.setText("Aisle: " + String.valueOf(item.getAisle()));
                }
                else {
                    JOptionPane.showMessageDialog(null, "Item not found!");
                }
            }
        });
        t.add(find_location,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 3;
        JButton but = grocer.reminder();
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddReminder.writeReminder();

            }
        });
        t.add(but, c);

        findTodaysReminder();

        return t;
    }


    private JPanel priceHisUPC(User user){
        JPanel t = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        JTextField upc = new JTextField("UPC");
        upc.setColumns(8);
        t.add(upc, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        JButton press = new JButton("PRICE HISTORY");
        press.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(user.getItemHistory(upc.getText()) != null){
                    ArrayList<String[]> pHis = user.getItemHistory(upc.getText());
                    JFrame f = new JFrame("PRICE HISTORY OF: " + upc.getText());
                    f.setLayout(new GridBagLayout());
                    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    f.setSize(400, 400);
                    f.setLocationRelativeTo(null);
                    for(String[] i : pHis){
                        String st = "";
                        for (String s : i){
                            st += s;
                        }
                        JLabel j = new JLabel(st);
                        f.add(j);
                    }
                    f.pack();
                    f.setVisible(true);
                }
            }
        });
        t.add(press, c);

        return t;
    }

    private JPanel addProd(User user){
        JPanel t = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        JTextField upc = new JTextField("UPC");
        upc.setColumns(8);
        t.add(upc, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        JTextField name = new JTextField("Name");
        name.setColumns(8);
        t.add(name, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        JTextField boughtPrice = new JTextField("Cost");
        boughtPrice.setColumns(5);
        t.add(boughtPrice, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 0;
        JTextField price = new JTextField("Price");
        price.setColumns(5);
        t.add(price, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 4;
        c.gridy = 0;
        JTextField sec = new JTextField("Section");
        sec.setColumns(5);
        t.add(sec, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 5;
        c.gridy = 0;
        JButton add = new JButton("ADD");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] secs = sec.getText().split("/");
                Vector<Section> sections = new Vector<>();
                for (int i=0; i<secs.length;i++){
                    sections.add(new Section(secs[i]));
                }
                Item item = new Item(upc.getText(), name.getText(), Double.valueOf(boughtPrice.getText()), Double.valueOf(price.getText()), 0, 0, 0, sections);
                for (Stock stock : user.myStocks){
                    if (stock.getStockName().equals(sections.get(0))){
                        stock.addItem(item);
                        user.writeStock();
                        return;
                    }
                }
                Stock newStock = new Stock(sections.get(0).getSectionName());
                newStock.addItem(item);
                user.myStocks.add(newStock);
                user.writeStock();
            }
        });
        t.add(add, c);

        return t;

    }


    public void findTodaysReminder() throws Exception {
        ZoneId zonedId = ZoneId.of("America/Montreal");
        LocalDate today = LocalDate.now(zonedId);

        try{
        FindReminders ob = new FindReminders(today.toString());
        if (ob.fileExist().equals("found")) {
            File file = ob.getFile();
            String date;
            date = today.toString();
            String reminder;
            reminder = "It is " + date + " you must " + ob.read(file);
            JOptionPane.showMessageDialog( null, reminder ); }
        }
        catch (Exception except){
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame = new JFrame(person);
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        JPanel p = new JPanel();
        if (person == "CASHIER"){
            try {
                p = cashier();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (person == "RECEIVER"){
            try {
                p = receiver();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (person == "MANAGER"){
            try {
                p = manager();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (person == "RESHELVER"){
            try {
                p = reshelver();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        frame.add(p);

        grocer.employee(frame);
    }

}