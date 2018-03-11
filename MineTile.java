/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Niki
 */
public class MineTile {
    private boolean mined;
    private int minedNeighbours;
    private boolean revealed;
    private boolean marked;
    
    public MineTile() {
        mined = false;
        minedNeighbours = 0;
        revealed = false;
        marked = false;
                
    }

    public boolean isMined() {
        return mined;
    }

    public int getMinedNeighbours() {
        return minedNeighbours;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMined(boolean mined) {
        this.mined = mined;
    }

    public void setMinedNeighbours(int minedNeighbours) {
        this.minedNeighbours = minedNeighbours;
    }
    
    /**
     * Increment the number of mined neighbours by 1.     * 
     */
    public void incrementMinedNeighbours() {
        this.minedNeighbours = minedNeighbours + 1;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    
    /**
     * Provide the grid representation
     *
     * @return Squares for unrevealed tiles, F for marked tiles, 
     *         stars for revealed mines, ints for adjacent
     */
    @Override
    public String toString() {
        String s = "";
        if (!revealed && !marked) {
            s = Character.toString('\u0890');
        }
        else {
            if (revealed) {
                if (mined) {
                    s = "*";
                } else {
                    s += minedNeighbours;
                }
            } 
                if (marked) {
                    s = "F";
                }
            }
        return s;
        
    }

    
    
    
}
