package com.example.binlin.guessinggame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    private Button guessButton;
    private TextView clue;
    private EditText guess;
    private int generatedNumber;
    private int numberOfGuesses = 0;
    private final int MAX_GUESS_COUNT = 4;
    public static final String winningNumber = "WINNING_NUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        guessButton = findViewById(R.id.submit_button);
        clue = findViewById(R.id.clue_textView);
        guess = findViewById(R.id.guess_editText);

        // this generate a random number between 1 and 100
        generatedNumber = (int) Math.ceil(Math.random() * 100);
        // Toast.makeText(this, Integer.toString(generatedNumber), Toast.LENGTH_SHORT).show();

        setListeners();
    }

    private void setListeners() {
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateGuess();
            }
        });
    }

    // check to make sure the user enter a valid number
    private void validateGuess() {
        try {
            int userGuess = Integer.parseInt(guess.getText().toString());
            if (userGuess > 100 || userGuess <= 0) {
                clue.setText("Enter a Number Between 1 and 100");
                clue.setVisibility(View.VISIBLE);
                guess.setText("");
            } else {
                checkGuess(userGuess);
            }
        } catch (NumberFormatException exception) {
            clue.setText("Please Enter a Number!");
            clue.setVisibility(View.VISIBLE);
        }

    }

    // take the userGuess and determine if it is correct or not
    private void checkGuess(int userGuess) {
        if (userGuess == generatedNumber) {
            // Goes to ResultsActivity, User has guessed correctly
            Intent winner = new Intent(this, ResultsActivity.class);
            startActivity(winner);

        } else if (numberOfGuesses == MAX_GUESS_COUNT) {
            // Goes to ResultsActivity, User has ran out of chances
            Intent loser = new Intent(this, ResultsActivity.class);
            loser.putExtra(winningNumber, generatedNumber);
            startActivity(loser);

        } else if (userGuess < generatedNumber) {
            // Update clue textView to say higher, make sure the visibility is visible, set guess EditText to "" and increment numberOfGuess by 1
            clue.setText(R.string.higher);
            clue.setVisibility(View.VISIBLE);
            guess.setText("");
            numberOfGuesses++;


        } else if (userGuess > generatedNumber) {
            // Update clue textView to say lower, make sure the visibility is visible, set guess EditText to "" and increment numberOfGuess by 1
            clue.setText("lower");
            clue.setVisibility(View.VISIBLE);
            guess.setText("");
            numberOfGuesses++;
        }
    }


    // remove super.onBackPressed to make sure if the back button is pressed nothing will happen
    @Override
    public void onBackPressed() {

    }
}

