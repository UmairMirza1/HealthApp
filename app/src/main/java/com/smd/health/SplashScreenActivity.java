package com.smd.health;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

        if (auth.getCurrentUser() != null) {
            Intent startIntent = new Intent(this, MainActivity.class);
            startActivity(startIntent);
        }
        else {
            Intent startIntent = new Intent(this, LoginActivity.class);
            startActivity(startIntent);
        }
    }
}