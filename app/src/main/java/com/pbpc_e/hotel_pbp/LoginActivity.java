package com.pbpc_e.hotel_pbp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String USER_PREF_NAME = "User";

    SharedPreferences preferences;
    String token = "";
    int id = -1;

    Button btnRegister, btnLogin;
    TextView tvTitle, tvSubtitle;
    TextInputLayout inputLayoutEmail, inputLayoutPassword;
    ImageView imgLogo;
    TextInputEditText inputEmail, inputPassword;
    ProgressDialog progressDialog;
//    FirebaseAuth fAuth;

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
        progressDialog = new ProgressDialog(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
//                String email = inputEmail.getText().toString();
//                String password = inputPassword.getText().toString();
//
//                if (isValid(email, password)) {
//                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(LoginActivity.this, "Log in successful!", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
//                                startActivity(intent);
//                                finish();
//                            } else {
//                                Toast.makeText(LoginActivity.this, "Log in failed!", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                    });
//                }
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

    private void login() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserResponse> add = apiService.login(inputEmail.getText().toString(), inputPassword.getText().toString());

        progressDialog.show();
        add.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                inputLayoutEmail.setError(null);
                inputLayoutPassword.setError(null);

                progressDialog.dismiss();
                if(response.code() == 200) {
                    if (response.body().getUser() != null){
                        id = response.body().getUser().getId();
                        token = response.body().getAccessToken();
                    }
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        if(jObjError.get("message") instanceof JSONObject){
                            if (jObjError.getJSONObject("message").has("email")) {
                                inputLayoutEmail.setError(jObjError.getJSONObject("message").getJSONArray("email").get(0).toString());
                            } if (jObjError.getJSONObject("message").has("password")) {
                                inputLayoutPassword.setError(jObjError.getJSONObject("message").getJSONArray("password").get(0).toString());
                            }
                        } else
                            Toast.makeText(LoginActivity.this, jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                if (id != -1 && !token.isEmpty()){
                    savePreferences();
                    Intent i;
//                    if(email.equals("admin")){
//                        i = new Intent(LoginActivity.this, MenuActivity.class);
//                    }
//                    else{
                        i = new Intent(LoginActivity.this, MenuActivity.class);
//                    }
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void savePreferences() {
        preferences = getSharedPreferences(USER_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id", id);
        editor.putString("token", token);
        editor.apply();
    }

    private void loadPreferences() {
        preferences = getSharedPreferences(USER_PREF_NAME, MODE_PRIVATE);
        token = preferences.getString("token", "");
        id = preferences.getInt("id", -1);
    }
}