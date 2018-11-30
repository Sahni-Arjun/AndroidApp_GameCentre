package fall2018.csc2017.GameCentre;

abstract class BoardManager extends GameManager {
    abstract boolean isValidTap(int position);
    abstract void touchMove(int position);
}
