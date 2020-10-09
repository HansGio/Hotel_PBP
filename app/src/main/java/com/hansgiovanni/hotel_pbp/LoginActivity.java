package com.hansgiovanni.hotel_pbp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.transition.Slide;
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

public class LoginActivity extends AppCompatActivity {

    Button btnRegister, btnLogin;
    TextView tvTitle, tvSubtitle;
    TextInputLayout inputLayoutEmail, inputLayoutPassword;
    ImageView imgLogo;
    TextInputEditText inputEmail, inputPassword;
    FirebaseAuth fAuth;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);

        imgLogo = findViewById(R.id.image_logo);
        tvTitle = findViewById(R.id.text_title);
        tvSubtitle = findViewById(R.id.text_subtitle);
        btnRegister = findViewById(R.id.button_register);
        btnLogin = findViewById(R.id.button_login);
        inputLayoutEmail = findViewById(R.id.input_layout_email);
        inputLayoutPassword = findViewById(R.id.input_layout_password);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        fAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if (isValid(email, password)) {
                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Log in successful!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Log in failed!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(imgLogo, "logo");
                pairs[1] = new Pair<View, String>(tvTitle, "title");
                pairs[2] = new Pair<View, String>(tvSubtitle, "subtitle");
                pairs[3] = new Pair<View, String>(btnLogin, "main_button");
                pairs[4] = new Pair<View, String>(btnRegister, "sub_button");
                pairs[5] = new Pair<View, String>(inputLayoutEmail, "email");
                pairs[6] = new Pair<View, String>(inputLayoutPassword, "password");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
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

    private boolean isValid(String email, String password) {
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

        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && !password.isEmpty() && password.length() >= 6;
    }
}