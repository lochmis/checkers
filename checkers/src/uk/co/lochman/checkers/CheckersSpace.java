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

    Node getRoot() {
        int state[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        return new Node(state, null);
    }

    Vector<Node> getSuccessors(Node parent, int player) {
        Vector<Node> successors = new Vector<Node>();
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (parent.state[(r * 3) + c] == 0) { /* empty cell here */

                    int state[] = parent.getStateCopy();
                    state[(r * 3) + c] = player;
                    successors.add(new Node(state, parent));
                }
            }
        }
        return successors;
    }

}
