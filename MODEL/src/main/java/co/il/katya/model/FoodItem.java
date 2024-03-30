package co.il.katya.model;

import java.io.Serializable;

public class FoodItem extends BaseEntity implements Serializable {

    private String name;

    private long bestUseBefore;

    private boolean isConsumed;



    public FoodItem(String name, long bestUseBefore, boolean isConsumed){
        this.name = name;
        this.bestUseBefore = bestUseBefore;
        this.isConsumed = isConsumed;
    }




    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public long getBestUseBefore() {return bestUseBefore;}

    public void setBestUseBefore(long bestUseBefore) {this.bestUseBefore = bestUseBefore;}

    public boolean isConsumed() {return isConsumed;}

    public void setConsumed(boolean consumed) {isConsumed = consumed;}
}
