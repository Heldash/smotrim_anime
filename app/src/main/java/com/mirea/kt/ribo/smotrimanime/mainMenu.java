package com.mirea.kt.ribo.smotrimanime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class mainMenu extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ImageButton menuButton;
    private TextView logOutOrLogIn;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.draw_layout);
        menuButton = findViewById(R.id.menu_btn);
        menuButton.setOnClickListener(v -> {
            drawerLayout.open();
        });
        logOutOrLogIn = drawerLayout.findViewById(R.id.logInOrOut);
        logOutOrLogIn.setOnClickListener(v -> {
            mAuth.signOut();
            user = mAuth.getCurrentUser();
            startActivity(new Intent(this,MainActivity.class));
            finish();
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