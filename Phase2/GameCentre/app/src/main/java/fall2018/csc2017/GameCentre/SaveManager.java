/*
Controller
 */
package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A save manager that modifies game states.
 */
class SaveManager implements Serializable {

    static final long serialVersionUID = 5971302647072124829L;

    /**
     * The name of each game.
     */
    static final String slidingTilesName = "sliding tiles";
    static final String sudokuName = "sudoku";
    static final String hangmanName = "hangman";

    /**
     * The two saving types of each game.
     */
    static final String auto = "auto";
    static final String perma = "perma";

    /**
     * Returning the user's most recent button press.
     * @return whether to load save or continue the auto save.
     */
    String getContinueOrLoad() {
        return continueOrLoad;
    }

    /**
     * Setting whether the user would like to load a save or continue.
     * @param continueOrLoad whether to load save or continue the auto save.
     */
    void setContinueOrLoad(String continueOrLoad) {
        this.continueOrLoad = continueOrLoad;
    }

    /**
     * Represents the user's choice of whether to load save or continue the auto save.
     */
    private String continueOrLoad = "auto";

    /**
     * Permanent save of user's games.
     */
    private ArrayList<GameState> permaSave;

    /**
     * Autosave of user's games.
     */
    private ArrayList<GameState> autoSave;

    /**
     * Creates a new Savemanager with no saves.
     */
    SaveManager() {
        permaSave = new ArrayList<>();
        autoSave = new ArrayList<>();
    }

    /**
     * Return the most recent saved game.
     *
     * @param saveType whether retrieving from auto or perma save
     * @return most recent saved game
     */
    GameState getLastState(String saveType) {
        if (saveType.equals(auto)) {
            int numAuto = autoSave.size();
            return autoSave.get(numAuto - 1);
        } else {
            int numAuto = permaSave.size();
            return permaSave.get(numAuto - 1);
        }
    }

    /**
     * Update the autoSave to the most recent save state.
     *
     * @param state The new state to add
     */
    void addState(GameState state) {
        autoSave.add(state);
    }

    /**
     * Erase the perma or auto save.
     *
     * @param saveType whether updating the perma or auto save
     */
    void updateSave(String saveType) {
        if (saveType.equals(auto)) {
            autoSave = new ArrayList<>();
            autoSave.addAll(permaSave);
        } else {
            permaSave = new ArrayList<>();
            permaSave.addAll(autoSave);
        }
    }

    /**
     * Wiping every saved state of the game. (Either perma or auto)
     * @param saveType whether wiping perma or auto save.
     */
    void wipeSave(String saveType) {
        if (saveType.equals(auto)) {
            autoSave = new ArrayList<>();
        } else {
            permaSave = new ArrayList<>();
        }
    }

    /**
     * Remove the last auto save.
     */
    void undo() {
        int numAuto = autoSave.size();
        autoSave.remove(numAuto - 1);
    }

    /**
     * Return the number of moves of the auto or perma save.
     *
     * @param saveType whether returning the auto or perma save
     * @return the number of moves
     */
    int getLength(String saveType) {
        if (saveType.equals(auto)) {
            return autoSave.size();
        } else {
            return permaSave.size();
        }
    }

    /**
     * Calculate and return the score based on number of moves taken.
     *
     * @return score of this SlidingTilesState.
     */
    int getFinalScore(String gameType) {
        switch (gameType) {
            case SaveManager.slidingTilesName: {
                int complexity = ((SlidingTilesState) getLastState(SaveManager.auto)).getComplexity();
                int numMoves = ((SlidingTilesState) getLastState(SaveManager.auto)).getNumMoves();

                if (complexity == 3) {
                    return (int) (Math.round((500 * Math.exp(-(double) numMoves / 35))));
                } else if (complexity == 4) {
                    return (int) (Math.round((1000 * Math.exp(-(double) numMoves / 20))));
                } else {
                    return (int) (Math.round((3000 * Math.exp(-(double) numMoves / 100))));
                }
            }
            case SaveManager.sudokuName:
                int difficulty = ((SudokuState) getLastState(SaveManager.auto)).getDifficulty();
                long time = ((SudokuState) getLastState(SaveManager.auto)).getTime();

                if (difficulty == 1) {
                    // Expecting a completion time of 2 mins
                    return (int) (Math.round((500 * Math.exp(-(double) (time / 1000) / 100))));
                } else if (difficulty == 2) {
                    // Expecting a completion time of 15 mins
                    return (int) (Math.round((1000 * Math.exp(-(double) (time / 1000) / 400))));
                } else {
                    // Expecting a completion time of 30 mins
                    return (int) (Math.round((3000 * Math.exp(-(double) (time / 1000) / 600))));
                }
            default: { // gameType.equals(SaveManager.hangMan)
                int complexity = ((HangmanState) getLastState(SaveManager.auto)).getComplexity();
                long numMoves = ((HangmanState) getLastState(SaveManager.auto)).getNumMoves();

                if (complexity == 3) {
                    return (int) (Math.round((500 * Math.exp(-(double) numMoves / 35))));
                } else if (complexity == 4) {
                    return (int) (Math.round((1000 * Math.exp(-(double) numMoves / 20))));
                } else {
                    return (int) (Math.round((3000 * Math.exp(-(double) numMoves / 100))));
                }
            }
        }
    }

