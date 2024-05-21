package com.mirea.kt.ribo.smotrimanime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button regBtn,gusetBtn,authButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gusetBtn = findViewById(R.id.guest_btn);
        gusetBtn.setOnClickListener(v->{
            Log.d("authorization_log","start activity mainMenu");
            startActivity(new Intent(this, mainMenu.class));
            finish();
        });
        regBtn = findViewById(R.id.reg_btn_auth);
        authButton = findViewById(R.id.loginBtn);
        regBtn.setOnClickListener(v -> startActivity(new Intent(this,registration_activity.class)));
        authButton.setOnClickListener(v ->{

        });
    }
}