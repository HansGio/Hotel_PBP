package com.hansgiovanni.hotel_pbp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    Button btnLogin, btnRegister;
    TextView tvTitle, tvSubtitle;
    TextInputLayout inputLayoutEmail, inputLayoutPassword;
    ImageView imgLogo;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);

        btnLogin = findViewById(R.id.button_login);
        btnRegister = findViewById(R.id.button_register);
        imgLogo = findViewById(R.id.image_logo);
        tvTitle = findViewById(R.id.text_title);
        tvSubtitle = findViewById(R.id.text_subtitle);
        inputLayoutEmail = findViewById(R.id.input_layout_name);
        inputLayoutPassword = findViewById(R.id.input_layout_password);

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
                pairs[5] = new Pair<View, String>(inputLayoutEmail, "email");
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
}