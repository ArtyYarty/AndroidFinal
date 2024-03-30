package co.il.katya.finalproject.ADAPTERS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import co.il.katya.finalproject.R;
import co.il.katya.model.DateUtil;
import co.il.katya.model.FoodItem;
import co.il.katya.model.FoodItems;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder>{
    private Context context;
    private int food_item_layout;
    private FoodItems foodItems;

    private OnItemClickListener listener;
    private OnItemLongClickListener longListener;

    public FoodAdapter(Context context, int food_item_layout, FoodItems foodItems, OnItemClickListener listener, OnItemLongClickListener longListener){
        this.context = context;
        this.food_item_layout = food_item_layout;
        this.foodItems = foodItems;
        this.listener = listener;
        this.longListener = longListener;
    }

    @NonNull
    @Override
    public FoodAdapter.FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodHolder(LayoutInflater.from(context).inflate(food_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.FoodHolder holder, int position) {
        FoodItem food = foodItems.get(position);

        if(food != null){
            holder.bind(food, listener, longListener);
        }
    }

    @Override
    public int getItemCount() {
        return (foodItems != null) ? foodItems.size() : 0;
    }

    public static class FoodHolder extends RecyclerView.ViewHolder {
        public TextView tvItemName;
        public TextView tvDate;
        public TextView tvCategory;

        public FoodHolder(View itemView){
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvCategory = itemView.findViewById(R.id.tvCategory);
        }

        public void bind(FoodItem foodItem, OnItemClickListener listener, OnItemLongClickListener longListener){
            tvItemName.setText(foodItem.getName());
            tvDate.setText(DateUtil.longDateToString(foodItem.getBestUseBefore()));
            //find a way to put category here

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(foodItem);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longListener.onItemLongClicked(foodItem);
                    return true;
                }
            });
        }
    }

    public void setFoodItems(FoodItems foodItems) {
        this.foodItems = foodItems;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        public void onItemClicked(FoodItem foodItem);
    }

    public interface OnItemLongClickListener {
        public boolean onItemLongClicked(FoodItem foodItem);
    }

}
