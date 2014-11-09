/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.lochman.checkers;

/**
 *
 * @author Radek
 */
public class GameManager {
    private final Checker[][] gameBoard;
    
    public GameManager(){
        this.gameBoard = setInitialBoard();
    }
    
    public void printBoard(){
        for (int j = 7; j>=0; j--){
            for (int i = 0; i < 8; i++){
                if(gameBoard[i][j] == null){
                    System.out.print(". ");
                } else if(gameBoard[i][j].getColor().equals("White")){
                    System.out.print("W ");
                } else {
                    System.out.print("B ");
                }
            }
            System.out.println();
        }
    }
    
    public void moveCheckerTo(int fromRow, int fromCol, int toRow, int toCol){
        gameBoard[toRow][toCol] = gameBoard[fromRow][fromCol];
        gameBoard[fromRow][fromCol] = null;
    }
    
    private Checker[][] setInitialBoard(){
        Checker[][] newBoard = {{new Checker("Black"), null, new Checker("Black"), null, null, null, new Checker("White"), null},
                                {null, new Checker("Black"), null, null, null, new Checker("White"), null, new Checker("White")},
                                {new Checker("Black"), null, new Checker("Black"), null, null, null, new Checker("White"), null},
                                {null, new Checker("Black"), null, null, null, new Checker("White"), null, new Checker("White")},
                                {new Checker("Black"), null, new Checker("Black"), null, null, null, new Checker("White"), null},
                                {null, new Checker("Black"), null, null, null, new Checker("White"), null, new Checker("White")},
                                {new Checker("Black"), null, new Checker("Black"), null, null, null, new Checker("White"), null},
                                {null, new Checker("Black"), null, null, null, new Checker("White"), null, new Checker("White")}
                                };
        return newBoard;
    }
}
