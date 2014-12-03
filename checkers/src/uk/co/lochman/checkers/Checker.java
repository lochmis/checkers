/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.lochman.checkers;

/**
 *
 * @author radek
 */
public class Checker {

    private boolean king = false;
    private final int color;

    public Checker(int color) {
        this.color = color;
    }

    public boolean isKing() {
        return king;
    }

    public void setKing(boolean king) {
        this.king = king;
    }

    public int getColor() {
        return color;
    }

    public Checker getCopy() {
        Checker newChecker = new Checker(color);
        newChecker.setKing(king);
        return newChecker;
    }
}
