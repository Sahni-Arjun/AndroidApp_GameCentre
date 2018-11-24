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
    public boolean hidden;

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
     * A Letter with id and background. The background may not have a corresponding image.
     *
     * @param id         the id
     * @param background the background
     */
    Letter(int id, int background) {
        this.id = id;
        this.background = background;
        this.hidden = true;
    }

    @Override
    public int compareTo(@NonNull Letter o) {
        return o.id - this.id;
    }

    /**
     * A letter with a background id; look up and set the id.
     *
     * @param backgroundId backgroundID of the tile.
     */
    Letter(int backgroundId) {
        id = backgroundId;

        switch (backgroundId) {
            case 0:
                background = R.drawable.letter_empty;
                break;
            case 65:
                background = R.drawable.letter_a;
                break;
            case 66:
                background = R.drawable.letter_b;
                break;
            case 67:
                background = R.drawable.letter_c;
                break;
            case 68:
                background = R.drawable.letter_d;
                break;
            case 69:
                background = R.drawable.letter_e;
                break;
            case 70:
                background = R.drawable.letter_f;
                break;
            case 71:
                background = R.drawable.letter_g;
                break;
            case 72:
                background = R.drawable.letter_h;
                break;
            case 73:
                background = R.drawable.letter_i;
                break;
            case 74:
                background = R.drawable.letter_j;
                break;
            case 75:
                background = R.drawable.letter_k;
                break;
            case 76:
                background = R.drawable.letter_l;
                break;
            case 77:
                background = R.drawable.letter_m;
                break;
            case 78:
                background = R.drawable.letter_n;
                break;
            case 79:
                background = R.drawable.letter_o;
                break;
            case 80:
                background = R.drawable.letter_p;
                break;
            case 81:
                background = R.drawable.letter_q;
                break;
            case 82:
                background = R.drawable.letter_r;
                break;
            case 83:
                background = R.drawable.letter_s;
                break;
            case 84:
                background = R.drawable.letter_t;
                break;
            case 85:
                background = R.drawable.letter_u;
                break;
            case 86:
                background = R.drawable.letter_v;
                break;
            case 87:
                background = R.drawable.letter_w;
                break;
            case 88:
                background = R.drawable.letter_x;
                break;
            case 89:
                background = R.drawable.letter_y;
                break;
            case 90:
                background = R.drawable.letter_z;
                break;
            default:
                background = R.drawable.letter_empty;
        }
    }
}
