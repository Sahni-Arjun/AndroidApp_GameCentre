package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.widget.Button;

import java.util.ArrayList;

public class NotRobotActivityController {
    NotRobotActivityController(){}
    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    void createTileButtons(Context context, SlidingTilesBoardManager manager, ArrayList<Button> buttons) {
        Board board = manager.getBoard();
        for (int row = 0; row != Board.numRows; row++) {
            for (int col = 0; col != Board.numCols; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                buttons.add(tmp);
            }
        }
    }
    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    void updateTileButtons(SlidingTilesBoardManager manager, ArrayList<Button> buttons) {
        Board board = manager.getBoard();
        int nextPos = 0;

        for (Button b : buttons) {
            int row = nextPos / Board.numRows;
            int col = nextPos % Board.numCols;
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
    }

}
