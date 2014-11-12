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

    public Node getRoot() {
        int state[] = {1, 1, 1, 1,
                       1, 1, 1, 1,
                       1, 1, 1, 1,
                       0, 0, 0, 0,
                       0, 0, 0, 0,
                      -1,-1,-1,-1,
                      -1,-1,-1,-1,
                      -1,-1,-1,-1};
        return new Node(state, null);
    }

    public Vector<Node> getSuccessors(Node parent, int player) {
        Vector<Node> moves = new Vector<Node>();
        Vector<Node> jumps = new Vector<Node>();
        if (player == 1) {
            for (int row = 0; row < 7; row++) {
                for (int col = 0; col < 4; col++) {
                    if (parent.state[row * 4 + col] == player) {
                        jumps.addAll(checkForWhiteJumps(row, col, player, parent));
                        if (jumps.isEmpty()) {
                            moves.addAll(checkForWhiteMoves(row, col, player, parent));
                        }

                    }
                }
            }
        } else {
            for (int row = 1; row < 8; row++) {
                for (int col = 0; col < 4; col++) {
                    if (parent.state[row * 4 + col] == player) {
                        jumps.addAll(checkForBlackJumps(row, col, player, parent));
                        if (jumps.isEmpty()) {
                            moves.addAll(checkForBlackMoves(row, col, player, parent));
                        }
                    }
                }
            }
        }
        Vector<Node> successors = new Vector<Node>();
        successors.addAll(moves);
        successors.addAll(jumps);
        return successors;
    }

    private Vector<Node> checkForWhiteMoves(int row, int col, int player, Node parent) {
        Vector<Node> moves = new Vector<Node>();
        if (row % 2 == 0) {
            if (col < 3) {
                if (parent.state[row * 4 + col + 4] == 0) {
                    int state[] = parent.getStateCopy();
                    state[row * 4 + col] = 0;
                    state[row * 4 + col + 4] = player;
                    moves.add(new Node(state, parent));
                }
                if (parent.state[row * 4 + col + 5] == 0) {
                    int state[] = parent.getStateCopy();
                    state[row * 4 + col] = 0;
                    state[row * 4 + col + 5] = player;
                    moves.add(new Node(state, parent));
                }
            } else {
                if (parent.state[row * 4 + col + 4] == 0) {
                    int state[] = parent.getStateCopy();
                    state[row * 4 + col] = 0;
                    state[row * 4 + col + 4] = player;
                    moves.add(new Node(state, parent));
                }
            }
        } else {
            if (col > 0) {
                if (parent.state[row * 4 + col + 3] == 0) {
                    int state[] = parent.getStateCopy();
                    state[row * 4 + col] = 0;
                    state[row * 4 + col + 3] = player;
                    moves.add(new Node(state, parent));
                }
                if (parent.state[row * 4 + col + 4] == 0) {
                    int state[] = parent.getStateCopy();
                    state[row * 4 + col] = 0;
                    state[row * 4 + col + 4] = player;
                    moves.add(new Node(state, parent));
                }
            } else {
                if (parent.state[row * 4 + col + 4] == 0) {
                    int state[] = parent.getStateCopy();
                    state[row * 4 + col] = 0;
                    state[row * 4 + col + 4] = player;
                    moves.add(new Node(state, parent));
                }
            }
        }
        return moves;
    }

    private Vector<Node> checkForBlackMoves(int row, int col, int player, Node parent) {
        Vector<Node> moves = new Vector<Node>();
        if (row % 2 == 0) {
            if (col < 3) {
                if (parent.state[row * 4 + col - 3] == 0) {
                    int state[] = parent.getStateCopy();
                    state[row * 4 + col] = 0;
                    state[row * 4 + col - 3] = player;
                    moves.add(new Node(state, parent));
                }
                if (parent.state[row * 4 + col - 4] == 0) {
                    int state[] = parent.getStateCopy();
                    state[row * 4 + col] = 0;
                    state[row * 4 + col - 4] = player;
                    moves.add(new Node(state, parent));
                }
            } else {
                if (parent.state[row * 4 + col - 4] == 0) {
                    int state[] = parent.getStateCopy();
                    state[row * 4 + col] = 0;
                    state[row * 4 + col - 4] = player;
                    moves.add(new Node(state, parent));
                }
            }
        } else {
            if (col > 0) {
                if (parent.state[row * 4 + col - 4] == 0) {
                    int state[] = parent.getStateCopy();
                    state[row * 4 + col] = 0;
                    state[row * 4 + col - 4] = player;
                    moves.add(new Node(state, parent));
                }
                if (parent.state[row * 4 + col - 5] == 0) {
                    int state[] = parent.getStateCopy();
                    state[row * 4 + col] = 0;
                    state[row * 4 + col - 5] = player;
                    moves.add(new Node(state, parent));
                }
            } else {
                if (parent.state[row * 4 + col - 4] == 0) {
                    int state[] = parent.getStateCopy();
                    state[row * 4 + col] = 0;
                    state[row * 4 + col - 4] = player;
                    moves.add(new Node(state, parent));
                }
            }
        }
        return moves;
    }

    private Vector<Node> checkForWhiteJumps(int row, int col, int player, Node parent) {
        Vector<Node> jumps = new Vector<Node>();
        if (row < 6) {
            if (row % 2 == 0) {
                if (col == 0) {
                    if (parent.state[row * 4 + 5] == -player && parent.state[row * 4 + 9] == 0) {
                        int state[] = parent.getStateCopy();
                        state[row * 4 + col] = 0;
                        state[row * 4 + col + 5] = 0;
                        state[row * 4 + col + 9] = player;
                        Vector<Node> longJumps = checkForWhiteJumps(row + 2, col + 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                } else if (col < 3) {
                    if (parent.state[row * 4 + col + 4] == -player && parent.state[row * 4 + col + 7] == 0) {
                        int state[] = parent.getStateCopy();
                        state[row * 4 + col] = 0;
                        state[row * 4 + col + 4] = 0;
                        state[row * 4 + col + 7] = player;
                        Vector<Node> longJumps = checkForWhiteJumps(row + 2, col - 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                    if (parent.state[row * 4 + col + 5] == -player && parent.state[row * 4 + 9] == 0) {
                        int state[] = parent.getStateCopy();
                        state[row * 4 + col] = 0;
                        state[row * 4 + col + 5] = 0;
                        state[row * 4 + col + 9] = player;
                        Vector<Node> longJumps = checkForWhiteJumps(row + 2, col + 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                } else {
                    if (parent.state[row * 4 + col + 4] == -player && parent.state[row * 4 + col + 7] == 0) {
                        int state[] = parent.getStateCopy();
                        state[row * 4 + col] = 0;
                        state[row * 4 + col + 4] = 0;
                        state[row * 4 + col + 7] = player;
                        Vector<Node> longJumps = checkForWhiteJumps(row + 2, col - 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                }
            } else {
                if (col == 0) {
                    if (parent.state[row * 4 + col + 4] == -player && parent.state[row * 4 + col + 9] == 0) {
                        int state[] = parent.getStateCopy();
                        state[row * 4 + col] = 0;
                        state[row * 4 + col + 4] = 0;
                        state[row * 4 + col + 9] = player;
                        Vector<Node> longJumps = checkForWhiteJumps(row + 2, col + 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                } else if (col < 3) {
                    if (parent.state[row * 4 + col + 3] == -player && parent.state[row * 4 + col + 7] == 0) {
                        int state[] = parent.getStateCopy();
                        state[row * 4 + col] = 0;
                        state[row * 4 + col + 3] = 0;
                        state[row * 4 + col + 7] = player;
                        Vector<Node> longJumps = checkForWhiteJumps(row + 2, col - 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                    if (parent.state[row * 4 + col + 4] == -player && parent.state[row * 4 + 9] == 0) {
                        int state[] = parent.getStateCopy();
                        state[row * 4 + col] = 0;
                        state[row * 4 + col + 4] = 0;
                        state[row * 4 + col + 9] = player;
                        Vector<Node> longJumps = checkForWhiteJumps(row + 2, col + 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                } else {
                    if (parent.state[row * 4 + 3] == -player && parent.state[row * 4 + 7] == 0) {
                        int state[] = parent.getStateCopy();
                        state[row * 4 + col] = 0;
                        state[row * 4 + col + 3] = 0;
                        state[row * 4 + col + 7] = player;
                        Vector<Node> longJumps = checkForWhiteJumps(row + 2, col - 1, player,new Node(state, parent));
                        if (longJumps.isEmpty()){
                            jumps.add(new Node(state, parent));
                        } else {
                            jumps.addAll(longJumps);
                        }
                    }
                }
            }
        }
        return jumps;
    }

    private Vector<Node> checkForBlackJumps(int row, int col, int player, Node parent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
