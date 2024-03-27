package co.il.katya.repository;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FirebaseInstance {
    private static volatile FirebaseInstance _instance = null;
    public static FirebaseApp app;
    private FirebaseInstance(Context context){
        FirebaseOptions options = new FirebaseOptions.Builder().setProjectId("androidproject-be096")
                .setApplicationId("1:295463540659:android:5a95572f4a0cd2e628ddc6").setApiKey("AIzaSyAIS1s1kUl0y9soDXHEvuutgyl4FY9VM94").setStorageBucket("androidproject-be096.appspot.com").build();
        app = FirebaseApp.initializeApp(context, options);
    }

    public static FirebaseInstance instance(Context context){
        if(_instance == null){
            synchronized (FirebaseInstance.class){
                if(_instance == null){
                    _instance = new FirebaseInstance(context);
                }
            }
        }
        return _instance;
    }
}
