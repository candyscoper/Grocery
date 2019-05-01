package grocer;

import kotlin.reflect.jvm.internal.impl.serialization.jvm.JvmProtoBufUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;

public class Grocer {

    private JFrame iFrame;
    private static final Grocer instance = new Grocer();
    protected User user;


    private Grocer(){
    }

    public static Grocer getInstanceOf(){
        return instance;
    }

    public JFrame window(){
        JFrame f = new JFrame("YOUR GROCER");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel buttonPanel = new JPanel();

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(150, 100, 150, 100));
        JButton customer = new JButton("CUSTOMER");
        JButton cashier = new JButton("CASHIER");
        JButton receiver = new JButton("RECEIVER");
        JButton manager = new JButton("MANAGER");
        JButton reshelver = new JButton("RESHELVER");

        ActionListener customerListener = new CustomerGUI(instance);
        ActionListener cashierListener = new EmployeeGUI(instance, "CASHIER");
        ActionListener receiverListener = new EmployeeGUI(instance, "RECEIVER");
        ActionListener managerListener = new EmployeeGUI(instance, "MANAGER");
        ActionListener reshelverListener = new EmployeeGUI(instance, "RESHELVER");
        customer.addActionListener(customerListener);
        cashier.addActionListener(cashierListener);
        receiver.addActionListener(receiverListener);
        manager.addActionListener(managerListener);
        reshelver.addActionListener(reshelverListener);

        buttonPanel.add(customer);
        buttonPanel.add(cashier);
        buttonPanel.add(receiver);
        buttonPanel.add(manager);
        buttonPanel.add(reshelver);
        f.add(buttonPanel, BorderLayout.CENTER);

        f.pack();
        f.setSize(600, 400);
        f.setLocationRelativeTo(null);

        return f;
    }

    public void customer(JFrame frame){
        frame.setVisible(true);

    }

    public void employee(JFrame frame){
    frame.setVisible(true);

    }

    public JButton reminder() {
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        JButton re = new JButton("ADD REMINDER");

        return re;
    }

    public JPanel findLocation(User user){
        JPanel t = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        JTextField upc = new JTextField("UPC");
        upc.setColumns(8);
        t.add(upc, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        JLabel aisle = new JLabel("Aisle: ");
        t.add(aisle, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        JButton press = new JButton("FIND");
        press.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(user.findItemByUPC(upc.getText())!=null){
                    aisle.setText("Aisle: " + String.valueOf(user.findItemByUPC(upc.getText()).getAisle()));
                } else {
                    JOptionPane.showMessageDialog( null,"No such item!");

                }

            }
        });
        t.add(press, c);


        return t;
    }


    private JFrame getdWindow() {
        return iFrame;
    }

    private void setdWindow(JFrame frame) {
        this.iFrame = frame;
    }

    public static void main(String[] args) {
        User user = new Reciever();
        user.setUp();
        user.importOrders();
        user.saveData();
        Grocer g = Grocer.getInstanceOf();
        g.setdWindow(g.window());
        g.getdWindow().setVisible(true);

    }
}
