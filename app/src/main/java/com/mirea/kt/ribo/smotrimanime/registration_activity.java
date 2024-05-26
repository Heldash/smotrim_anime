package com.mirea.kt.ribo.smotrimanime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mirea.kt.ribo.smotrimanime.utils_for_storage.Account;

public class registration_activity extends AppCompatActivity implements OnCompleteListener{

    private FirebaseDatabase database;
    private static final String TAG = "RegistrationUser";
    private EditText mailEdText, usernameEdText, passwordEdText;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ImageView visiblePass;
    private Button regButton;

    private String username,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        mailEdText = findViewById(R.id.mail_registration);
        usernameEdText = findViewById(R.id.nickname_acc);
        passwordEdText = findViewById(R.id.password_reg_edit_text);
        regButton = findViewById(R.id.reg_button_for_data);
        regButton.setOnClickListener(v -> {
            this.username = usernameEdText.getText().toString();
            this.email = mailEdText.getText().toString();
            String password = passwordEdText.getText().toString();
            if(username!=null&& email != null&&
                    password!=null && !username.isEmpty() && !email.isEmpty() &&
                    !password.isEmpty()){
                createAccount(email,password);
            }
        });
        visiblePass = findViewById(R.id.visible_password_reg);
        visiblePass.setOnClickListener(v -> {
            if (passwordEdText.getInputType() == 129) {
                passwordEdText.setInputType(InputType.TYPE_CLASS_TEXT);
            } else {
                passwordEdText.setInputType(129);
            }
            passwordEdText.setSelection(passwordEdText.getText().length());
        });
    }

    private void createAccount(String email, String password) {
        String TAG = "Creating_account";
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, this);
    }

    @Override
    public void onComplete(@androidx.annotation.NonNull Task task) {
        if (task.isSuccessful()) {
            Log.d(TAG, "createUserWithEmail:success");
            user = mAuth.getCurrentUser();
            if (user!=null){
                DatabaseReference reference = database.getReference("Users");
                String UID = user.getUid();
                Log.d("User_UID",UID);
                Account acc = new Account(username,email,UID,reference.getKey(),"");
                reference.child(UID).setValue(acc).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(getApplicationContext(),mainMenu.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(), "Ошибка на сервере",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else {
                Toast.makeText(getApplicationContext(), "Ошибка авторизации.",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.w(TAG, "createUserWithEmail:failure", task.getException());
            Toast.makeText(getApplicationContext(), "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}