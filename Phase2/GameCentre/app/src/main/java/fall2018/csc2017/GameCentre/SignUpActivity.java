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
 * The activity which facilitates sign-ups.
 */
public class SignUpActivity extends AppCompatActivity {

    /**
     * The file system
     */
    private FileSystem fileSystem = new FileSystem();

    /**
     * The current activity.
     */
    private Context currentContext = this;

    /**
     * The controller that manages the logic in this class.
     */
    private SignUpActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        controller = new SignUpActivityController(fileSystem);
        addEnterButtonListener();
    }

    /**
     * Returns a toast according to whether or not username exists after Enter is clicked.
     * Directs to GameMenu if it is correct.
     */
    private void addEnterButtonListener() {
        Button enterButton = findViewById(R.id.button2);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText temp_user = findViewById(R.id.editText3);
                EditText temp_password = findViewById(R.id.editText4);

                String signUpMessage = controller.enterButtonListener(currentContext, temp_user.getText().toString(), temp_password.getText().toString());
                Toast.makeText(currentContext, signUpMessage, Toast.LENGTH_SHORT).show();

                if (signUpMessage.equals("Loading...")){
                    switchToGameMenu();
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
