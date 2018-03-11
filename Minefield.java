/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Niki
 */
public class Minefield {

    private MineTile[][] minefield;
    private int rows, columns, mines;

    /**
     * Initialize the fields, and then populate the minefield
     *
     * @param rows
     * @param columns
     * @param numMines
     */
    public Minefield(int rows, int columns, int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
        minefield = new MineTile[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                minefield[i][j] = new MineTile();
            }
        }
    }

    /**
     * Mine a particular tile.
     *
     * @param row
     * @param column
     * @return true if mined; false if already mined
     */
    public boolean mineTile(int row, int column) {
        if (minefield[row][column].isMined()) {
            return false;
        }
            minefield[row][column].setMined(true);
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = column - 1; j <= column + 1; j++) {
                    if (i >= 0 && i < rows && j >= 0 && j < columns) {
                        minefield[i][j].incrementMinedNeighbours();
                    }
                }
            }
            return true;
        }


    public MineTile getMineTile(int row, int col) {
        return minefield[row][col];
    }
    
    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getMines() {
        return mines;
    }
    

    public MineTile[][] getMinefield() {
        return minefield;
    }

    /**
     * Provide the grid representation
     *
     * @return Squares for unrevealed tiles, F for marked tiles, stars for
     * revealed mines, ints for adjacent
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                s += minefield[i][j].toString();
            }
            s += "\n";
        }

        return s;
    }

    /**
     * Populate randomly the minefield with the necessary number of mines
     *
     */
    public void populate() {
        int created = 0;
        while (created < mines) {
            int row = (int) (Math.random() * this.rows);
            int col = (int) (Math.random() * this.columns);
            if (!minefield[row][col].isMined() && !(row == 0 && col == 0)) {
                mineTile(row, col);
                created++;
            }

        }
    }

    /**
     * Mark a tile as containing a mine, or unmark it
     *
     * @param row
     * @param col
     */
    public void markTile(int row, int col) {
        if (!minefield[row][col].isRevealed()) {
            if (minefield[row][col].isMarked()) {
                minefield[row][col].setMarked(false);
            } else {
                minefield[row][col].setMarked(true);
            }
        }
    }
    
        /**
         * Step on a tile (revealing it) and recursively step on surrounding
         * tiles if necessary
         *
         * @param row
         * @param column
         * @return false if tile contains a mine, true otherwise
         */
    public boolean step(int row, int column) {
        if (minefield[row][column].isMined()) {
            minefield[row][column].setRevealed(true);
            return false;
        } else {
                minefield[row][column].setRevealed(true);
                if (minefield[row][column].getMinedNeighbours() == 0) {
                for (int i = row - 1; i <= row + 1; i++) {
                    for (int j = column - 1; j <= column + 1; j++) {
                        if (i >= 0 && i < rows && j >= 0 && j < columns) {
                            if (!minefield[i][j].isRevealed() && !minefield[i][j].isMined() && !minefield[i][j].isMarked()) {
                                step(i, j);
                            }
                        }
                    }
                }
            } 
            return true;
        }
    }

    /**
     * Show if the minefield has a revealed bomb
     *
     * @return true if yes, false otherwise
     */
    public boolean revealedBomb() {
        boolean revealedBomb = false;
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                if (minefield[i][j].isRevealed() && minefield[i][j].isMined()) {
                    revealedBomb = true;
                }
            }
        }
        return revealedBomb;
    }

    /**
     * Show if all mines are revealed
     *
     * @return true if yes, false otherwise
     */
    public boolean allMinesRevealed() {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((!minefield[i][j].isMined() && minefield[i][j].isMarked())
                        || (minefield[i][j].isMined() && !minefield[i][j].isMarked())) {
                    return false;
                }
            }
        }
        return true;
    }

}
