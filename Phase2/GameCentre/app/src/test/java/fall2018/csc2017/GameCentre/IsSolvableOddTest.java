//package fall2018.csc2017.GameCentre;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//
//public class IsSolvableOddTest {
//
//    /**
//     * Board for this test.
//     */
//    Board board;
//
//    /**
//     * Create an odd sized board which is solvable.
//     */
//    private void solvableOddBoard() {
//        List<Tile> tilesOddBoardEvenInversions = new ArrayList<>();
//        tilesOddBoardEvenInversions.add(new Tile(0));
//        tilesOddBoardEvenInversions.add(new Tile(1));
//        tilesOddBoardEvenInversions.add(new Tile(2));
//        tilesOddBoardEvenInversions.add(new Tile(3));
//        tilesOddBoardEvenInversions.add(new Tile(4));
//        tilesOddBoardEvenInversions.add(new Tile(5));
//        tilesOddBoardEvenInversions.add(new Tile(6));
//        tilesOddBoardEvenInversions.add(new Tile(8));
//        tilesOddBoardEvenInversions.add(new Tile(7));
//        this.board = new Board(tilesOddBoardEvenInversions);
//    }
//
//    /**
//     * Create an unsolvable odd board.
//     */
//    private void unsolvableOddBoard() {
//        List<Tile> tilesOddBoardOddInversions = new ArrayList<>();
//        tilesOddBoardOddInversions.add(new Tile(8));
//        tilesOddBoardOddInversions.add(new Tile(1));
//        tilesOddBoardOddInversions.add(new Tile(2));
//        tilesOddBoardOddInversions.add(new Tile(3));
//        tilesOddBoardOddInversions.add(new Tile(4));
//        tilesOddBoardOddInversions.add(new Tile(5));
//        tilesOddBoardOddInversions.add(new Tile(6));
//        tilesOddBoardOddInversions.add(new Tile(7));
//        tilesOddBoardOddInversions.add(new Tile(0));
//        this.board = new Board(tilesOddBoardOddInversions);
//    }
//
//    /**
//     * Create an already solved odd board.
//     */
//    private void solvedOddBoard(){
//        List<Tile> tilesSolved = new ArrayList<>();
//        tilesSolved.add(new Tile(0));
//        tilesSolved.add(new Tile(1));
//        tilesSolved.add(new Tile(2));
//        tilesSolved.add(new Tile(3));
//        tilesSolved.add(new Tile(4));
//        tilesSolved.add(new Tile(5));
//        tilesSolved.add(new Tile(6));
//        tilesSolved.add(new Tile(7));
//        tilesSolved.add(new Tile(8));
//        this.board = new Board(tilesSolved);
//    }
//
//    /**
//     * Set the complexity for the test.
//     */
//    @Before
//    public void setComplexity(){
//        SlidingTileComplexityActivity.complexity = 3;
//    }
//
//    /**
//     * Test if the odd sized board is solvable.
//     */
//    @Test
//    public void testIsSolvableOddSizedBoard(){
//        solvableOddBoard();
//        assertEquals(true, this.board.isSolvable());
//        unsolvableOddBoard();
//        assertEquals(false, this.board.isSolvable());
//        solvedOddBoard();
//        assertEquals(true, this.board.isSolvable());
//    }
//}
