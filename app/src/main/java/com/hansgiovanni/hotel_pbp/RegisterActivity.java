package com.hansgiovanni.hotel_pbp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    Button btnLogin, btnRegister;
    TextView tvTitle, tvSubtitle;
    TextInputLayout inputLayoutEmail, inputLayoutPassword, inputLayoutName, inputLayoutPhone;
    TextInputEditText inputEmail, inputPassword, inputName, inputPhone;
    ImageView imgLogo;
    private FirebaseAuth mAuth;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);

        mAuth = FirebaseAuth.getInstance();
        btnLogin = findViewById(R.id.button_login);
        btnRegister = findViewById(R.id.button_register);
        imgLogo = findViewById(R.id.image_logo);
        tvTitle = findViewById(R.id.text_title);
        tvSubtitle = findViewById(R.id.text_subtitle);
        inputLayoutName = findViewById(R.id.input_layout_name);
        inputLayoutEmail = findViewById(R.id.input_layout_email);
        inputLayoutPassword = findViewById(R.id.input_layout_password);
        inputLayoutName = findViewById(R.id.input_layout_name);
        inputLayoutPhone = findViewById(R.id.input_layout_phone);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        inputName= findViewById(R.id.input_name);
        inputPhone = findViewById(R.id.input_phone);

        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String name = inputName.getText().toString();
                String phone = inputPhone.getText().toString();

                if (isValid(email, password, name, phone)){
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Log in successful!", Toast.LENGTH_SHORT).show();
                                        // If sign in fails, display a message to the user.
    //                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
    //                                    Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
    //                                            Toast.LENGTH_SHORT).show();
    //                                    updateUI(null);
                                    }

                                }
                            });
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(imgLogo, "logo");
                pairs[1] = new Pair<View, String>(tvTitle, "title");
                pairs[2] = new Pair<View, String>(tvSubtitle, "subtitle");
                pairs[3] = new Pair<View, String>(btnRegister, "main_button");
                pairs[4] = new Pair<View, String>(btnLogin, "sub_button");
                pairs[5] = new Pair<View, String>(inputLayoutName, "email");
                pairs[6] = new Pair<View, String>(inputLayoutPassword, "password");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, pairs);
                startActivity(intent, options.toBundle());

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);
            }
        });


    }

    private boolean isValid(String email, String password, String name, String phone) {
        if (email.isEmpty()){
            inputLayoutEmail.setError("Please enter email");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            inputLayoutEmail.setError("Invalid email");
        } else inputLayoutEmail.setError(null);

        if (password.isEmpty()) {
            inputLayoutPassword.setError("Please enter password");
        } else if (password.length() < 6){
            inputLayoutPassword.setError("Password too short");
        } else inputLayoutPassword.setError(null);

        if (name.isEmpty()) {
            inputLayoutName.setError("Please enter name");
        } else if (name.length() < 6){
            inputLayoutName.setError("Name too short");
        } else inputLayoutName.setError(null);

        if (phone.isEmpty()) {
            inputLayoutPhone.setError("Please enter phone number");
        } else if (phone.length() < 6){
            inputLayoutPhone.setError("Phone too short");
        } else inputLayoutPhone.setError(null);

        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                !password.isEmpty() && password.length() >= 6 &&
                !name.isEmpty() && name.length() >= 6 &&
                !phone.isEmpty() && phone.length() >= 6;
    }
}