/*
Model
 */
package fall2018.csc2017.GameCentre;

class SlidingTilesTile extends Tile {

    SlidingTilesTile(int backgroundId) {
        super(backgroundId);

        if ((SlidingTileComplexityActivity.complexity *
                SlidingTileComplexityActivity.complexity) == getId()) {
            setBackground(R.drawable.tile_25);
        }
    }
}
