package com.example.uts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mc(View view) {
        Intent intent = new Intent(MainActivity.this, multiple_choice_Activity.class);
        System.out.println("MC");
        startActivity(intent);
    }

    @Override
    public void onStart() {
        System.out.println("Start");
        super.onStart();
    }


    @Override
    protected void onResume() {
        System.out.println("onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        System.out.println("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        System.out.println("onStop");
        super.onStop();
    }
}

