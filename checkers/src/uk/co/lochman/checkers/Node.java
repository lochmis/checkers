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
public class Node {

    Checker state[][] = new Checker[8][4];
    int evaluation = -1;
    Node parent = null;

    Node(Checker state[][], Node parent) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 4; col++) {
                this.state[row][col] = state[row][col];
            }
        }
        this.parent = parent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 4; col++) {
                sb.append(state[row][col]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    Checker[][] getStateCopy() {
        Checker[][] copy = new Checker[8][4];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 4; col++) {
                copy[row][col] = state[row][col];
            }
        }
        return copy;
    }

    Vector<Node> getPath(Vector<Node> v) {
        v.insertElementAt(this, 0);
        if (parent != null) {
            v = parent.getPath(v);
        }
        return v;
    }

    Vector<Node> getPath() {
        return (getPath(new Vector<Node>()));
    }
}
