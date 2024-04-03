package co.il.katya.model;

import java.io.Serializable;

public class FoodItem extends BaseEntity implements Serializable {

    private String name;

    private long bestUseBefore;

    private String category;

    //private boolean isConsumed;

    private String content;


    public FoodItem() {}
    public FoodItem(String name, long bestUseBefore, String category, String content){
        this.name = name;
        this.bestUseBefore = bestUseBefore;
        this.category = category;
        this.content = content;
    }




    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public long getBestUseBefore() {return bestUseBefore;}

    public void setBestUseBefore(long bestUseBefore) {this.bestUseBefore = bestUseBefore;}

//    public boolean isConsumed() {return isConsumed;}
//
//    public void setConsumed(boolean consumed) {isConsumed = consumed;}

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
