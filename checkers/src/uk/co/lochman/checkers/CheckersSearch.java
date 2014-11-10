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
public class CheckersSearch {

    CheckersSpace space = new CheckersSpace();

    String name(int player) {
        String s = ".";
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
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                int player = node.state[(r * 3) + c];
                pr(" " + name(player));
            }
            pr("\n");
        }
        pr("\n");
    }

    /**
     * decide whether board state s is won for player p
     */
    boolean wonFor(int s[], int p) {
        boolean b = (s[0] == p && s[1] == p && s[2] == p)
                || (s[3] == p && s[4] == p && s[5] == p)
                || (s[6] == p && s[7] == p && s[8] == p)
                || (s[0] == p && s[3] == p && s[6] == p)
                || (s[1] == p && s[4] == p && s[7] == p)
                || (s[2] == p && s[5] == p && s[8] == p)
                || (s[0] == p && s[4] == p && s[8] == p)
                || (s[2] == p && s[4] == p && s[6] == p);
        return b;
    }

    int winnerOf(int state[]) {
        int player = 0;
        if (wonFor(state, 1)) {
            player = 1;
        } else if (wonFor(state, -1)) {
            player = -1;
        }
        return player;
    }

    int evaluate(Node node, int player) { /* using NEGMAX */

        int value = 0;
        if (wonFor(node.state, player)) {
            value = 1;
        } else if (wonFor(node.state, -player)) {
            value = -1;
        } else {
            Vector<Node> successors = space.getSuccessors(node, -player);
            if (successors.size() == 0) { /* draw */

                value = 0;
            } else {
                for (int i = 0; i < successors.size(); i++) {
                    Node successor = successors.get(i);
                    successor.evaluation = evaluate(successor, -player);
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

            Vector<Node> successors = space.getSuccessors(node, player);
            int maxValue = -Integer.MAX_VALUE;
            bestNodes.clear();
            for (int i = 0; i < successors.size(); i++) {
                Node newNode = successors.get(i);
                int value = evaluate(newNode, player);
                if (value == maxValue || player == -1) {
                    bestNodes.add(newNode);
                } /* ensure random opponent */ else if (value > maxValue) {
                    bestNodes.clear();
                    bestNodes.add(newNode);
                    maxValue = value;
                }
            }
            if (successors.isEmpty()) { /* game drawn */

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

