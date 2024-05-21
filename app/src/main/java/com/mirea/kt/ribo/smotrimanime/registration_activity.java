package com.mirea.kt.ribo.smotrimanime;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class registration_activity extends AppCompatActivity {


    private static final String TAG = "RegistrationUser";
    private EditText mailEdText,usernameEdText,passwordEdText;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ImageView visiblePass;
    private Button regButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();

        mailEdText = findViewById(R.id.mail_registration);
        usernameEdText = findViewById(R.id.nickname_acc);
        passwordEdText = findViewById(R.id.password_reg_edit_text);
        regButton = findViewById(R.id.reg_button_for_data);
        regButton.setOnClickListener(v -> {
        });
        visiblePass = findViewById(R.id.visible_password_reg);
        visiblePass.setOnClickListener(v -> {
            if (passwordEdText.getInputType()==129) {
                passwordEdText.setInputType(InputType.TYPE_CLASS_TEXT);
            } else{
                passwordEdText.setInputType(129);
            }
            passwordEdText.setSelection(passwordEdText.getText().length());
        });
    }
    private void createAccount(String email, String password) {
        String TAG = "Creating_account";
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            user = mAuth.getCurrentUser();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}