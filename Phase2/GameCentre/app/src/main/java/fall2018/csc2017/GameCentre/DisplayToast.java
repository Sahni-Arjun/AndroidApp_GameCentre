/*
Model class
 */
package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.widget.Toast;

/**
 * A view class that displays toasts to the screen.
 */
public class DisplayToast {

    /**
     * Display a toast
     * @param context the current activity
     * @param text the message to display.
     */
    public void displayToast(Context context, String text){
        Toast.makeText(context, text +
                            "", Toast.LENGTH_SHORT).show();
    }
}
