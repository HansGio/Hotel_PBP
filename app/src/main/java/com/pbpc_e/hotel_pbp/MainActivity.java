package com.pbpc_e.hotel_pbp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Animation bottomAnim, topAnim, fadeAnim;
    ImageView imgLogo, imgStar1, imgStar2, imgStar3, imgStar4, imgStar5;
    TextView tvTitle, tvSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getWindow().setExitTransition(null);

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
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                if(FirebaseAuth.getInstance().getCurrentUser() != null) intent = new Intent(MainActivity.this, HomeActivity.class);
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
}