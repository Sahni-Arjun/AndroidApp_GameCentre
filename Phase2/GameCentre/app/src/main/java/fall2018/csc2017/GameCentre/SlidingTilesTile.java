/*
Model
 */
package fall2018.csc2017.GameCentre;

/**
 * Sliding tile tile that has the blank tile representing the last object.
 */
class SlidingTilesTile extends Tile {

    /**
     * Initializes a new tile with a corresponding background.
     * @param backgroundId the background.
     */
    SlidingTilesTile(int backgroundId) {
        super(backgroundId);

        if ((SlidingTileComplexityActivity.complexity *
                SlidingTileComplexityActivity.complexity) == getId()) {
            setBackground(R.drawable.tile_25);
        }
    }
}
