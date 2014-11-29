package uk.co.lochman.checkers;

import java.util.Vector;

/**
 *
 * @author Radek
 */
public class CheckersSearch {

    CheckersSpace space = new CheckersSpace();

    String name(int player) {
        String s = " ";
        if (player == 1) {
            s = "W";
        } else if (player == -1) {
            s = "B";
        }
        return s;
    }

    void pr(String s) {
        System.out.print(s);
    }

    void printState(Node node) { /* print board state */

        System.out.println("\n");
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 4; c++) {
                if (node.state[r][c] != null){
                    int player = node.state[r][c].getColor();
                    if (node.state[r][c].isKing()){
                        pr(" ." + name(player) + "K");
                    } else {
                        pr(" . " + name(player));
                    }
                } else {
                    pr(" .  ");
                }
            }
            pr("\n");
            r++;
            for (int c = 0; c < 4; c++) {
                if (node.state[r][c] != null){
                    int player = node.state[r][c].getColor();
                    if (node.state[r][c].isKing()){
                        pr(name(player) + "K .");
                    } else {
                        pr(" " + name(player) + " .");
                    }
                } else {
                    pr("   .");
                }                
            }
            pr("\n");
        }
        pr("\n");
    }

    /**
     * decide whether board state s is won for player p
     */
    boolean wonFor(Checker[][] state, int player) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 4; col++){
                if (state[row][col] != null && state[row][col].getColor() == -player) {
                    return false;
                }
            }
        }
        return true;
    }

    int winnerOf(Checker[][] state) {
        int player = 0;
        if (wonFor(state, 1)) {
            player = 1;
        } else if (wonFor(state, -1)) {
            player = -1;
        }
        return player;
    }
    
    int evaluateState(Checker[][] state) {
        int whiteNumber = 0;
        int blackNumber = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 4; col++){
                if (state[row][col] != null) {
                    if (state[row][col].getColor() == 1){
                        whiteNumber++;
                        if (state[row][col].isKing()){
                            whiteNumber += 2;
                        }
                    }
                    if (state[row][col].getColor() == -1){
                        blackNumber++;
                        if (state[row][col].isKing()){
                            blackNumber += 2;
                        }
                    }
                }
            }
        }
        if (whiteNumber == 0) {
            return -100;
        } else if (blackNumber == 0) {
            return 100;
        } else {
            return whiteNumber - blackNumber;
        }
    }

    int evaluate(Node node, int player, int depth, int alpha, int beta) {

        if (depth < 1) {
            return player * evaluateState(node.state);
        }
        int bestValue = -100;
        Vector<Node> successors = space.getSuccessors(node, -player);
        if (successors.isEmpty()) {
            return 100;
        }
        for (Node successor : successors) {
            successor.evaluation = evaluate(successor, -player, depth - 1, -alpha, -beta);
            if (-successor.evaluation > bestValue) {
                bestValue = -successor.evaluation;
            }
            if (-successor.evaluation > alpha) {
                alpha = -successor.evaluation;
            }
            if (alpha >= beta){
                return alpha;
            }
        }
        return bestValue;
    }

    void run() {
        int p = 1;
        int player = 1;
        Node node = space.getRoot();
        Vector<Node> bestNodes = new Vector<Node>();
        printState(node);

        while ((p = winnerOf(node.state)) == 0) { /* while no winner */
            System.out.println(player + " is making a move");
            Vector<Node> successors = space.getSuccessors(node, player);
            int maxValue = -100;
            bestNodes.clear();
            if (player == 1) {
                for (Node newNode : successors) {
                    newNode.evaluation = evaluate(newNode, player, 15, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    if (newNode.evaluation == maxValue) {
                        bestNodes.add(newNode);
                    } /* ensure random opponent */ else if (newNode.evaluation > maxValue) {
                        bestNodes.clear();
                        bestNodes.add(newNode);
                        maxValue = newNode.evaluation;
                    }
                }
            } else {
                bestNodes.addAll(successors);
            }
            if (successors.isEmpty()) { 
                p = -player;
                break;
            } else {
                int randomIndex = (int) (Math.random() * bestNodes.size());
                node = bestNodes.get(randomIndex);
                System.out.println("Space evaluation after " + name(player) + " move is (" + node.evaluation + ")");
                //System.out.println("\nChosen value: " + node.evaluation);
                printState(node);
                player = -player;
            }
        }
        pr(p == 0 ? "DRAW" : "GAME WON FOR " + name(p) + "\n\n");
    }

    public static void main(String args[]) { // do the search
        new CheckersSearch().run();
    }
}
