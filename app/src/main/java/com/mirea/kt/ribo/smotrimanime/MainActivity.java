package com.mirea.kt.ribo.smotrimanime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button regBtn,gusetBtn,authButton;
    private EditText loginEdText,passwordEdText;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user==null) {
            Log.d("authoriz_blyat",user+"");
            setContentView(R.layout.activity_main);
            gusetBtn = findViewById(R.id.guest_btn);
            gusetBtn.setOnClickListener(v -> {
                Log.d("authorization_log", "start activity mainMenu");
                startActivity(new Intent(getApplicationContext(), mainMenu.class));
                finish();
            });
            regBtn = findViewById(R.id.reg_btn_auth);
            authButton = findViewById(R.id.loginButton);
            authButton.setOnClickListener(v -> {
                Log.d("Authorization", "loginnnnn");
                String login = loginEdText.getText().toString();
                String password = passwordEdText.getText().toString();
                if (login != null && password != null && !login.isEmpty() &&
                        !password.isEmpty()){
                    Log.d("Authorization", "login+password");
                    loginUser(login,password);
                }else{
                    Toast.makeText(this,"Заполните поля логин и пароль",Toast.LENGTH_LONG).show();
                }
            });
            loginEdText = findViewById(R.id.loginPole);
            passwordEdText = findViewById(R.id.passwordPole);
            regBtn.setOnClickListener(v -> startActivity(new Intent(this, registration_activity.class)));
        }else{
            startActivity(new Intent(this,mainMenu.class));
            finish();
        }
    }
    public void loginUser(String login,String password){
        Log.d("Authorization", login+":"+password);
        mAuth.signInWithEmailAndPassword(login, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Authorization", "signInWithEmail:success");
                            startActivity(new Intent(getApplicationContext(),mainMenu.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AuthorizationFailed", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {

    }
}