package com.example.uts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn_mc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_mc = findViewById(R.id.Multiple_Choice);
        btn_mc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Multiple_Choice.class);
                startActivity(intent);
            }
        });
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

