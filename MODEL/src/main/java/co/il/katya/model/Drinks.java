package co.il.katya.model;

public class Drinks extends FoodItem{

    private int ml;
    private boolean isAlcoholic;
    private boolean isJuice;

    public Drinks(String name, long bestUseBefore, boolean isConsumed, int ml, boolean isAlcoholic, boolean isJuice) {
        super(name, bestUseBefore, isConsumed);
        this.ml = ml;
        this.isAlcoholic = isAlcoholic;
        this.isJuice = isJuice;
    }

    public int getMl() {
        return ml;
    }

    public void setMl(int ml) {
        this.ml = ml;
    }

    public boolean isAlcoholic() {
        return isAlcoholic;
    }

    public void setAlcoholic(boolean alcoholic) {
        isAlcoholic = alcoholic;
    }

    public boolean isJuice() {
        return isJuice;
    }

    public void setJuice(boolean juice) {
        isJuice = juice;
    }
}
