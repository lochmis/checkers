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

    int state[] = new int[32];
    int evaluation = -1;
    Node parent = null;

    Node(int s[], Node parent) {
        System.arraycopy(s, 0, state, 0, 32);
        this.parent = parent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int field : state) {
            sb.append(field);
            sb.append(" ");
        }
        return sb.toString();
    }

    int[] getStateCopy() {
        int[] copy = new int[32];
        System.arraycopy(state, 0, copy, 0, 32);
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
