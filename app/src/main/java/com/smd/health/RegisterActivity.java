package com.smd.health;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    EditText emailText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);

        findViewById(R.id.login_btn).setOnClickListener(view -> {
            login();
        });

        findViewById(R.id.register_btn).setOnClickListener(view -> {
            register();
        });
    }

    void login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    void register() {
        auth.createUserWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString()).addOnCompleteListener(res -> {
            if (res.isSuccessful()) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else {
                Exception ex = res.getException();
                String message = "";
                if (ex instanceof FirebaseAuthWeakPasswordException) {
                    message = "Password too weak!!";
                }
                else if (ex instanceof FirebaseAuthInvalidCredentialsException) {
                    message = "Email is not of correct format";
                }
                else if (ex instanceof FirebaseAuthUserCollisionException) {
                    message = "User with that email already exists";
                }
                else {
                    message = "Unknown Error";
                }

                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}