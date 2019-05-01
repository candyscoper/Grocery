package grocer;

import javax.swing.*;
import java.io.PrintWriter;
import java.util.Vector;
/**
 * Created by Yemudan on 2017-07-18.
 */
class Section {
    private String sectionName;
    private Vector<Section> subsections;
    private Vector<Item> itemList;
    private int aisle;


    Section (){
        //System.out.println("new sec empty");
        sectionName = "";
        subsections = new Vector<Section>();
        itemList = new Vector<Item>();
        aisle = 0;
    }

    Section (String name) {
        //System.out.println("new sec");
        sectionName = name;
        subsections = new Vector<Section>();
        itemList = new Vector<Item>();
        aisle = 0;
    }

    String getSectionName(){
        return sectionName;
    }

    void printSection(){
        JOptionPane.showMessageDialog( null,sectionName + " section: ");
        for (Item itm : itemList){
            itm.printItem();
        }
        for (Section i: subsections){
            i.printSection();
        }
    }

    /*
    boolean addSubSection(Section sec){
        //System.out.println("here");
        if (subsections.size() == 0){
            subsections.add(sec);
            return true;
        }
        if (subsections.contains(sec)){
            return false;
        }
        subsections.add(sec);
        sec.setAisle(this.aisle);
        return true;
    }
    */

    void newSection(Vector<String> secNames){
        if(secNames.size()==0){
            return;
        }
        Section sec = new Section(secNames.get(0));
        for (Section i : subsections){
            if (i.equals(sec)){
                secNames.remove(0);
                i.newSection(secNames);
                return;
            }
        }

        subsections.add(sec);
        secNames.remove(0);
        sec.newSection(secNames);
    }

    boolean addItem(Item e){
        if (itemList.contains(e)){
            return false;
        }
        itemList.add(e);
        return true;
    }

    Item findItem(String name){
        for (Item i : itemList){
            if (i.getName().equals(name)){
                return i;
            }
        }
        /*
        for (Section j : subsections){
            Item item = j.findItem(name);
            if (item != null){
                return item;
            }
        }
        */
        return null;
    }

    Item findItemByUPC(String upc){

        for (Item i : itemList){
            if (i.getUPC().equals(upc)){
                return i;
            }
        }


        for (Section j : subsections){
            Item item = j.findItemByUPC(upc);
            if (item != null){
                return item;
            }
        }

        return null;
    }

    @Override
    public boolean equals(Object sec){
        if (sec == null){
            return false;
        }
        if (sec instanceof Section) {
            return this.getSectionName().equals(((Section)sec).getSectionName());
        }
        return false;
    }

    Section findSec(String subName){
        if (this.sectionName.equals(subName)){
            return this;
        }
        if (subsections.isEmpty()) {
            return null;
        }
        Section sec;
        for (Section i : subsections) {
            sec = i.findSec(subName);
            if (sec != null){
                return sec;
            }
        }
        //System.out.println(subName + " sub not found");
        return null;
    }

     void setAisle(int aisle){
        this.aisle = aisle;
        if (!subsections.isEmpty()) {
            for (Section i : subsections) {
                i.setAisle(aisle);
            }
        }
    }

     int getAisle(){
        return this.aisle;
    }

    void writeAisles(PrintWriter writer, String storeName){
         if (aisle!=0) {
             writer.println( storeName + "/" + sectionName + "/" + aisle);
         }
         else{
            for (Section sec : subsections){
                sec.writeAisles(writer, storeName);
            }
         }
    }


}
