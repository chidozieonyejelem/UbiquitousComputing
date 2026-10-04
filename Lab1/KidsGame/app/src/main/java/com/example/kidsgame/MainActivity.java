package com.example.kidsgame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int secretNumber;
    private int guessCount;
    private Random random = new Random();

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

        startNewGame();
    }

    public void onGuessClick(View view) {
        EditText editGuess = findViewById(R.id.editGuess);
        TextView textResult = findViewById(R.id.textResult);
        TextView textGuesses = findViewById(R.id.textGuesses);
        Button buttonGuess = findViewById(R.id.buttonGuess);
        Button buttonPlayAgain = findViewById(R.id.buttonPlayAgain);

        String input = editGuess.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
            return;
        }

        int guess = Integer.parseInt(input);

        if (guess < 1 || guess > 30) {
            Toast.makeText(this, "Your guess must be between 1 and 30", Toast.LENGTH_SHORT).show();
            return;
        }

        guessCount++;
        textGuesses.setText("Number of guesses: " + guessCount);

        if (guess == secretNumber) {
            textResult.setText("Correct! The number was " + secretNumber + "!");
            buttonGuess.setEnabled(false);
            buttonPlayAgain.setVisibility(View.VISIBLE);
        } else if (guess < secretNumber) {
            textResult.setText("Higher than " + guess);
        } else {
            textResult.setText("Lower than " + guess);
        }

        editGuess.setText("");
    }

    public void onPlayAgainClick(View view) {
        startNewGame();
    }

    private void startNewGame() {
        EditText editGuess = findViewById(R.id.editGuess);
        TextView textResult = findViewById(R.id.textResult);
        TextView textGuesses = findViewById(R.id.textGuesses);
        Button buttonGuess = findViewById(R.id.buttonGuess);
        Button buttonPlayAgain = findViewById(R.id.buttonPlayAgain);

        secretNumber = random.nextInt(30) + 1;
        guessCount = 0;

        editGuess.setText("");
        textResult.setText("");
        textGuesses.setText("Number of guesses: 0");
        buttonGuess.setEnabled(true);
        buttonPlayAgain.setVisibility(View.GONE);
    }
}