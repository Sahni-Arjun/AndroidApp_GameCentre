package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.ArrayList;

class SaveManager implements Serializable {

    static final long serialVersionUID = 5971302647072124829L;

    /**
     * Permanent save of user's SlidingTiles game.
     */
    private ArrayList<GameState> permaSave = new ArrayList<>();

    /**
     * Autosave of user's SlidingTiles game.
     */
    private ArrayList<GameState> autoSave = new ArrayList<>();

    /**
     * Return the most recent saved game of SlidingTiles.
     *
     * @param saveType whether retrieving from auto or perma save
     * @return most recent saved game
     */
    GameState getLastState(String saveType) {
        if (saveType.equals("auto")) {
            return autoSave.get(autoSave.size() - 1);
        } else {
            return permaSave.get(permaSave.size() - 1);
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
        if (saveType.equals("auto")) {
            autoSave = new ArrayList<>();
            autoSave.addAll(permaSave);
        } else {
            permaSave = new ArrayList<>();
            permaSave.addAll(autoSave);
        }
    }

    /**
     * Erase the auto save.
     */
    void wipeAutoSave() {
        autoSave = new ArrayList<>();
    }

    /**
     * Erase the auto save.
     */
    void wipePermaSave() {
        permaSave = new ArrayList<>();
    }

    /**
     * Remove the last auto save.
     */
    void undo() {
        autoSave.remove(autoSave.size() - 1);
    }

    /**
     * Return the number of moves of the auto or perma save.
     *
     * @param saveType whether returning the auto or perma save
     * @return the number of moves
     */
    int getLength(String saveType) {
        if (saveType.equals("auto")) {
            return autoSave.size();
        } else {
            return permaSave.size();
        }
    }
}
