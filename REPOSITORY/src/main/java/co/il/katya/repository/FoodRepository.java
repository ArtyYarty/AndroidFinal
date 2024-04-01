package co.il.katya.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import co.il.katya.model.FoodItem;
import co.il.katya.model.FoodItems;

public class FoodRepository {
    private FirebaseFirestore db;
    private CollectionReference collection;

    private final MutableLiveData<FoodItems> foodItemsMutableLiveData;

    public FoodRepository(Context context) {

        try {
            db = FirebaseFirestore.getInstance();
        }
        catch (Exception e) {
            FirebaseInstance instance = FirebaseInstance.instance(context);
            db = FirebaseFirestore.getInstance(FirebaseInstance.app);
        }

        collection = db.collection("Blogs");
        foodItemsMutableLiveData = new MutableLiveData<>();
    }

    public Task<Boolean> add (FoodItem foodItem) {

        TaskCompletionSource<Boolean> taskCompletion = new TaskCompletionSource<Boolean>();
        DocumentReference document = collection.document();
        Log.d("ll", document.getId());
        foodItem.setIdFs(document.getId());
        document.set(foodItem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("ll", "saved");
                taskCompletion.setResult(true);
                Log.d("ll", "after saved");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ll", "fail");
                taskCompletion.setResult(false);
            }
        });
        Log.d("ll", "return save");
        return taskCompletion.getTask();
    }

    public MutableLiveData<FoodItems> getAll() {

        FoodItems blogPosts = new FoodItems();

        collection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshots) {
                if (querySnapshots != null && !querySnapshots.isEmpty()) {
                    for (DocumentSnapshot document : querySnapshots) {
                        FoodItem foodItem = document.toObject(FoodItem.class);
                        if (foodItem  != null) {
                            blogPosts.add(foodItem);
                        }
                    }
                }
                foodItemsMutableLiveData.postValue(blogPosts);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                foodItemsMutableLiveData.postValue(blogPosts);
            }
        });

        return foodItemsMutableLiveData;
    }

    public Task<Boolean> update (FoodItem foodItem) {
        TaskCompletionSource<Boolean> completionSource = new TaskCompletionSource<>();

        DocumentReference document = collection.document(foodItem.getIdFs());
        document.set(foodItem)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        completionSource.setResult(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completionSource.setResult(false);
                        completionSource.setException(e);
                    }
                });

        return completionSource.getTask();
    }

    public Task<Boolean> delete(FoodItem foodItem) {
        TaskCompletionSource<Boolean> completionSource = new TaskCompletionSource<>();

        DocumentReference document = collection.document(foodItem.getIdFs());
        document.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        completionSource.setResult(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completionSource.setResult(false);
                        completionSource.setException(e);
                    }
                });
        return completionSource.getTask();
    }
}
