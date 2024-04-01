package co.il.katya.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import co.il.katya.model.FoodItem;
import co.il.katya.model.FoodItems;
import co.il.katya.repository.FoodRepository;

public class FoodViewModel extends ViewModel {
    private MutableLiveData<Boolean> successOperation;
    private Context context;
    private FoodRepository repository;

    private MutableLiveData<FoodItems> foodItemsMutableLiveData;

    public FoodViewModel(Context context) {
        successOperation = new MutableLiveData<>();
        this.context = context;
        repository = new FoodRepository(context);
        foodItemsMutableLiveData = new MutableLiveData<>();
    }

    public LiveData<Boolean> getSuccessOperation() {
        return successOperation;
    };

    public void add(FoodItem foodItem){
        Log.d("ll", "going to repository");
        repository.add(foodItem)
                .addOnSuccessListener(aBoolean ->
                {successOperation.setValue(true);})
                .addOnFailureListener(e ->
                {successOperation.setValue(false);});
        Log.d("ll", "after repository");
    }

    public LiveData<FoodItems> getAll() {
        foodItemsMutableLiveData = repository.getAll();
        return foodItemsMutableLiveData;
    }

    public void save(FoodItem foodItem) {
        if (foodItem.getIdFs() != null) {
            update(foodItem);
        }
        else {
            add(foodItem);
        }
    }

    private void update(FoodItem blogPost) {
        repository.update(blogPost).addOnSuccessListener(new OnSuccessListener<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        if (aBoolean) {
                            successOperation.setValue(true);
                        } else {
                            successOperation.setValue(false);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        successOperation.setValue(false);
                    }
                });

    }

    public void delete(FoodItem foodItem) {
        repository.delete(foodItem).addOnSuccessListener(new OnSuccessListener<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        if (aBoolean) {
                            successOperation.setValue(true);
                        }
                        else {
                            successOperation.setValue(false);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        successOperation.setValue(false);
                    }
                });
    }
}
