package uk.co.lochman.checkers;

/**
 *
 * @author Radek
 */
public class Node {

    private Checker[][] state = new Checker[8][4];
    public int evaluation = -1;
    public Node parent = null;
    public int parentRow = 0;
    public int parentCol = 0;

    Node(Checker state[][], Node parent, int parentRow, int parentCol) {
        this.parentRow = parentRow;
        this.parentCol = parentCol;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 4; col++) {
                this.state[row][col] = state[row][col];
            }
        }
        this.parent = parent;
    }
    
    public Checker[][] getState() {
        return state;
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
}
