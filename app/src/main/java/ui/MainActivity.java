package ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import algonquin.cst2335.dai00047.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailAddress = prefs.getString("Email", "");
        Log.e("MainActivity", "In onCreate()");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.editText.setText(emailAddress);
        binding.button.setOnClickListener(click -> {
            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
            nextPage.putExtra("EmailAddress", binding.editText.getText().toString());
            prefs.edit().putString("Email", binding.editText.getText().toString()).commit();
            startActivity(nextPage);
        });


    }

    }












