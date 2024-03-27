package co.il.katya.finalproject.ACTIVITIES;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sh = getSharedPreferences("Login", MODE_PRIVATE);
        String email = sh.getString("name", "");
        String password = sh.getString("password", "");

        etEmail.setText(email);
        etPassword.setText(password);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(cbRemember.isChecked()){
            SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("name", etEmail.getText().toString());
            myEdit.putString("password", etPassword.getText().toString());
            myEdit.apply();
        }
    }
}