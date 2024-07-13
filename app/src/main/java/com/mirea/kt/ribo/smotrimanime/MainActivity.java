package com.mirea.kt.ribo.smotrimanime;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mirea.kt.ribo.smotrimanime.rec_views.MainMenu;

import org.checkerframework.checker.nullness.qual.NonNull;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button regBtn,gusetBtn,authButton;
    private EditText loginEdText,passwordEdText;
    private ImageView visiblePass;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user==null) {
            Log.d("authorization",user+"");
            setContentView(R.layout.activity_main);
            gusetBtn = findViewById(R.id.guest_btn);
            gusetBtn.setOnClickListener(v -> {
//                View layout =(View) getResources().getLayout(R.layout.activity_registration);
//                Animation anim = AnimationUtils.loadAnimation(this,R.anim.inner_video);
//                setContentView(layout);
//
                Log.d("authorization_log", "start activity MainMenu");
                startActivity(new Intent(getApplicationContext(), MainMenu.class));
                finish();
            });
            regBtn = findViewById(R.id.reg_btn_auth);
            authButton = findViewById(R.id.loginButton);
            visiblePass = findViewById(R.id.visible_password_reg);
            visiblePass.setOnClickListener(v -> {
                if (passwordEdText.getInputType() == 129) {
                    passwordEdText.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    passwordEdText.setInputType(129);
                }
                passwordEdText.setSelection(passwordEdText.getText().length());
            });
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
//            setContentView(R.layout.activity_registration);
        }else{
            startActivity(new Intent(this, MainMenu.class));
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
                            startActivity(new Intent(getApplicationContext(), MainMenu.class));

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