package co.il.katya.finalproject.ACTIVITIES;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import co.il.katya.finalproject.R;

public class SignInActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private TextView tvAppName;
    private TextView tvSignUpPage;

    private EditText etEmail;
    private EditText etPassword;

    private CheckBox cbRemember;

    private Button btnSignIn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);



        initViews();
        setListeners();
    }
    private void initViews() {
        tvWelcome = findViewById(R.id.tvWelcome);
        tvAppName = findViewById(R.id.tvAppName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        cbRemember = findViewById(R.id.cbRemember);
        btnSignIn2 = findViewById(R.id.btnRegister);
        tvSignUpPage = findViewById(R.id.tvSignUpPage);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkBox = preferences.getString("remember", "");
        if(checkBox.equals("true")) {
            Intent intent = new Intent(SignInActivity.this, MainMenuActivity.class);
            startActivity(intent);
        }
        else if(checkBox.equals("false")){
            Toast.makeText(SignInActivity.this, "Please Sign In", Toast.LENGTH_SHORT).show();
        }
    }

    private void setListeners() {
        btnSignIn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etEmail.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())) {
                    Toast.makeText(SignInActivity.this, "One or more fields missing", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(SignInActivity.this, MainMenuActivity.class);
                    startActivity(intent);
                }
            }
        });

        tvSignUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    Toast.makeText(SignInActivity.this, "Checked", Toast.LENGTH_SHORT).show();
                    //If checkbox is true will run this activity
                }

                else if(!compoundButton.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(SignInActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }





}