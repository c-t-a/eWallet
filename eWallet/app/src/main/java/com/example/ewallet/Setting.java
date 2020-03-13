package com.example.ewallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.AppCompatDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import java.util.prefs.Preferences;

public class Setting extends AppCompatActivity {

    private Button buttonSave;
    private EditText editBalance;
    private int newBalance;
    private Switch switchNightMode;

    SharedPreferences preferencesSaveSwitchChecked;

    Preferences pBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //Set actionbar title
        getSupportActionBar().setTitle("Setting");

        editBalance = (EditText) findViewById(R.id.Balance);
        switchNightMode = (Switch) findViewById(R.id.NightMode);
        buttonSave = (Button) findViewById(R.id.Save);

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            switchNightMode.setChecked(true);
        }
        switchNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartApp();
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restartApp();
                }
            }
        });

        buttonSave.setBackgroundResource(R.drawable.button_disable);
        buttonSave.setEnabled(false);
        //add TextWatcher in edit text field for new balance
        editBalance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    buttonSave.setBackgroundResource(R.drawable.button_disable);
                    buttonSave.setEnabled(false);
                }else{
                    buttonSave.setBackgroundResource(R.drawable.button_enable);
                    buttonSave.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    public void saveSetting(View view){
        // Get new balance from user input
        newBalance = Integer.parseInt(editBalance.getText().toString());
        SharedPreferences preferencesSetNewBalance = getSharedPreferences("shared preferences",MODE_PRIVATE);
        preferencesSetNewBalance.edit().putInt("New Balance",newBalance).apply();
        // Clear Transaction History
        SharedPreferences sharedPreferences = getSharedPreferences("shared list preferences",MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        // Transmit data into MainActivity
        Intent myIntent = new Intent(this,MainActivity.class);
        startActivity(myIntent);
    }
    public void restartApp(){
        Intent intent = new Intent(getApplicationContext(),Setting.class);
        startActivity(intent);
        finish();
    }
}
