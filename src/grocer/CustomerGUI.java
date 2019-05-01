package grocer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;

public class CustomerGUI implements ActionListener {

    private Grocer grocer;
    private JFrame frame;
    protected String name;


    public CustomerGUI(Grocer g) {
        this.grocer = g;
    }


    private Vector<String> makeInfoPanel(User user, String match){
        // height = 4, width = 1
        Vector<Item> items = user.itemLookUp(match);
        Vector<String> strs = new Vector<>();
        for(Item i : items){
            String str = new String();
            str += i.getUPC() + " ";
            str += i.getName() + " ";
            str += "$"+ i.getPrice();
            if(i.isOnSale()){
                str += " On Sale!";
            }
            strs.add(str);

        }
        return strs;

    }

    public JPanel getPanel(User user) {
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();


        // Finding items
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        JPanel p = grocer.findLocation(user);
        panel.add(p, c);

        // Name
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        JTextField name = new JTextField("Name");
        name.setColumns(8);
        panel.add(name, c);


        // Info panel pls
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        JPanel panels = new JPanel();
        Vector<String> strs = makeInfoPanel(user, "");
        DefaultListModel<String> dlm = new DefaultListModel<>();
        for (int i = 0; i< strs.size(); i++) {
            dlm.addElement(strs.get(i));
        }
        JScrollPane scrollPane = new JScrollPane();
        JList<String> list = new JList<>(dlm);
        list.setVisibleRowCount(5);
        scrollPane.setViewportView(list);
        panels.add(scrollPane);
        panel.add(panels, c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        JButton search = new JButton("INFO");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 2;
                c.gridwidth = 3;
                Vector<String> strs = makeInfoPanel(user, name.getText());
                DefaultListModel<String> dlm = new DefaultListModel<>();
                for (int i = 0; i< strs.size(); i++) {
                    dlm.addElement(strs.get(i));
                }
                list.setModel(dlm);
            }
        });
        panel.add(search, c);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Customer customer = new Customer();
        customer.setUp();
        frame = new JFrame("CUSTOMER");
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        JPanel pan = getPanel(customer);

        frame.add(pan);

        grocer.customer(frame);
    }

}
