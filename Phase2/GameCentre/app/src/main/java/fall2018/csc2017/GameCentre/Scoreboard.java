package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Scoreboard which add scores and gets the top number of scores.
 */
class Scoreboard implements Serializable {

    static final long serialVersionUID = 6102199765345718407L;

    /**
     * The priority queue of all scores.
     */
    private PriorityQueue<Score> allScores;

    /**
     * Stores the score and the name of the user who achieved the score.
     */
    private class Score implements Serializable{

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

    /**
     * Compares based on the score value of the score object.
     */
    private class ScoreComparator implements Comparator<Score>, Serializable{
        private static final long serialVersionUID = 6102199765345718477L;

        /**
         * Compare the difference between the two score objects
         *
         * @param o1 score object to compare
         * @param o2 score object to compare
         * @return the difference between the score value of the two score objects
         */
        @Override
        public int compare(Score o1, Score o2) {
            // This order of subtraction makes it a max heap, reverse for min heap
            return o2.getScore() - o1.getScore();
        }
    }

    /**
     * Creates a new empty scoreboard
     */
    Scoreboard() {
        this.allScores = new PriorityQueue<>(new ScoreComparator());
    }

    /**
     * Adds the given score to the scoreboard.
     *
     * @param score the score
     */
    void addToScoreBoard(Score score) {
        this.allScores.add(score);
    }

    /**
     * Return the top number of scores specified by the user.
     *
     * @return the top scores
     */
    StringBuilder getTopScores() {
        StringBuilder topScores = new StringBuilder();
        PriorityQueue<Score> allScoresCopy = new PriorityQueue<>(allScores);
        for (int i = 0; i < 10; i++) {
            Score currScore = allScoresCopy.poll();
            if(currScore != null){
                topScores.append(currScore).append("\n");
            }
        }
        return topScores;
    }

    /**
     * Return a new score object specified by the user's name and score.
     *
     * @param name  the name of the user
     * @param score the score of the user
     * @return the score object with the above parameters
     */
    Score createScore(String name, int score) {
        return new Score(name, score);
    }

    /**
     * Return the latest entered score in the scoreboard.
     * @return the latest score value
     */
    int getLatestScore(){
        Object[] arrayOfScores = this.allScores.toArray();
        Score newScore = (Score) arrayOfScores[arrayOfScores.length - 1];
        return newScore.getScore();
    }
}
