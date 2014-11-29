/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.lochman.checkers;

import java.util.Vector;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author radek
 */
public class CheckersSpaceTest {
    
    public CheckersSpaceTest() {
    }

    /**
     * Test of getSuccessors method, of class CheckersSpace.
     */
    @Test
    public void givenBoardWithOneMovePossible_whenGettingSuccessors_thenOneSuccessorReturned() {
        Checker[][] state = makeState(new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0});
        Node parent = new Node(state, null);
        int player = 1;
        CheckersSpace instance = new CheckersSpace();
        Vector<Node> result = instance.getSuccessors(parent, player);
        assertEquals(1, result.size());
    }
    
    @Test
    public void givenBoardWithTwoMovesPossible_whenGettingSuccessors_thenTwoSuccessorsReturned() {
        Checker[][] state = makeState(new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0});
        Node parent = new Node(state, null);
        int player = 1;
        CheckersSpace instance = new CheckersSpace();
        Vector<Node> result = instance.getSuccessors(parent, player);
        assertEquals(2, result.size());
    }
    
    @Test
    public void givenBoardWithOneMovePossible2_whenGettingSuccessors_thenOneSuccessorReturned() {
        Checker[][] state = makeState(new int[] {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
        Node parent = new Node(state, null);
        int player = 1;
        CheckersSpace instance = new CheckersSpace();
        Vector<Node> result = instance.getSuccessors(parent, player);
        assertEquals(1, result.size());
    }
    
    @Test
    public void givenBoardWithTwoMovesPossible2_whenGettingSuccessors_thenTwoSuccessorsReturned() {
        Checker[][] state = makeState(new int[] {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
        Node parent = new Node(state, null);
        int player = 1;
        CheckersSpace instance = new CheckersSpace();
        Vector<Node> result = instance.getSuccessors(parent, player);
        assertEquals(2, result.size());
    }
    
    @Test
    public void givenBoardWithTwoBlackMovesPossible_whenGettingSuccessors_thenTwoSuccessorsReturned() {
        Checker[][] state = makeState(new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,0,0,0,0,0});
        Node parent = new Node(state, null);
        int player = -1;
        CheckersSpace instance = new CheckersSpace();
        Vector<Node> result = instance.getSuccessors(parent, player);
        assertEquals(2, result.size());
    }
    
    @Test
    public void givenBoardWithOneBlackMovePossible_whenGettingSuccessors_thenOneSuccessorReturned() {
        Checker[][] state = makeState(new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,0,0,0,0});
        Node parent = new Node(state, null);
        int player = -1;
        CheckersSpace instance = new CheckersSpace();
        Vector<Node> result = instance.getSuccessors(parent, player);
        assertEquals(1, result.size());
    }
    
    @Test
    public void givenBoardWithTwoBlackMovesPossible2_whenGettingSuccessors_thenTwoSuccessorsReturned() {
        Checker[][] state = makeState(new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,0});
        Node parent = new Node(state, null);
        int player = -1;
        CheckersSpace instance = new CheckersSpace();
        Vector<Node> result = instance.getSuccessors(parent, player);
        assertEquals(2, result.size());
    }
    
    @Test
    public void givenBoardWithOneBlackMovePossible2_whenGettingSuccessors_thenOneSuccessorReturned() {
        Checker[][] state = makeState(new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,0,0,0});
        Node parent = new Node(state, null);
        int player = -1;
        CheckersSpace instance = new CheckersSpace();
        Vector<Node> result = instance.getSuccessors(parent, player);
        assertEquals(1, result.size());
    }
    
    private Checker[][] makeState(int[] state){
        int position = 0;
        Checker[][] newState = new Checker[8][4];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 4; col++){
                if (state[position] == 0) {
                    newState[row][col] = null;
                    position++;
                } else {
                    newState[row][col] = new Checker(state[position]);
                    position++;
                }
            }
        }
        return newState;
    }
    
}
