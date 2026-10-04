package com.example.androidform;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onSubmitClick(View view) {
        EditText editName = findViewById(R.id.editName);
        EditText editPassword = findViewById(R.id.editPassword);
        EditText editPhone = findViewById(R.id.editPhone);
        EditText editEmail = findViewById(R.id.editEmail);

        String name = editName.getText().toString().trim();
        String password = editPassword.getText().toString();
        String phone = editPhone.getText().toString().trim();
        String email = editEmail.getText().toString().trim();

        boolean valid = true;

        boolean nameHasDigit = false;
        for (int i = 0; i < name.length(); i++) {
            if (Character.isDigit(name.charAt(i))) {
                nameHasDigit = true;
            }
        }
        if (name.isEmpty()) {
            editName.setError("Please enter your name");
            valid = false;
        } else if (nameHasDigit) {
            editName.setError("Name cannot contain numbers");
            valid = false;
        }

        if (password.length() < 6) {
            editPassword.setError("Password must be at least 6 characters");
            valid = false;
        }

        boolean phoneHasLetter = false;
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                phoneHasLetter = true;
            }
        }
        if (phone.isEmpty()) {
            editPhone.setError("Please enter your phone number");
            valid = false;
        } else if (phoneHasLetter) {
            editPhone.setError("Phone number can only contain digits");
            valid = false;
        }

        if (!email.contains("@") || !email.contains(".")) {
            editEmail.setError("Please enter a valid email address");
            valid = false;
        }

        if (valid) {
            Toast.makeText(this, "Thank you " + name + ", your request is being processed",
                    Toast.LENGTH_LONG).show();
        }
    }
}