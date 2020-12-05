package com.pbpc_e.hotel_pbp;

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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Button btnLogin, btnRegister;
    TextView tvTitle, tvSubtitle;
    TextInputLayout inputLayoutEmail, inputLayoutPassword, inputLayoutName, inputLayoutPhone;
    TextInputEditText inputEmail, inputPassword, inputName, inputPhone;
    ImageView imgLogo;

    private static final String TAG = "RegisterActivity";
//    private FirebaseAuth mAuth;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);

//        mAuth = FirebaseAuth.getInstance();
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
        inputName = findViewById(R.id.input_name);
        inputPhone = findViewById(R.id.input_phone);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startLoginActivity();
            }
        });
    }

    public void startLoginActivity() {
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

    private void register() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserResponse> add = apiService.register(inputName.getText().toString(), inputEmail.getText().toString(), inputPassword.getText().toString(), inputPhone.getText().toString());

        add.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                inputLayoutEmail.setError(null);
                inputLayoutPassword.setError(null);
                inputLayoutName.setError(null);
                inputLayoutPhone.setError(null);

//                progressDialog.dismiss();
                if(response.code() == 200) {
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    startLoginActivity();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        if(jObjError.get("message") instanceof JSONObject){
                            if (jObjError.getJSONObject("message").has("email")) {
                                inputLayoutEmail.setError(jObjError.getJSONObject("message").getJSONArray("email").get(0).toString());
                            } if (jObjError.getJSONObject("message").has("password")) {
                                inputLayoutPassword.setError(jObjError.getJSONObject("message").getJSONArray("password").get(0).toString());
                            } if (jObjError.getJSONObject("message").has("name")) {
                                inputLayoutName.setError(jObjError.getJSONObject("message").getJSONArray("name").get(0).toString());
                            } if (jObjError.getJSONObject("message").has("phone")) {
                                inputLayoutPhone.setError(jObjError.getJSONObject("message").getJSONArray("phone").get(0).toString());
                            }
                        } else
                            Toast.makeText(RegisterActivity.this, jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
            }
        });
    }
}