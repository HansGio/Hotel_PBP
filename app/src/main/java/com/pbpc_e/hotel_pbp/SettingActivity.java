package com.pbpc_e.hotel_pbp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingActivity extends AppCompatActivity {

    private static final String PREF_NAME = "Theme";

    SharedPreferences preferences;
    boolean isDarkMode;

    Button btnDarkMode;
    ImageView imageTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btnDarkMode = findViewById(R.id.button_darkmode);
        imageTheme = findViewById(R.id.image_theme);
        loadPreferences();

        if (isDarkMode) {
            btnDarkMode.setText("Disable Dark Mode");
            imageTheme.setImageResource(R.drawable.cloud_night);
        } else {
            btnDarkMode.setText("Enable Dark Mode");
            imageTheme.setImageResource(R.drawable.cloud_day);
        }

        btnDarkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDarkMode){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    btnDarkMode.setText("Enable Dark Mode");
                    imageTheme.setImageResource(R.drawable.cloud_day);
                    isDarkMode = false;
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    btnDarkMode.setText("Disable Dark Mode");
                    imageTheme.setImageResource(R.drawable.cloud_night);
                    isDarkMode = true;
                }
                savePreferences();
            }
        });
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("darkMode", isDarkMode);
        editor.apply();
    }

    private void loadPreferences() {
        preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        isDarkMode = preferences.getBoolean("darkMode", false);
    }
}