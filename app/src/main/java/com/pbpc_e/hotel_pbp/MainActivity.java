package com.pbpc_e.hotel_pbp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private static final String THEME_PREF_NAME = "Theme";
    private static final String USER_PREF_NAME = "User";

    SharedPreferences preferencesTheme, preferencesUser;
    boolean isDarkMode;
    String email = "", token = "";
    int id = -1;

    Animation bottomAnim, topAnim, fadeAnim;
    ImageView imgLogo, imgStar1, imgStar2, imgStar3, imgStar4, imgStar5;
    TextView tvTitle, tvSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getWindow().setExitTransition(null);

        loadPreferences();
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "Channel 1";
            CharSequence name = "Channel 1";
            String description = "This is Channel 1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            FirebaseMessaging.getInstance().subscribeToTopic("news")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful())
                                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        fadeAnim = AnimationUtils.loadAnimation(this, R.anim.fade);

        imgStar1 = findViewById(R.id.image_star1);
        imgStar2 = findViewById(R.id.image_star2);
        imgStar3 = findViewById(R.id.image_star3);
        imgStar4 = findViewById(R.id.image_star4);
        imgStar5 = findViewById(R.id.image_star5);
        imgLogo = findViewById(R.id.image_logo);
        tvTitle = findViewById(R.id.text_title);
        tvSubtitle = findViewById(R.id.text_subtitle);

        imgStar1.setAnimation(fadeAnim);
        imgStar2.setAnimation(fadeAnim);
        imgStar3.setAnimation(fadeAnim);
        imgStar4.setAnimation(fadeAnim);
        imgStar5.setAnimation(fadeAnim);
        imgLogo.setAnimation(topAnim);
        tvTitle.setAnimation(bottomAnim);
        tvSubtitle.setAnimation(bottomAnim);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (!token.isEmpty())
                    intent = new Intent(MainActivity.this, MenuActivity.class);
                else intent = new Intent(MainActivity.this, LoginActivity.class);

//                Pair[] pairs = new Pair[3];
//                pairs[0] = new Pair<View, String>(imgLogo, "logo");
//                pairs[1] = new Pair<View, String>(tvTitle, "title");
//                pairs[2] = new Pair<View, String>(tvSubtitle, "subtitle");
//
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
//                startActivity(intent, options.toBundle());
                startActivity(intent);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1500);
            }
        }, 3500);

    }

    private void savePreferences() {
        SharedPreferences.Editor editor = preferencesTheme.edit();
        editor.putBoolean("darkMode", isDarkMode);
        editor.apply();
    }

    private void loadPreferences() {
        preferencesTheme = getSharedPreferences(THEME_PREF_NAME, MODE_PRIVATE);
        isDarkMode = preferencesTheme.getBoolean("darkMode", false);

        preferencesUser = getSharedPreferences(USER_PREF_NAME, MODE_PRIVATE);
        email = preferencesUser.getString("email", "");
        token = preferencesUser.getString("token", "");
        id = preferencesUser.getInt("id", -1);
    }
}