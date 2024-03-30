package co.il.katya.finalproject.ADAPTERS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import co.il.katya.model.FoodItem;
import co.il.katya.model.FoodItems;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder>{
    private Context context;
    private int food_layout;
    private FoodItems foodTests;

    private AdapterView.OnItemClickListener listener;
    private AdapterView.OnItemLongClickListener longListener;

    public FoodAdapter(Context context, int food_layout, FoodItems foodItems, AdapterView.OnItemClickListener listener, AdapterView.OnItemLongClickListener longListener){
        this.context = context;
        this.food_layout = food_layout;
        this.foodTests = foodItems;
        this.listener = listener;
        this.longListener = longListener;
    }

    @NonNull
    @Override
    public FoodAdapter.FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodHolder(LayoutInflater.from(context).inflate(food_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.FoodHolder holder, int position) {
        FoodItem food = foodTests.get(position);

        if(food != null){
            //holder.bind(food, listener, longListener);
        }
    }

    @Override
    public int getItemCount() {
        return (foodTests != null) ? foodTests.size() : 0;
    }

    public static class FoodHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView type;
        public TextView amount;
        public TextView date;
        public TextView isSpicy;

        public FoodHolder(View itemView){
            super(itemView);

        }
    }

}
