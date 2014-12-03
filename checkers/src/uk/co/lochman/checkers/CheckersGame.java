package uk.co.lochman.checkers;

import java.util.Vector;

/**
 *
 * @author Radek
 */
public class CheckersGame {

    private CheckersSpace space = new CheckersSpace();
    private Node currentNode = space.getRoot();
    public int difficulty = 0;
    public int activePlayer = 1;

    public Node getCurrentNode() {
        return currentNode;
    }

    public boolean aiTurn() {
        int aiPlayer = activePlayer;
        Vector<Node> bestNodes = new Vector<Node>();
        Vector<Node> successors = space.getSuccessors(currentNode, aiPlayer);
        if (successors.isEmpty()) {
            return true;
        } else {
            int maxValue = -100;
            if (difficulty == 0) {
                bestNodes.addAll(successors);
            } else {
                for (Node newNode : successors) {
                    newNode.evaluation = evaluateNode(newNode, aiPlayer, difficulty, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    if (newNode.evaluation == maxValue) {
                        bestNodes.add(newNode);
                    } else if (newNode.evaluation > maxValue) {
                        bestNodes.clear();
                        bestNodes.add(newNode);
                        maxValue = newNode.evaluation;
                    }
                }
            }

            int randomIndex = (int) (Math.random() * bestNodes.size());
            currentNode = bestNodes.get(randomIndex);
            activePlayer = -activePlayer;
            return false;
        }
    }

    public CheckersSpace getSpace() {
        return space;
    }

    public void setCurrentNode(Node newNode) {
        this.currentNode = newNode;
    }

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

    public void printState(Node node) { /* print board state */

        System.out.println("\n");
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 4; c++) {
                if (node.getState()[r][c] != null) {
                    int player = node.getState()[r][c].getColor();
                    if (node.getState()[r][c].isKing()) {
                        pr(" ." + name(player) + "K");
                    } else {
                        pr(" . " + name(player));
                    }
                } else {
                    pr(" .  ");
                }
            }
            pr("\n");
            r++;
            for (int c = 0; c < 4; c++) {
                if (node.getState()[r][c] != null) {
                    int player = node.getState()[r][c].getColor();
                    if (node.getState()[r][c].isKing()) {
                        pr(name(player) + "K .");
                    } else {
                        pr(" " + name(player) + " .");
                    }
                } else {
                    pr("   .");
                }
            }
            pr("\n");
        }
        pr("\n");
    }

    boolean wonFor(Checker[][] state, int player) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 4; col++) {
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

    int evaluateState(Checker[][] state) {
        int whiteNumber = 0;
        int blackNumber = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 4; col++) {
                if (state[row][col] != null) {
                    if (state[row][col].getColor() == 1) {
                        whiteNumber++;
                        if (state[row][col].isKing()) {
                            whiteNumber += 2;
                        }
                    }
                    if (state[row][col].getColor() == -1) {
                        blackNumber++;
                        if (state[row][col].isKing()) {
                            blackNumber += 2;
                        }
                    }
                }
            }
        }
        if (whiteNumber == 0) {
            return -100;
        } else if (blackNumber == 0) {
            return 100;
        } else {
            return whiteNumber - blackNumber;
        }
    }

    int evaluateNode(Node node, int player, int depth, int alpha, int beta) {

        if (depth < 1) {
            return player * evaluateState(node.getState());
        }
        int bestValue = -100;
        Vector<Node> successors = space.getSuccessors(node, -player);
        if (successors.isEmpty()) {
            return 100;
        }
        for (Node successor : successors) {
            successor.evaluation = evaluateNode(successor, -player, depth - 1, -alpha, -beta);
            if (-successor.evaluation > bestValue) {
                bestValue = -successor.evaluation;
            }
            if (-successor.evaluation > alpha) {
                alpha = -successor.evaluation;
            }
            if (alpha >= beta) {
                return alpha;
            }
        }
        return bestValue;
    }

}
