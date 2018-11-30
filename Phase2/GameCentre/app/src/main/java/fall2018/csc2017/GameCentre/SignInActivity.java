/*
View
 */
package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity for the sign in procedure for the app.
 */
public class SignInActivity extends AppCompatActivity {

    /**
     * The file system
     */
    private FileSystem fileSystem = new FileSystem();

    /**
     * The controller that manages the logic in this View.
     */
    private SignInActivityController controller;

    /**
     * The current activity.
     */
    private Context currentContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        controller = new SignInActivityController(fileSystem);
        addEnterButtonListener();
    }

    /**
     * Returns a toast according to the username and password correctness after Enter is clicked.
     * Directs to GameMenu if it is correct.
     */
    private void addEnterButtonListener() {
        Button enterButton = findViewById(R.id.button);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText temp_user = findViewById(R.id.editText);
                EditText temp_password = findViewById(R.id.editText2);

                Boolean signInSuccess = controller.enterButtonListener(currentContext, temp_user.getText().toString(), temp_password.getText().toString());
                if (signInSuccess){
                    Toast.makeText(currentContext, "Loading...", Toast.LENGTH_SHORT).show();
                    switchToGameMenu();
                }else{
                    Toast.makeText(currentContext, "Wrong username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Switch to GameMenu view to go to the menu page
     */
    private void switchToGameMenu() {
        Intent tmp = new Intent(this, NotRobotActivity.class);
        startActivity(tmp);
    }
}
