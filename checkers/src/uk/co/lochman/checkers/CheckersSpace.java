/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.lochman.checkers;

import java.util.Vector;

/**
 *
 * @author Radek
 */
public class CheckersSpace {

    public Node getRoot() {
        Checker state[][] = {
            {new Checker(1), new Checker(1), new Checker(1), new Checker(1)},
            {new Checker(1), new Checker(1), new Checker(1), new Checker(1)},
            {new Checker(1), new Checker(1), new Checker(1), new Checker(1)},
            {null, null, null, null},
            {null, null, null, null},
            {new Checker(-1), new Checker(-1), new Checker(-1), new Checker(-1)},
            {new Checker(-1), new Checker(-1), new Checker(-1), new Checker(-1)},
            {new Checker(-1), new Checker(-1), new Checker(-1), new Checker(-1)}
        };
//        Checker state[][] = {
//            {null, null, null, null},
//            {null, null, null, null},
//            {null, null, null, null},
//            {null, null, null, null},
//            {null, new Checker(1), null, null},
//            {null, null, null, null},
//            {null, new Checker(1), null, null},
//            {null, null, new Checker(-1), null}
//        };
        return new Node(state, null);
    }

    public Vector<Node> getSuccessors(Node parent, int player) {
        Vector<Node> moves = new Vector<Node>();
        Vector<Node> jumps = new Vector<Node>();
        if (player == 1) {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 4; col++) {
                    if (parent.state[row][col] != null && parent.state[row][col].getColor() == player) {
                        jumps.addAll(checkForWhiteJumps(row, col, player, parent));
                        if (parent.state[row][col].isKing()){
                            jumps.addAll(checkForBlackJumps(row, col, player, parent));
                        }
                        if (jumps.isEmpty()) {
                            moves.addAll(checkForWhiteMoves(row, col, parent));
                            if (parent.state[row][col].isKing()){
                                moves.addAll(checkForBlackMoves(row, col, parent));
                            }
                        }

                    }
                }
            }
        } else {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 4; col++) {
                    if (parent.state[row][col] != null && parent.state[row][col].getColor() == player) {
                        jumps.addAll(checkForBlackJumps(row, col, player, parent));
                        if (parent.state[row][col].isKing()){
                            jumps.addAll(checkForWhiteJumps(row, col, player, parent));
                        }
                        if (jumps.isEmpty()) {
                            moves.addAll(checkForBlackMoves(row, col, parent));
                            if (parent.state[row][col].isKing()){
                                moves.addAll(checkForWhiteMoves(row, col, parent));
                            }
                        }
                    }
                }
            }
        }
        if(!jumps.isEmpty()){
            return jumps;
        } else {
            return moves;
        }
    }

    private Vector<Node> checkForWhiteMoves(int row, int col, Node parent) {
        Vector<Node> moves = new Vector<Node>();
        if (row < 7) {
            if (row % 2 == 0) {
                if (col < 3) {
                    if (parent.state[row+1][col] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row+1][col] = state[row][col].getCopy();
                        state[row][col] = null;
                        if(row==6){
                            state[row+1][col].setKing(true);
                        }
                        moves.add(new Node(state, parent));
                    }
                    if (parent.state[row+1][col+1] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row+1][col+1] = state[row][col].getCopy();
                        state[row][col] = null;
                        if(row==6){
                            state[row+1][col+1].setKing(true);
                        }
                        moves.add(new Node(state, parent));
                    }
                } else {
                    if (parent.state[row+1][col] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row+1][col] = state[row][col].getCopy();
                        state[row][col] = null;
                        if(row==6){
                            state[row+1][col].setKing(true);
                        }
                        moves.add(new Node(state, parent));
                    }
                }
            } else {
                if (col > 0) {
                    if (parent.state[row+1][col] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row+1][col] = state[row][col].getCopy();
                        state[row][col] = null;
                        moves.add(new Node(state, parent));
                    }
                    if (parent.state[row+1][col-1] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row+1][col-1] = state[row][col].getCopy();
                        state[row][col] = null;
                        moves.add(new Node(state, parent));
                    }
                } else {
                    if (parent.state[row+1][col] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row+1][col] = state[row][col].getCopy();
                        state[row][col] = null;
                        moves.add(new Node(state, parent));
                    }
                }
            }
        }
        return moves;
    }

    private Vector<Node> checkForBlackMoves(int row, int col, Node parent) {
        Vector<Node> moves = new Vector<Node>();
        if (row > 0) {
            if (row % 2 == 0) {
                if (col < 3) {
                    if (parent.state[row-1][col] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row-1][col] = state[row][col].getCopy();
                        state[row][col] = null;
                        moves.add(new Node(state, parent));
                    }
                    if (parent.state[row-1][col+1] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row-1][col+1] = state[row][col].getCopy();
                        state[row][col] = null;
                        moves.add(new Node(state, parent));
                    }
                } else {
                    if (parent.state[row-1][col] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row-1][col] = state[row][col].getCopy();
                        state[row][col] = null;
                        moves.add(new Node(state, parent));
                    }
                }
            } else {
                if (col > 0) {
                    if (parent.state[row-1][col-1] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row-1][col-1] = state[row][col].getCopy();
                        state[row][col] = null;
                        if(row==1){
                            state[row-1][col-1].setKing(true);
                        }
                        moves.add(new Node(state, parent));
                    }
                    if (parent.state[row-1][col] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row-1][col] = state[row][col].getCopy();
                        state[row][col] = null;
                        if(row==1){
                            state[row-1][col].setKing(true);
                        }
                        moves.add(new Node(state, parent));
                    }
                } else {
                    if (parent.state[row-1][col] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row-1][col] = state[row][col].getCopy();
                        state[row][col] = null;
                        if(row==1){
                            state[row-1][col].setKing(true);
                        }
                        moves.add(new Node(state, parent));
                    }
                }
            }
        }
        return moves;
    }

    private Vector<Node> checkForWhiteJumps(int row, int col, int player, Node parent) {
        Vector<Node> jumps = new Vector<Node>();
        if (row < 6) {
            if (row % 2 == 0) {
                if (col == 0) {
                    if (parent.state[row+1][col+1] != null && parent.state[row+1][col+1].getColor() == -player && parent.state[row+2][col+1] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row+2][col+1] = state[row][col].getCopy();
                        state[row][col] = null;
                        state[row+1][col+1] = null;
                        Vector<Node> longJumps = checkForWhiteJumps(row + 2, col + 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                } else if (col < 3) {
                    if (parent.state[row+1][col+1] != null && parent.state[row+1][col+1].getColor() == -player && parent.state[row+2][col+1] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row+2][col+1] = state[row][col].getCopy();
                        state[row][col] = null;
                        state[row+1][col+1] = null;
                        Vector<Node> longJumps = checkForWhiteJumps(row + 2, col + 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                    if (parent.state[row+1][col] != null && parent.state[row+1][col].getColor() == -player && parent.state[row+2][col-1] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row+2][col-1] = state[row][col].getCopy();
                        state[row][col] = null;
                        state[row+1][col] = null;
                        Vector<Node> longJumps = checkForWhiteJumps(row + 2, col - 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                } else {
                    if (parent.state[row+1][col] != null && parent.state[row+1][col].getColor() == -player && parent.state[row+2][col-1] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row+2][col-1] = state[row][col].getCopy();
                        state[row][col] = null;
                        state[row+1][col] = null;
                        Vector<Node> longJumps = checkForWhiteJumps(row + 2, col - 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                }
            } else {
                if (col == 0) {
                    if (parent.state[row+1][col] != null && parent.state[row+1][col].getColor() == -player && parent.state[row+2][col+1] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row+2][col+1] = state[row][col].getCopy();
                        state[row][col] = null;
                        state[row+1][col] = null;
                        if(row==5){
                            state[row+2][col+1].setKing(true);
                        }
                        Vector<Node> longJumps = checkForWhiteJumps(row + 2, col + 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                } else if (col < 3) {
                    if (parent.state[row+1][col] != null && parent.state[row+1][col].getColor() == -player && parent.state[row+2][col+1] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row+2][col+1] = state[row][col].getCopy();
                        state[row][col] = null;
                        state[row+1][col] = null;
                        if(row==5){
                            state[row+2][col+1].setKing(true);
                        }
                        Vector<Node> longJumps = checkForWhiteJumps(row + 2, col + 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                    if (parent.state[row+1][col-1] != null && parent.state[row+1][col-1].getColor() == -player && parent.state[row+2][col-1] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row+2][col-1] = state[row][col].getCopy();
                        state[row][col] = null;
                        state[row+1][col-1] = null;
                        if(row==5){
                            state[row+2][col-1].setKing(true);
                        }
                        Vector<Node> longJumps = checkForWhiteJumps(row + 2, col - 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                } else {
                    if (parent.state[row+1][col-1] != null && parent.state[row+1][col-1].getColor() == -player && parent.state[row+2][col-1] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row+2][col-1] = state[row][col].getCopy();
                        state[row][col] = null;
                        state[row+1][col-1] = null;
                        if(row==5){
                            state[row+2][col-1].setKing(true);
                        }
                        Vector<Node> longJumps = checkForWhiteJumps(row + 2, col - 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                }
            }
        }
        return jumps;
    }

    private Vector<Node> checkForBlackJumps(int row, int col, int player, Node parent) {
        Vector<Node> jumps = new Vector<Node>();
        if (row > 1) {
            if (row % 2 == 0) {
                if (col == 0) {
                    if (parent.state[row-1][col+1] != null && parent.state[row-1][col+1].getColor() == -player && parent.state[row-2][col+1] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row-2][col+1] = state[row][col].getCopy();
                        state[row][col] = null;
                        state[row-1][col+1] = null;
                        if(row==2){
                            state[row-2][col+1].setKing(true);
                        }
                        Vector<Node> longJumps = checkForBlackJumps(row - 2, col + 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                } else if (col < 3) {
                    if (parent.state[row-1][col+1] != null && parent.state[row-1][col+1].getColor() == -player && parent.state[row-2][col+1] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row-2][col+1] = state[row][col].getCopy();
                        state[row][col] = null;
                        state[row-1][col+1] = null;
                        if(row==2){
                            state[row-2][col+1].setKing(true);
                        }
                        Vector<Node> longJumps = checkForBlackJumps(row - 2, col + 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                    if (parent.state[row-1][col] != null && parent.state[row-1][col].getColor() == -player && parent.state[row-2][col-1] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row-2][col-1] = state[row][col].getCopy();
                        state[row][col] = null;
                        state[row-1][col] = null;
                        if(row==2){
                            state[row-2][col-1].setKing(true);
                        }
                        Vector<Node> longJumps = checkForBlackJumps(row - 2, col - 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                } else {
                    if (parent.state[row-1][col] != null && parent.state[row-1][col].getColor() == -player && parent.state[row-2][col-1] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row-2][col-1] = state[row][col].getCopy();
                        state[row][col] = null;
                        state[row-1][col] = null;
                        if(row==2){
                            state[row-2][col-1].setKing(true);
                        }
                        Vector<Node> longJumps = checkForBlackJumps(row - 2, col - 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                }
            } else {
                if (col == 0) {
                    if (parent.state[row-1][col] != null && parent.state[row-1][col].getColor() == -player && parent.state[row-2][col+1] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row-2][col+1] = state[row][col].getCopy();
                        state[row][col] = null;
                        state[row-1][col] = null;
                        Vector<Node> longJumps = checkForBlackJumps(row - 2, col + 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                } else if (col < 3) {
                    if (parent.state[row-1][col] != null && parent.state[row-1][col].getColor() == -player && parent.state[row-2][col+1] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row-2][col+1] = state[row][col].getCopy();
                        state[row][col] = null;
                        state[row-1][col] = null;
                        Vector<Node> longJumps = checkForBlackJumps(row - 2, col + 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                    if (parent.state[row-1][col-1] != null && parent.state[row-1][col-1].getColor() == -player && parent.state[row-2][col-1] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row-2][col-1] = state[row][col].getCopy();
                        state[row][col] = null;
                        state[row-1][col-1] = null;
                        Vector<Node> longJumps = checkForBlackJumps(row - 2, col - 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                } else {
                    if (parent.state[row-1][col-1] != null && parent.state[row-1][col-1].getColor() == -player && parent.state[row-2][col-1] == null) {
                        Checker state[][] = parent.getStateCopy();
                        state[row-2][col-1] = state[row][col].getCopy();
                        state[row][col] = null;
                        state[row-1][col-1] = null;
                        Vector<Node> longJumps = checkForBlackJumps(row - 2, col - 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                }
            }
        }
        return jumps;
    }

}
