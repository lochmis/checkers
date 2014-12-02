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
        String expResult = " ";
        String result = instance.name(player);
        assertEquals(expResult, result);
    }

    /**
     * Test of wonFor method, of class CheckersSearch.
     */
    @Test
    public void givenPlayerWhiteAndWhitesOnlyBoard_whenCheckingIfWin_thenTrueExpected() {
        Checker[][] s = makeState(new int[] {1,1,1,1,1,1,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
        int p = 1;
        CheckersSearch instance = new CheckersSearch();
        boolean expResult = true;
        boolean result = instance.wonFor(s, p);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenPlayerBlackAndBlacksOnlyBoard_whenCheckingIfWin_thenTrueExpected() {
        Checker[][] s = makeState(new int[] {-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
        int p = -1;
        CheckersSearch instance = new CheckersSearch();
        boolean expResult = true;
        boolean result = instance.wonFor(s, p);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenPlayerBlackAndMixedBoard_whenCheckingIfWin_thenFalseExpected() {
        Checker[][] s = makeState(new int[] {-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
        int p = -1;
        CheckersSearch instance = new CheckersSearch();
        boolean expResult = false;
        boolean result = instance.wonFor(s, p);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenPlayerWhiteAndMixedBoard_whenCheckingIfWin_thenFalseExpected() {
        Checker[][] s = makeState(new int[] {-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
        int p = 1;
        CheckersSearch instance = new CheckersSearch();
        boolean expResult = false;
        boolean result = instance.wonFor(s, p);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenNoPlayerAndMixedBoard_whenCheckingIfWin_thenTrueExpected() {
        Checker[][] s = makeState(new int[] {-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
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
        Checker[][] s = makeState(new int[] {-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
        CheckersSearch instance = new CheckersSearch();
        int expResult = -1;
        int result = instance.winnerOf(s);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenWhiteOnlyBoard_whenCheckingWhoWin_thenWhiteExpected() {
        Checker[][] s = makeState(new int[] {1,1,1,1,1,1,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
        CheckersSearch instance = new CheckersSearch();
        int expResult = 1;
        int result = instance.winnerOf(s);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenMixedBoard_whenCheckingWhoWin_thenZeroExpected() {
        Checker[][] s = makeState(new int[] {-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
        CheckersSearch instance = new CheckersSearch();
        int expResult = 0;
        int result = instance.winnerOf(s);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenMixedBoard_whenEvaluatingState_givenSevenBlackOneWhite_thenExpectMinusSix() {
        Checker[][] s = makeState(new int[] {-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
        CheckersSearch instance = new CheckersSearch();
        int expResult = -6;
        int result = instance.evaluateState(s);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenMixedBoard_whenEvaluatingState_givenSevenWhiteOneBlack_thenExpectSix() {
        Checker[][] s = makeState(new int[] {1,1,1,1,1,1,1,0,0,0,0,0,0,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
        CheckersSearch instance = new CheckersSearch();
        int expResult = 6;
        int result = instance.evaluateState(s);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenMixedBoard_whenEvaluatingState_givenSevenWhite_thenExpectHundred() {
        Checker[][] s = makeState(new int[] {1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
        CheckersSearch instance = new CheckersSearch();
        int expResult = 100;
        int result = instance.evaluateState(s);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenMixedBoard_whenEvaluatingState_givenSevenBlack_thenExpectMinusHundred() {
        Checker[][] s = makeState(new int[] {-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
        CheckersSearch instance = new CheckersSearch();
        int expResult = -100;
        int result = instance.evaluateState(s);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenMixedBoard_whenEvaluatingState_given4Black2White2WhiteKings_thenExpect4() {
        Checker[][] s = makeState(new int[] {1,1,1,1,-1,-1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
        s[0][0].setKing(true);
        s[0][1].setKing(true);
        CheckersSearch instance = new CheckersSearch();
        int expResult = 4;
        int result = instance.evaluateState(s);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenMixedBoard_whenEvaluatingState_given2Black2BlackKings4White_thenExpectMinus4() {
        Checker[][] s = makeState(new int[] {-1,-1,-1,-1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
        s[0][0].setKing(true);
        s[0][1].setKing(true);
        CheckersSearch instance = new CheckersSearch();
        int expResult = -4;
        int result = instance.evaluateState(s);
        assertEquals(expResult, result);
    }
    
    @Test
    public void givenMixedBoard_whenEvaluatingState_givenRoot_thenExpectZero() {
        Checker[][] s = {
            {new Checker(1), new Checker(1), new Checker(1), new Checker(1)},
            {new Checker(1), new Checker(1), new Checker(1), new Checker(1)},
            {new Checker(1), new Checker(1), new Checker(1), null},
            {null, null, null, new Checker(1)},
            {null, null, null, null},
            {new Checker(-1), new Checker(-1), new Checker(-1), new Checker(-1)},
            {new Checker(-1), new Checker(-1), new Checker(-1), new Checker(-1)},
            {new Checker(-1), new Checker(-1), new Checker(-1), new Checker(-1)}
        };
        CheckersSearch instance = new CheckersSearch();
        int expResult = 0;
        int result = instance.evaluateState(s);
        assertEquals(expResult, result);
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
