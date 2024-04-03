package co.il.katya.model;

public class FruitsVegetables extends FoodItem{
    private int amount;

    public FruitsVegetables(String name, long bestUseBefore, String category, String content, int amount) {
        super(name, bestUseBefore, category, content);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
