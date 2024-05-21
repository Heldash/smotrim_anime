package com.mirea.kt.ribo.smotrimanime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class mainMenu extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ImageButton menuButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.draw_layout);
        menuButton = findViewById(R.id.menu_btn);
        menuButton.setOnClickListener(v -> {
            drawerLayout.open();
        });
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId()==R.id.acc_button){
                Toast.makeText(this,"acc_button",Toast.LENGTH_LONG).show();
                drawerLayout.close();
                return true;
            } else if (menuItem.getItemId()==R.id.random_btn){
                Toast.makeText(this,"rand_btn",Toast.LENGTH_LONG).show();
                drawerLayout.close();
                return true;
            } else {
                return true;
            }
        });

    }
}