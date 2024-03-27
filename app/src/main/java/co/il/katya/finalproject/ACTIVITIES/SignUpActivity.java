package co.il.katya.finalproject.ACTIVITIES;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.il.katya.finalproject.R;

public class SignUpActivity extends AppCompatActivity {
    private TextView tvWelcome;
    private TextView tvAppName;
    private TextView tvSignInPage;

    private EditText etFullName;
    private EditText etEmail;
    private EditText etPass;
    private EditText etPassRepeat;
    private EditText etPhone;

    private Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();
        setListeners();
    }

    private void initViews() {
        tvWelcome = findViewById(R.id.tvWelcome);
        tvAppName = findViewById(R.id.tvAppName);
        tvSignInPage = findViewById(R.id.tvSignInPage);
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPassword);
        etPassRepeat = findViewById(R.id.etPassRepeat);
        etPhone = findViewById(R.id.etPhone);
        btnRegister = findViewById(R.id.btnRegister);

    }

    private void setListeners() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDataEntered();
            }
        });

        tvSignInPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkDataEntered() {
        if (isEmpty(etFullName)) {
            Toast t = Toast.makeText(this, "You must enter full name to register!", Toast.LENGTH_SHORT);
            t.show();
        }

        if (isEmpty(etPhone)) {
            etPhone.setError("Phone is required!");
        }

        if(isEmpty(etPass)){
            etPass.setError("Password is required!");
        }

        if(isEmpty(etPassRepeat)){
            etPassRepeat.setError("Password is required!");
        }

        if (isEmail(etEmail) == false) {
            etEmail.setError("Enter valid email!");
        }

        if(etPass.getText() != etPassRepeat.getText()){
            etPass.setError("Passwords do not match!");
            etPassRepeat.setError("Passwords do not match!");
        }

    }
}