    /**
     * Updating the most recent move made by the player
     * @param gameType the game played
     * @param gameManager the state of the game
     */
    void updateState(String gameType, GameManager gameManager) {
        switch (gameType) {
            case slidingTilesName: {
                SlidingTilesState lastAutoState = (SlidingTilesState) this.getLastState(SaveManager.auto);
                int numMoves = this.getLength(SaveManager.auto);

                //Creating new game state with field values of the previous state.
                SlidingTilesState newState = new SlidingTilesState((SlidingTilesBoardManager) gameManager, numMoves,
                        SlidingTileComplexityActivity.complexity, SetUndoActivityController.undo,
                        lastAutoState.getNumMovesUndone(), lastAutoState.getUnlimitedUndo());
                this.addState(newState);
                break;
            }
            case hangmanName: {
                if (autoSave.size() == 0) {

                    HangmanState newState = new HangmanState((WordManager) gameManager, 0,
                            HangmanComplexityActivity.complexity, 10,
                            0, true); // todo update
                    this.addState(newState);
                }
                HangmanState lastAutoState = (HangmanState) this.getLastState(SaveManager.auto);
                int numMoves = this.getLength(SaveManager.auto);

                //Creating new game state with field values of the previous state.
                HangmanState newState = new HangmanState((WordManager) gameManager, numMoves,
                        HangmanComplexityActivity.complexity, SetUndoActivityController.undo,
                        lastAutoState.getNumMovesUndone(), lastAutoState.getUnlimitedUndo());
                this.addState(newState);
                break;
            }
            case sudokuName: {
                SudokuState lastAutoState = (SudokuState) this.getLastState(SaveManager.auto);
                int numMoves = this.getLength(SaveManager.auto);
                long lastTime = lastAutoState.getTime();

                //Creating new game state with field values of the previous state.
                SudokuState newState = new SudokuState((SudokuBoardManager) gameManager, numMoves,
                        lastAutoState.getDifficulty(), SetUndoActivityController.undo,
                        lastAutoState.getNumMovesUndone(), lastAutoState.getUnlimitedUndo(),
                        lastTime);
                this.addState(newState);
                break;
            }
        }
    }

    /**
     * Setting the amount of time that has passed in a sudoku game.
     * @param startTime the time of the last save.
     */
    void updateSudokuTime(long startTime) {
        SudokuState lastAutoState = (SudokuState) this.getLastState(SaveManager.auto);
        lastAutoState.setTime(lastAutoState.getTime() + System.currentTimeMillis() - startTime);
    }

    /**
     * Going back to a previous saved game state.
     *
     * @param gameType the game in question.
     * @return whether an undo was sucessfully made.
     */
    boolean undoMove(String gameType) {

        switch (gameType) {
            case slidingTilesName: {
                boolean canUndo = getLastState(SaveManager.auto).canUndo();
                SlidingTilesState currentAutoState = (SlidingTilesState) getLastState(SaveManager.auto);

                if ((getLength(SaveManager.auto) != 1) && canUndo) {
                    int prevMovesUndone = currentAutoState.getNumMovesUndone();
                    undo();
                    getLastState(SaveManager.auto).incrementNumMoves(prevMovesUndone);
                    return true;
                }
                return false;
            }
            case hangmanName: {
                boolean canUndo = getLastState(SaveManager.auto).canUndo();
                HangmanState currentAutoState = (HangmanState) getLastState(SaveManager.auto);

                if ((getLength(SaveManager.auto) != 1) && canUndo) {
                    int prevMovesUndone = currentAutoState.getNumMovesUndone();
                    undo();
                    getLastState(SaveManager.auto).incrementNumMoves(prevMovesUndone);
                    return true;
                }
                return false;
            }
            default: {
                boolean canUndo = getLastState(SaveManager.auto).canUndo();
                SudokuState currentAutoState = (SudokuState) getLastState(SaveManager.auto);

                if ((getLength(SaveManager.auto) != 1) && canUndo) {
                    int prevMovesUndone = currentAutoState.getNumMovesUndone();
                    undo();
                    getLastState(SaveManager.auto).incrementNumMoves(prevMovesUndone);
                    return true;
                }
                return false;
            }
        }

    }

    /**
     * Return the tile arrangement of SlidingTiles.
     * @return the tile arrangement
     */
    Tile[][] getboardArrangement() {
        // store the previous save in a variable.
        SlidingTilesState prevState;
        prevState = (SlidingTilesState) getLastState(SaveManager.auto);

        // from the previous save get the tile arrangement.
        return prevState.getSlidingTilesBoardManager().getBoard().getTiles();
    }


    /**
     * Return the letter arangement of Hangman
     * @return the letter arrangement.
     */
    Letter[][] getWordArrangement() {
        // store the previous save in a variable.
        HangmanState prevState;
        prevState = (HangmanState) getLastState(SaveManager.auto);

        // from the previous save get the word arrangement.
        return prevState.getWordManager().getWord().getLetters();
    }


}
