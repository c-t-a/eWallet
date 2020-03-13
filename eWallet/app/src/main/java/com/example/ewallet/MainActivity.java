package com.example.ewallet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {

    ArrayList<Items> ItemsList;
    int addPrice,chkBalance,newBalance,Balance;
    String addCategory;

    private AlertDialog dialog;

    private Button buttonIncomeExpenseDialog,buttonEnter;
    private EditText category,price;
    private RadioGroup radioGroup;
    private RadioButton radioButton,radioIncome,radioExpense;
    private ListView listViewItem;
    private TextView textBalance;

    @Override
    protected void onStart() {
        super.onStart();
        //get Balance from share Preferences
        SharedPreferences preferencesGetNewBalance = getSharedPreferences("shared preferences",MODE_PRIVATE);
        Balance = preferencesGetNewBalance.getInt("New Balance",Balance);
        textBalance.setText(""+Balance);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load data from JSON
        LoadData();

        listViewItem = (ListView) findViewById(R.id.ItemList);
        textBalance = (TextView) findViewById(R.id.Balance);
        buttonIncomeExpenseDialog = (Button) findViewById(R.id.IncomeExpenseDialog);
        buttonIncomeExpenseDialog.setBackgroundResource(R.drawable.button_default);

        MainAdapter adapter = new MainAdapter(MainActivity.this,ItemsList);
        listViewItem.setAdapter(adapter);
    }
    public void OpenDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.activity_dialog);

        dialog = builder.create();
        dialog.show();

        category = dialog.findViewById(R.id.Category);
        price = dialog.findViewById(R.id.Price);
        radioGroup = dialog.findViewById(R.id.radioGroup);
        radioIncome = dialog.findViewById(R.id.Income);
        radioExpense = dialog.findViewById(R.id.Expense);
        radioExpense.setChecked(true);
    }
    public void CloseDialog(View view){
        // get add category from user input
        addCategory = category.getText().toString();
        // get add price from user input
        try {
            addPrice = Integer.parseInt(price.getText().toString());
        }catch (NumberFormatException e){
            addPrice = 0;
        }
        // get check radio button id from user input
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = dialog.findViewById(radioID);
        if(radioButton == radioExpense){
            addPrice = -addPrice;
        }else{
            addPrice = addPrice;
        }
        // get balance from Text View
        chkBalance = Integer.parseInt(textBalance.getText().toString());
        // Calculate balance
        chkBalance = chkBalance + addPrice;
        SharedPreferences preferencesSetCalcualteBalance = getSharedPreferences("shared preferences",MODE_PRIVATE);
        preferencesSetCalcualteBalance.edit().putInt("New Balance",chkBalance).apply();
        textBalance.setText(""+chkBalance);
        if(addCategory!=null && addPrice!=0){
            ItemsList.add(new Items(addCategory,addPrice));
            SharedPreferences sharedPreferences = getSharedPreferences("shared list preferences",MODE_PRIVATE);
            Gson gson = new Gson();
            String json = gson.toJson(ItemsList);
            sharedPreferences.edit().putString("take list",json).apply();
        }
        dialog.dismiss();
    }
    public void LoadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared list preferences",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("take list",null);
        Type type = new TypeToken<ArrayList<Items>>() {}.getType();
        ItemsList = gson.fromJson(json,type);
        if(ItemsList == null){
            ItemsList = new ArrayList<>();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                startActivity(new Intent(MainActivity.this, Setting.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
