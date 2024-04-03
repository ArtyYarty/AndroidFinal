package co.il.katya.model;

public class SaucesSpices extends FoodItem{

    private int grams;
    private boolean isSpicy;
    public SaucesSpices(String name, long bestUseBefore, String category, String content, int grams, boolean isSpicy) {
        super(name, bestUseBefore, category, content);
        this.grams = grams;
        this.isSpicy = isSpicy;
    }

    public int getGrams() {
        return grams;
    }

    public void setGrams(int grams) {
        this.grams = grams;
    }

    public boolean isSpicy() {
        return isSpicy;
    }

    public void setSpicy(boolean spicy) {
        isSpicy = spicy;
    }
}
