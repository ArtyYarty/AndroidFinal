package co.il.katya.model;

public class FruitsVegetables extends FoodItem{
    private int amount;

    public FruitsVegetables(String name, long bestUseBefore, boolean isConsumed, int amount) {
        super(name, bestUseBefore, isConsumed);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
