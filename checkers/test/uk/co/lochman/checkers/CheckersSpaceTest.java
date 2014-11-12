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
        int state[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0};
        Node parent = new Node(state, null);
        int player = 1;
        CheckersSpace instance = new CheckersSpace();
        Vector<Node> result = instance.getSuccessors(parent, player);
        assertEquals(1, result.size());
    }
    
    @Test
    public void givenBoardWithTwoMovesPossible_whenGettingSuccessors_thenTwoSuccessorsReturned() {
        int state[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0};
        Node parent = new Node(state, null);
        int player = 1;
        CheckersSpace instance = new CheckersSpace();
        Vector<Node> result = instance.getSuccessors(parent, player);
        assertEquals(2, result.size());
    }
    
    @Test
    public void givenBoardWithOneMovePossible2_whenGettingSuccessors_thenOneSuccessorReturned() {
        int state[] = {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        Node parent = new Node(state, null);
        int player = 1;
        CheckersSpace instance = new CheckersSpace();
        Vector<Node> result = instance.getSuccessors(parent, player);
        assertEquals(1, result.size());
    }
    
    @Test
    public void givenBoardWithTwoMovesPossible2_whenGettingSuccessors_thenTwoSuccessorsReturned() {
        int state[] = {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        Node parent = new Node(state, null);
        int player = 1;
        CheckersSpace instance = new CheckersSpace();
        Vector<Node> result = instance.getSuccessors(parent, player);
        assertEquals(2, result.size());
    }
    
    @Test
    public void givenBoardWithTwoBlackMovesPossible_whenGettingSuccessors_thenTwoSuccessorsReturned() {
        int state[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,0,0,0,0,0};
        Node parent = new Node(state, null);
        int player = -1;
        CheckersSpace instance = new CheckersSpace();
        Vector<Node> result = instance.getSuccessors(parent, player);
        assertEquals(2, result.size());
    }
    
    @Test
    public void givenBoardWithOneBlackMovePossible_whenGettingSuccessors_thenOneSuccessorReturned() {
        int state[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,0,0,0,0};
        Node parent = new Node(state, null);
        int player = -1;
        CheckersSpace instance = new CheckersSpace();
        Vector<Node> result = instance.getSuccessors(parent, player);
        assertEquals(1, result.size());
    }
    
    @Test
    public void givenBoardWithTwoBlackMovesPossible2_whenGettingSuccessors_thenTwoSuccessorsReturned() {
        int state[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,0};
        Node parent = new Node(state, null);
        int player = -1;
        CheckersSpace instance = new CheckersSpace();
        Vector<Node> result = instance.getSuccessors(parent, player);
        assertEquals(2, result.size());
    }
    
    @Test
    public void givenBoardWithOneBlackMovePossible2_whenGettingSuccessors_thenOneSuccessorReturned() {
        int state[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,0,0,0};
        Node parent = new Node(state, null);
        int player = -1;
        CheckersSpace instance = new CheckersSpace();
        Vector<Node> result = instance.getSuccessors(parent, player);
        assertEquals(1, result.size());
    }
    
}
