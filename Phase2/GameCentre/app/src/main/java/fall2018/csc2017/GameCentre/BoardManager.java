package fall2018.csc2017.GameCentre;

abstract class BoardManager {
    abstract boolean isValidTap(int position);
    abstract void touchMove(int position);
    abstract boolean puzzleSolved();
}
