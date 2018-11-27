package fall2018.csc2017.GameCentre;

public class SlidingTilesTile extends Tile {

    SlidingTilesTile(int id, int background) {
        super(id, background);

    }

    SlidingTilesTile(int backgroundId) {
        super(backgroundId);

        if ((SlidingTileComplexityActivity.complexity *
                SlidingTileComplexityActivity.complexity) == getId()) {
            setBackground(R.drawable.tile_25);
            return;
        }
    }
}
