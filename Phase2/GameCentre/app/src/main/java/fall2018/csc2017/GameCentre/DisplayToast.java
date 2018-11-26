package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.widget.Toast;

public class DisplayToast {
    public void displayToast(Context context, String text){
        Toast.makeText(context, text +
                            "", Toast.LENGTH_SHORT).show();
    }
}
