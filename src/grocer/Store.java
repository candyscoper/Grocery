package grocer;

import javax.swing.*;
import java.io.*;
import java.util.Vector;

/**
 * Created by Yemudan on 2017-07-18.
 */
public class Store {
    private Vector<Section> sections;
    private String storeName;

    Store(){
        sections = new Vector<Section>();
    }

    Store(String name){
        sections = new Vector<Section>();
        storeName = name;
    }

    String getStoreName(){return storeName;}


    void printStore(){
        //System.out.println("In grocer.Store: ");
        for (Section i : sections){
           i.printSection();
        }
    }

    void shelfItem(Item item){

        Item tempItem = findItem(item);
        if (tempItem != null){ //if the item is in the store
            //System.out.println("here " + item.getUPC());
            tempItem.restock();
            return;
        }

        //item is not in the store at this point

        Vector<String> sectionNames = item.getSectionNames();
        //String sectionName = sections.get(sections.size()-1);
        newSection(sectionNames);
        Section sec = findSection(sectionNames.get(sectionNames.size()-1));
        if (sec == null){
            JOptionPane.showMessageDialog( null,"something went wrong in creating new section in shelf item");
            return;
        }
        sec.addItem(item);
        item.restock();

    }


    void newSection(Vector<String> secNames){

        if (secNames.size()==0){
            //System.out.println("section already exists");
            return;
        }

        Section sec = new Section(secNames.get(0));

        for (Section i : sections){
            if (i.equals(sec)){
                secNames.remove(0);
                i.newSection(secNames);
                return;
            }
        }

        sections.add(sec);
        secNames.remove(0);
        sec.newSection(secNames);

    }

    Section findSection(String name){
        for (Section i : sections){
            Section sec = i.findSec(name);
            if (sec != null){
                return sec;
            }
        }
        //System.out.println(name + " sec not found");
        return null;
    }

    Item findItemByUPC(String upc){
        for(Section i : sections){
            Item item = i.findItemByUPC(upc);
            if (item != null){
                return item;
            }
        }
        return null;
    }

    public boolean sellItem(String upc, int amount){
        Item item = findItemByUPC(upc);
        if (item == null){
            return false;
        }
        if (item.sellAmount(amount)){
            item.printItem();
            return true;
        }
        item.printItem();
        JOptionPane.showMessageDialog( null,"Not enough to sell");
        return false;

    }

    Item findItem(Item item){
        Vector<String> secNames = item.getSectionNames();
        Section sec = findSection(secNames.get(secNames.size()-1));
        if (sec == null){
            return null;
        }

        return sec.findItemByUPC(item.getUPC());

    }

    void writeAisles(PrintWriter writer){
        for (Section sec : sections){
            sec.writeAisles(writer, storeName);
        }
    }





}
