package co.il.katya.finalproject.ACTIVITIES;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import co.il.katya.finalproject.ADAPTERS.FoodAdapter;
import co.il.katya.finalproject.R;
import co.il.katya.model.FoodItem;
import co.il.katya.model.FoodItems;
import co.il.katya.viewmodel.FoodViewModel;
import co.il.katya.viewmodel.GenericViewModelFactory;

public class MyFridge extends AppCompatActivity {
    private RecyclerView rvFoodItems;
    private FoodAdapter adapter;
    private FoodItems foodItems;
    private FoodItem oldItem;
    private FoodViewModel foodViewModel;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fridge);

        initViews();
        setRecyclerView();
        setObservers();
        setListeners();
    }

    private void setListeners() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FoodItemActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setObservers() {
        GenericViewModelFactory<FoodViewModel> factory = new GenericViewModelFactory<>(getApplication(), FoodViewModel::new);
        foodViewModel = new ViewModelProvider(this, factory).get(FoodViewModel.class);
        foodViewModel.getAll().observe(this, new Observer<FoodItems>() {

            @Override
            public void onChanged(FoodItems observedFoodItems) {
                foodItems = observedFoodItems;
                adapter.setFoodItems(foodItems);
            }
        });
    }

    private void setRecyclerView() {
        FoodAdapter.OnItemClickListener listener = new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(FoodItem foodItem) {
                    oldItem = foodItem;
                    Intent update = new Intent(MyFridge.this, FoodItemActivity.class);
                    update.putExtra("ITEM", foodItem);
                    startActivityForResult(update, 2);
            }

        };

        FoodAdapter.OnItemLongClickListener longListener = new FoodAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(FoodItem foodItem) {
                if(foodItem != null) {
                    deleteFoodItem(foodItem);
                    return true;
                }
                return false;
            }
        };

        adapter = new FoodAdapter(this, R.layout.food_item_layout, foodItems, listener, longListener);
        rvFoodItems.setAdapter(adapter);
        rvFoodItems.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initViews() {
        rvFoodItems = findViewById(R.id.rvFoodItems);
        fabAdd = findViewById(R.id.fabAdd);

        addItems();
    }

    private void deleteFoodItem(FoodItem foodItem) {
        foodViewModel.delete(foodItem);
        foodItems.remove(foodItem);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                FoodItem foodItem = data.getSerializableExtra("ITEM", FoodItem.class);

                if (requestCode == 1) {
                    foodItems.add(foodItem);
                }
                else {
                    int ind = foodItems.indexOf(oldItem);
                    foodItems.set(foodItems.indexOf(oldItem), foodItem);
                }

                adapter.notifyDataSetChanged();
                foodViewModel.save(foodItem);
            }
        }
    }
    private void addItems() {
        foodItems = new FoodItems();
    }
}