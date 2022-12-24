package com.smd.health;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    EditText emailText;
    EditText passwordText;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);

        Button registerBtn = findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(view -> {
            register();
        });
        Button loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(view -> {
            login();
        });
    }

    void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    void login() {
        auth.signInWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString()).addOnCompleteListener(res -> {
            if (res.isSuccessful()) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else {
                Exception ex = res.getException();
                String message = "";
                if (ex instanceof FirebaseAuthInvalidUserException) {
                    message = "User with this email does not exist";
                }
                else if (ex instanceof FirebaseAuthInvalidCredentialsException) {
                    message = "Password is incorrect";
                }
                else {
                    message = "Unknown Error";
                }

                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}