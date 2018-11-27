package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

class SaveManager implements Serializable {

    static final long serialVersionUID = 5971302647072124829L;

    static final String slidingTilesName = "sliding tiles";
    static final String sudokuName = "sudoku";
    static final String hangmanName = "hangman";

    static final String auto = "auto";
    static final String perma = "perma";

    public String getContinueOrLoad() {
        return continueOrLoad;
    }

    public void setContinueOrLoad(String continueOrLoad) {
        this.continueOrLoad = continueOrLoad;
    }

    private String continueOrLoad = "auto";

    /**
     * Permanent save of user's games.
     */
    private HashMap<String,ArrayList<GameState>> permaSave = new HashMap<>();

    /**
     * Autosave of user's games.
     */
    private HashMap<String,ArrayList<GameState>> autoSave = new HashMap<>();

    SaveManager(){
        permaSave.put(SaveManager.slidingTilesName, new ArrayList<GameState>());
        permaSave.put(SaveManager.sudokuName, new ArrayList<GameState>());
        permaSave.put(SaveManager.hangmanName, new ArrayList<GameState>());
        autoSave.put(SaveManager.slidingTilesName, new ArrayList<GameState>());
        autoSave.put(SaveManager.sudokuName, new ArrayList<GameState>());
        autoSave.put(SaveManager.hangmanName, new ArrayList<GameState>());
    }

    /**
     * Return the most recent saved game of SlidingTiles.
     *
     * @param saveType whether retrieving from auto or perma save
     * @return most recent saved game
     */
    GameState getLastState(String saveType, String gameType) {
        if (saveType.equals(auto)) {
            int numAuto = autoSave.get(gameType).size();
            return autoSave.get(gameType).get(numAuto - 1);
        } else {
            int numAuto = permaSave.get(gameType).size();
            return permaSave.get(gameType).get(numAuto - 1);
        }
    }

    /**
     * Update the autoSave to the most recent save state.
     *
     * @param state The new state to add
     */
    void addState(GameState state, String gameType) {
        autoSave.get(gameType).add(state);
    }

    /**
     * Erase the perma or auto save.
     *
     * @param saveType whether erasing the perma or auto save
     */
    void updateSave(String saveType, String gameType) {
        if (saveType.equals(auto)) {
            autoSave.put(gameType, new ArrayList<GameState>());
            autoSave.get(gameType).addAll(permaSave.get(gameType));
        } else {
            permaSave.put(gameType, new ArrayList<GameState>());
            permaSave.get(gameType).addAll(autoSave.get(gameType));
        }
    }

    void wipeSave(String saveType, String gameType) {
        if (saveType.equals(auto)) {
            autoSave.put(gameType, new ArrayList<GameState>());
        } else {
            permaSave.put(gameType, new ArrayList<GameState>());
        }
    }

    /**
     * Remove the last auto save.
     */
    void undo(String gameType) {
        int numAuto = autoSave.get(gameType).size();
        autoSave.get(gameType).remove(numAuto - 1);
    }

    /**
     * Return the number of moves of the auto or perma save.
     *
     * @param saveType whether returning the auto or perma save
     * @return the number of moves
     */
    int getLength(String saveType, String gameType) {
        if (saveType.equals(auto)) {
            return autoSave.get(gameType).size();
        } else {
            return permaSave.get(gameType).size();
        }
    }

    void updateState(String gameType, BoardManager boardManager){
        if (gameType.equals(slidingTilesName)){
            SlidingTilesState lastAutoState = (SlidingTilesState) this.getLastState("auto", SaveManager.slidingTilesName);
            int numMoves = this.getLength("auto", SaveManager.slidingTilesName);

            //Creating new game state with field values of the previous state.
            SlidingTilesState newState = new SlidingTilesState((SlidingTilesBoardManager) boardManager, numMoves,
                    SlidingTileComplexityActivity.complexity, SetUndoActivity.undo,
                    lastAutoState.getNumMovesUndone(), lastAutoState.getUnlimitedUndo());
            this.addState(newState, SaveManager.slidingTilesName);
        } else if(gameType.equals(sudokuName)){

        }
    }

    // TODO make this general for all games?
    boolean undoMove(){
        boolean canUndo = getLastState(SaveManager.auto, SaveManager.slidingTilesName).canUndo();
        SlidingTilesState currentAutoState = (SlidingTilesState) getLastState("auto", SaveManager.slidingTilesName);

        if ((getLength(SaveManager.auto, SaveManager.slidingTilesName) != 1) && canUndo) {
            int prevMovesUndone = currentAutoState.getNumMovesUndone();
            undo(SaveManager.slidingTilesName);
            getLastState(SaveManager.auto, SaveManager.slidingTilesName).incrementNumMoves(prevMovesUndone);
            return true;
        }
        return false;
    }

    // TODO make this general for all games.
    Tile[][] getboardArrangement(){
        // store the previous save in a variable.
        SlidingTilesState prevState;
        prevState = (SlidingTilesState) getLastState(SaveManager.auto, SaveManager.slidingTilesName);

        // from the previous save get the tile arrangement.
        return prevState.getSlidingTilesBoardManager().getBoard().getTiles();
    }


    Letter[][] getWordArrangement(){
        // store the previous save in a variable.
        HangmanState prevState;
        prevState = (HangmanState) getLastState(SaveManager.auto, SaveManager.hangmanName);

        // from the previous save get the word arrangement.
        return prevState.getWordManager().getWord().getLetters();
    }




}
