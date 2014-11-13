/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.lochman.checkers;

//import com.google.common.base.Objects;
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
                    pr(" . " + name(player));
                } else {
                    pr(" .  ");
                }
            }
            pr("\n");
            r++;
            for (int c = 0; c < 4; c++) {
                if (node.state[r][c] != null){
                    int player = node.state[r][c].getColor();
                    pr(" " + name(player) + " .");
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

    int evaluate(Node node, int player, int depth) { /* using NEGMAX */

        int value = 0;
        if (wonFor(node.state, player)) {
            value = 1;
        } else if (wonFor(node.state, -player)) {
            value = -1;
        } else if (depth > 0) {
            Vector<Node> successors = space.getSuccessors(node, -player);
            if (successors.size() == 0) { /* draw */
                value = -1;
            } else {
                for (Node successor : successors) {
                    successor.evaluation = evaluate(successor, -player, depth - 1);
                    if (successor.evaluation > value) {
                        value = successor.evaluation;
                    }
                }
                value = -value;
            }
        }
        return (value);
    }

    void run() {
        int p, player = 1;
        Node node = space.getRoot();
        Vector<Node> bestNodes = new Vector<Node>();
        printState(node);

        while ((p = winnerOf(node.state)) == 0) { /* while no winner */
            System.out.println(player + " is making a move");
            Vector<Node> successors = space.getSuccessors(node, player);
            int maxValue = -Integer.MAX_VALUE;
            bestNodes.clear();
            if (player == 1) {
                for (Node newNode : successors) {
                    int value = evaluate(newNode, player, 5);
                    pr("" + value);
                    if (value == maxValue) {
                        bestNodes.add(newNode);
                    } /* ensure random opponent */ else if (value > maxValue) {
                        bestNodes.clear();
                        bestNodes.add(newNode);
                        maxValue = value;
                    }
                }
            } else {
                bestNodes.addAll(successors);
            }
            if (successors.isEmpty()) { 
                p = -player;
                break;
            } else {
                pr("State after new " + name(player) + " (" + player + ")");
                int randomIndex = (int) (Math.random() * bestNodes.size());
                node = bestNodes.get(randomIndex);
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
