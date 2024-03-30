package co.il.katya.finalproject.ACTIVITIES;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import co.il.katya.finalproject.R;

public class MainMenuActivity extends AppCompatActivity {

    private Button btnMyFridge;
    private Button btnRecipes;
    private Button btnSurprise;
    private Button btnNewAllergy;
    private Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        initViews();
        setListeners();
    }

    private void initViews() {
        btnMyFridge = findViewById(R.id.btnMyFridge);
        btnRecipes = findViewById(R.id.btnRecipes);
        btnSurprise = findViewById(R.id.btnSurprise);
        btnNewAllergy = findViewById(R.id.btnNewAllergy);
        btnLogOut = findViewById(R.id.btnLogOut);
    }

    private void setListeners() {
        btnMyFridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, MyFridge.class);
                startActivity(intent);
            }
        });

        btnRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Shows list of recipes with available items from fridge
            }
        });

        btnSurprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Make an alert dialog that shows a random pop up of a surprise recipe (With picture)
            }
        });

        btnNewAllergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //New pop up alert that gives the option to add a new allergy.
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, SignInActivity.class);
                intent.putExtra("checkboxState", false);
                startActivity(intent);

            }
        });
    }
}