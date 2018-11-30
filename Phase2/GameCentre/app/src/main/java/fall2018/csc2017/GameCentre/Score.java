/*
Model
 */
package fall2018.csc2017.GameCentre;

import java.io.Serializable;

/**
 * Stores the score and the name of the user who achieved the score.
 */
public class Score implements Serializable {

    static final long serialVersionUID = 6102199765345718887L;

    /**
     * The name of the user.
     */
    private String name;

    /**
     * The score of the user.
     */
    private int score;

    /**
     * Constructs new score object with name and score.
     *
     * @param name  the name of the user
     * @param score the score of said user
     */
    Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Return the name of the user associated with this score
     *
     * @return the name of said user
     */
    public String getName() {
        return name;
    }

    /**
     * Return the score
     *
     * @return the score
     */
    int getScore() {
        return score;
    }

    @Override
    public String toString(){
        return this.getScore() + " " +  this.getName();
    }
}