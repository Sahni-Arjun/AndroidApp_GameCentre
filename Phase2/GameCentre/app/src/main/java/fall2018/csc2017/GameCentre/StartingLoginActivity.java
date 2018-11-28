package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class StartingLoginActivity extends AppCompatActivity {

    /**
     * The names of the save account manager ser file and scoreboard ser file.
     */
    public static final String SAVE_ACCOUNT_MANAGER = "save_account_manager.ser";
    public static final String SAVE_SLIDING_SCOREBOARD = "save_sliding_scoreboard.ser";
    public static final String SAVE_SUDOKU_SCOREBOARD = "save_sudoku_scoreboard.ser";
    public static final String SAVE_HANGMAN_SCOREBOARD = "save_hangman_scoreboard.ser";

    /**
     * The current user's name.
     */
    public static String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_login);
        SlidingTileComplexityActivity.complexity = 2;
        addSignInButtonListener();
        addSignUpButtonListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Board.numCols = 2;
        Board.numRows = 2;
    }

    @Override
    public void onBackPressed() {
    }

    private void addSignInButtonListener() {
        Button saveButton = findViewById(R.id.btnSignIn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSignIn();
            }
        });
    }

    /**
     * Switch to the SignInActivity view to sign in.
     */
    private void switchToSignIn() {
        Intent tmp = new Intent(this, SignInActivity.class);
        startActivity(tmp);
    }

    /**
     * Activates sign up button.
     */
    private void addSignUpButtonListener() {
        Button saveButton = findViewById(R.id.btnSignUp);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSignUp();
            }
        });
    }

    /**
     * Switch to the SignUpActivity view to sign up.
     */
    private void switchToSignUp() {
        Intent tmp = new Intent(this, SignUpActivity.class);
        startActivity(tmp);
    }
}
