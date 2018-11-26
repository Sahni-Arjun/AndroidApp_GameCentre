package fall2018.csc2017.GameCentre;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CreateBoard {

    private SudokuBoard board;
    private Tile[][] tiles;


    CreateBoard() {
        Tile[][] tiles = new Tile[9][9];
        final int numTiles = 81;
        for (int tileNum = 0; tileNum != 9; tileNum++) {
            for (int num = 0; num != 9; num++) {
                tiles[tileNum][num] = new Tile(0);
            }

        }
        int i = 0;
        int j = 0;
        boolean notFinished = true;
        while (notFinished) {
            ArrayList<Integer> available = getAvailable(i,j);
            Random rand = new Random();
            int randomElement = available.get(rand.nextInt(available.size()));
            if(!available.isEmpty()){
                tiles[i][j] = new Tile(randomElement);
                if(j<8){ j++;}
                else{i++;}

            }
            else{
                if(j >= 2){j = j-2;}
                else{i = i-1;}
            }
            if(i*j>64){notFinished = false;}





        }
    }

    private ArrayList<Integer> getNeighbours(int i, int j) {
        Tile mainTile = tiles[i][j];
        ArrayList<Integer> neighbours = new ArrayList<>();
        for (int x = 0; x != 9; x++) {
            if (x != i) {
                neighbours.add(tiles[x][j].getBackground());
            }
        }
        for (int x = 0; x != 9; x++) {
            if (x != j) {
                neighbours.add(tiles[i][x].getBackground());
            }
        }
        int i3 = i / 3;
        int j3 = j / 3;
        for (int x = i3 * 3; x != i3 * 3 + 2; x++) {
            for (int y = j3 * 3; y != j3 * 3 + 2; y++) {
                if ((x != i) && (y != j)) {
                    neighbours.add(tiles[x][y].getBackground());
                }
            }
        }
        return neighbours;
    }

    private ArrayList<Integer> getAvailable(int i, int j) {
        ArrayList<Integer> available = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        ArrayList<Integer> neighbours = getNeighbours(i,j);
        for(Integer x: neighbours){
            if (available.contains(x)){
                available.remove(x);
            }

        }
        return available;
    }
}


