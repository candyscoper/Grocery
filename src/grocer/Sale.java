package grocer;

public class Sale {


    public boolean onSale = false;
    protected double saleP;

    public Sale(){

    }

    protected void putOnSale(double p){
        this.onSale = true;
        this.saleP = p;
    }

    protected void takeOffSale(){
        this.onSale = false;
    }

    protected double getSaleP(){
        return saleP;
    }

    protected boolean isOnSale(){
        return onSale;
    }

}
