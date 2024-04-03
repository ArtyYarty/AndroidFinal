package co.il.katya.model;

public class Meat extends FoodItem{

    private int amount;
    private String type;

    public Meat(String name, long bestUseBefore, String category, String content, int amount, String type) {
        super(name, bestUseBefore, category, content);
        this.amount = amount;
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
