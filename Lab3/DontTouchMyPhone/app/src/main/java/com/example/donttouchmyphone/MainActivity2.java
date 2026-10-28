package com.example.donttouchmyphone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String time = intent.getStringExtra("time");
        float x = intent.getFloatExtra("x", 0);
        float y = intent.getFloatExtra("y", 0);
        float z = intent.getFloatExtra("z", 0);
        float change = intent.getFloatExtra("change", 0);

        TextView textDetails = findViewById(R.id.textDetails);
        textDetails.setText("Motion detected at: " + time
                + "\n\nX: " + x
                + "\nY: " + y
                + "\nZ: " + z
                + "\n\nAmount of movement: " + change);
    }
}