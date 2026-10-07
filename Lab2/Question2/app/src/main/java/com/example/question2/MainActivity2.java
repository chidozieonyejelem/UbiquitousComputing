package com.example.question2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    String code;

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
        String name = intent.getStringExtra("name");
        code = intent.getStringExtra("code");

        TextView textMessage = findViewById(R.id.textMessage);
        textMessage.setText("Thank you " + name + ", your request is being processed");
    }

    public void onValidateClick(View view) {
        EditText editCode = findViewById(R.id.editCode);
        TextView textResult = findViewById(R.id.textResult);

        String enteredCode = editCode.getText().toString().trim();

        if (enteredCode.equals(code)) {
            textResult.setText("Your account has been validated");
        } else {
            editCode.setError("Wrong code, please try again");
        }
    }
}