/*
Controller
 */
package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.ArrayList;

class SaveManager implements Serializable {

    static final long serialVersionUID = 5971302647072124829L;

    static final String slidingTilesName = "sliding tiles";
    static final String sudokuName = "sudoku";
    static final String hangmanName = "hangman";

    static final String auto = "auto";
    static final String perma = "perma";

    String getContinueOrLoad() {
        return continueOrLoad;
    }

    void setContinueOrLoad(String continueOrLoad) {
        this.continueOrLoad = continueOrLoad;
    }

    private String continueOrLoad = "auto";

    /**
     * Permanent save of user's games.
     */
    private ArrayList<GameState> permaSave;

    /**
     * Autosave of user's games.
     */
    private ArrayList<GameState> autoSave;

    SaveManager(){
        permaSave = new ArrayList<>();
        autoSave = new ArrayList<>();
    }

    /**
     * Return the most recent saved game of SlidingTiles.
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
     * @param saveType whether erasing the perma or auto save
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

    void updateState(String gameType, GameManager gameManager){
        if (gameType.equals(slidingTilesName)){
            SlidingTilesState lastAutoState = (SlidingTilesState) this.getLastState(SaveManager.auto);
            int numMoves = this.getLength(SaveManager.auto);

            //Creating new game state with field values of the previous state.
            SlidingTilesState newState = new SlidingTilesState((SlidingTilesBoardManager) gameManager, numMoves,
                    SlidingTileComplexityActivity.complexity, SetUndoActivityController.undo,
                    lastAutoState.getNumMovesUndone(), lastAutoState.getUnlimitedUndo());
            this.addState(newState);
        }

        if (gameType.equals(hangmanName)){

            if(autoSave.size() == 0){

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
        }else if(gameType.equals(sudokuName)){
            SudokuState lastAutoState = (SudokuState) this.getLastState(SaveManager.auto);
            int numMoves = this.getLength(SaveManager.auto);
            long lastTime = lastAutoState.getTime();

            //Creating new game state with field values of the previous state.
            SudokuState newState = new SudokuState((SudokuBoardManager) gameManager, numMoves,
                    lastAutoState.getDifficulty(), SetUndoActivityController.undo,
                    lastAutoState.getNumMovesUndone(), lastAutoState.getUnlimitedUndo(),
                    lastTime);
            this.addState(newState);
        }
    }

    void updateSudokuTime(long startTime){ // todo delete?
        SudokuState lastAutoState = (SudokuState) this.getLastState(SaveManager.auto);
        lastAutoState.setTime(lastAutoState.getTime() + System.currentTimeMillis() - startTime);
    }

    // TODO make this general for all games?
    boolean undoMove(String gameType){

        if (gameType.equals(slidingTilesName)) {
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

        if (gameType.equals(hangmanName)) {
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

        else {
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

    // TODO make this general for all games.
    Tile[][] getboardArrangement(){
        // store the previous save in a variable.
        SlidingTilesState prevState;
        prevState = (SlidingTilesState) getLastState(SaveManager.auto);

        // from the previous save get the tile arrangement.
        return prevState.getSlidingTilesBoardManager().getBoard().getTiles();
    }


    Letter[][] getWordArrangement(){
        // store the previous save in a variable.
        HangmanState prevState;
        prevState = (HangmanState) getLastState(SaveManager.auto);

        // from the previous save get the word arrangement.
        return prevState.getWordManager().getWord().getLetters();
    }




}
