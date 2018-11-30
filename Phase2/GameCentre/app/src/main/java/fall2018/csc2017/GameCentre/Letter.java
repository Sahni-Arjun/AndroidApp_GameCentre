package fall2018.csc2017.GameCentre;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * A Letter in a Hangman Word
 */
public class Letter implements Comparable<Letter>, Serializable {

    /**
     * The background id to find the letter image.
     */
    private int background;

    /**
     * The unique id.
     */
    private int id;

    /**
     * Whether this letter's current background should be shown.
     */
    boolean hidden;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Return the letter id.
     *
     * @return the letter id
     */
    public int getId() {
        return id;
    }

    /**
     * A letter with a background id; look up and set the id.
     *
     * @param backgroundId backgroundID of the tile.
     */
    Letter(int backgroundId) {
        id = backgroundId;
        background = R.drawable.letter_empty;
        hidden = true;
    }

    @Override
    public int compareTo(@NonNull Letter o) {
        return o.id - this.id;
    }
}
