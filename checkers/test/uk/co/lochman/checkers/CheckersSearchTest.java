/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.lochman.checkers;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author radek
 */
public class CheckersSearchTest {
    
    public CheckersSearchTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of name method, of class CheckersSearch.
     */
    @Test
    public void givenPlayerWhite_whenCheckingName_WIsReturned() {
        int player = 1;
        CheckersSearch instance = new CheckersSearch();
        String expResult = "W";
        String result = instance.name(player);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenPlayerBlack_whenCheckingName_BIsReturned() {
        int player = -1;
        CheckersSearch instance = new CheckersSearch();
        String expResult = "B";
        String result = instance.name(player);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenNoPlayer_whenCheckingName_DotIsReturned() {
        int player = 0;
        CheckersSearch instance = new CheckersSearch();
        String expResult = ".";
        String result = instance.name(player);
        assertEquals(expResult, result);
    }

    /**
     * Test of wonFor method, of class CheckersSearch.
     */
    @Test
    public void givenPlayerWhiteAndWhitesOnlyBoard_whenCheckingIfWin_thenTrueExpected() {
        int[] s = {1,1,1,1,1,1,1,0,0,0,0,0,0,1};
        int p = 1;
        CheckersSearch instance = new CheckersSearch();
        boolean expResult = true;
        boolean result = instance.wonFor(s, p);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenPlayerBlackAndBlacksOnlyBoard_whenCheckingIfWin_thenTrueExpected() {
        int[] s = {-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,-1};
        int p = -1;
        CheckersSearch instance = new CheckersSearch();
        boolean expResult = true;
        boolean result = instance.wonFor(s, p);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenPlayerBlackAndMixedBoard_whenCheckingIfWin_thenFalseExpected() {
        int[] s = {-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,1};
        int p = -1;
        CheckersSearch instance = new CheckersSearch();
        boolean expResult = false;
        boolean result = instance.wonFor(s, p);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenPlayerWhiteAndMixedBoard_whenCheckingIfWin_thenFalseExpected() {
        int[] s = {-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,1};
        int p = 1;
        CheckersSearch instance = new CheckersSearch();
        boolean expResult = false;
        boolean result = instance.wonFor(s, p);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenNoPlayerAndMixedBoard_whenCheckingIfWin_thenTrueExpected() {
        int[] s = {-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,1};
        int p = 1;
        CheckersSearch instance = new CheckersSearch();
        boolean expResult = false;
        boolean result = instance.wonFor(s, p);
        assertEquals(expResult, result);
    }

    /**
     * Test of winnerOf method, of class CheckersSearch.
     */
    @Test
    public void givenBlackOnlyBoard_whenCheckingWhoWin_thenBlackExpected() {
        int[] state = {-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,-1};
        CheckersSearch instance = new CheckersSearch();
        int expResult = -1;
        int result = instance.winnerOf(state);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenWhiteOnlyBoard_whenCheckingWhoWin_thenWhiteExpected() {
        int[] state = {1,1,1,1,1,1,1,0,0,0,0,0,0,1};
        CheckersSearch instance = new CheckersSearch();
        int expResult = 1;
        int result = instance.winnerOf(state);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenMixedBoard_whenCheckingWhoWin_thenZeroExpected() {
        int[] state = {-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,1};
        CheckersSearch instance = new CheckersSearch();
        int expResult = 0;
        int result = instance.winnerOf(state);
        assertEquals(expResult, result);
    }
    
}
