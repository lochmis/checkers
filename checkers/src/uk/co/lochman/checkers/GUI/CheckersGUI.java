package uk.co.lochman.checkers.GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import uk.co.lochman.checkers.CheckersGame;
import uk.co.lochman.checkers.Node;

/**
 *
 * @author Radek
 */
public class CheckersGUI extends JFrame implements ActionListener {

    ImagePanel mainGrid;
    CheckersGame game;
    CheckerSquare[][] activeBoard;
    boolean dragging;
    Container pane;
    Vector<Node> possibleMoves;
    boolean highlightMoves = false;
    boolean highlightCheckers = false;
    Timer timer;
    boolean jumped;

    public CheckersGUI() {
        super("Checkers by Radek Lochman");
        init();
    }

    public static void main(String[] args) {
        CheckersGUI GameGrid = new CheckersGUI();
    }

    private void init() {
        game = new CheckersGame();
        activeBoard = new CheckerSquare[8][4];
        dragging = false;
        jumped = false;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 520);
        this.setResizable(false);

        pane = this.getContentPane();
        pane.setLayout(new BorderLayout());

        JPanel RMenu = new JPanel();
        RMenu.setLayout(new GridLayout(2, 0));
        pane.add("East", RMenu);

        JPanel RMenuT = new JPanel();
        RMenuT.setLayout(new GridLayout(8, 0));
        RMenu.add(RMenuT);

        JLabel label = new JLabel("Game Controls");
        label.setHorizontalAlignment(JTextField.CENTER);
        label.setFont(new Font("sansserif", Font.BOLD, 18));
        RMenuT.add(label);
        RMenuT.add(new JLabel(""));

        JButton newGame = new JButton("Restart Game");
        newGame.addActionListener(new NewGameListener(this));
        RMenuT.add(newGame);
        RMenuT.add(new JLabel(""));

        JButton showCheckers = new JButton("Show Help - Checkers");
        showCheckers.addActionListener(new ShowCheckersListener(this));
        RMenuT.add(showCheckers);

        JButton showMoves = new JButton("Show Help - Moves");
        showMoves.addActionListener(new ShowMovesListener(this));
        RMenuT.add(showMoves);

        JSlider difficulty = new JSlider(JSlider.HORIZONTAL, 1, 5, 1);
        difficulty.addChangeListener(new DifficultyListener(this));
        difficulty.setMajorTickSpacing(1);
        difficulty.setPaintLabels(true);
        difficulty.setPaintTicks(true);
        RMenu.add(difficulty);

        try {
            mainGrid = new ImagePanel(ImageIO.read(getClass().getClassLoader().getResource("resources/checkersboard.png")));
        } catch (IOException ex) {
            Logger.getLogger(CheckersGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        JPanel center = new JPanel();
        center.setSize(480, 480);
        center.add(mainGrid);
        mainGrid.setLayout(new GridLayout(8, 8));
        pane.add("Center", center);

        setupSquares();
        setupGameBoard();
        setVisible(true);
        possibleMoves = game.getSpace().getSuccessors(game.getCurrentNode(), game.activePlayer);
        unlockCheckers();
    }

    private void setupSquares() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                if (game.getCurrentNode().getState()[i][j] != null) {
                    activeBoard[i][j] = new CheckerSquare(game.getCurrentNode().getState()[i][j].getColor());
                    activeBoard[i][j].addMouseListener(new CheckerListener(i, j, this));
                } else {
                    activeBoard[i][j] = new CheckerSquare(0);
                    activeBoard[i][j].addMouseListener(new CheckerListener(i, j, this));
                }
            }
        }
    }

    private void setupGameBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                mainGrid.add(new CheckerSquare(0));
                if (game.getCurrentNode().getState()[i * 2][j] != null) {
                    activeBoard[i * 2][j].changePlayer(game.getCurrentNode().getState()[i * 2][j]);
                }
                mainGrid.add(activeBoard[i * 2][j]);
            }
            for (int j = 0; j < 4; j++) {
                if (game.getCurrentNode().getState()[i * 2 + 1][j] != null) {
                    activeBoard[i * 2 + 1][j].changePlayer(game.getCurrentNode().getState()[i * 2 + 1][j]);
                }
                mainGrid.add(activeBoard[i * 2 + 1][j]);
                mainGrid.add(new CheckerSquare(0));
            }
        }
    }

    public void unlockCheckers() {
        if (possibleMoves.isEmpty()) {
            JOptionPane.showMessageDialog(mainGrid, "Sorry! You've Lost!!!");
        }
        for (Node successor : possibleMoves) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++) {
                    if (successor.getState()[i][j] == null && activeBoard[i][j].player != 0 && game.activePlayer == activeBoard[i][j].player) {
                        activeBoard[i][j].unlock();
                        mainGrid.updateUI();
                    }
                }
            }
        }
    }

    public void unlockPossibleMoves(int row, int col) {
        for (Node successor : possibleMoves) {
            if (successor.getState()[row][col] == null) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (successor.getState()[i][j] != null && activeBoard[i][j].player == 0) {
                            activeBoard[i][j].unlock(successor);
                            mainGrid.updateUI();
                        }
                    }
                }
            }
        }
    }

    public void highlightCheckers() {
        for (Node successor : possibleMoves) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++) {
                    if (successor.getState()[i][j] == null && activeBoard[i][j].player != 0 && game.activePlayer == activeBoard[i][j].player) {
                        activeBoard[i][j].setOpaque(true);
                        mainGrid.updateUI();
                    }
                }
            }
        }
    }

    public void highlightPossibleMoves() {
        for (Node successor : possibleMoves) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++) {
                    if (successor.getState()[i][j] != null && activeBoard[i][j].player == 0) {
                        activeBoard[i][j].setOpaque(true);
                        mainGrid.updateUI();
                    }
                }
            }
        }
    }

    public void refreshColors() {
        resetColors();
        if (highlightCheckers) {
            highlightCheckers();
        }
        if (highlightMoves) {
            highlightPossibleMoves();
        }
    }

    public void lockAll() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                activeBoard[i][j].lock();
            }
        }
    }

    public void resetColors() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                activeBoard[i][j].setOpaque(false);
                mainGrid.updateUI();
            }
        }
    }

    public void refreshBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                activeBoard[i][j].changePlayer(game.getCurrentNode().getState()[i][j]);
                mainGrid.updateUI();
            }
        }
    }

    public void changeCursor() {
        if (!dragging) {
            mainGrid.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            try {
                Image image = ImageIO.read(getClass().getClassLoader().getResource("resources/white.png"));
                if (game.activePlayer == -1) {
                    image = ImageIO.read(getClass().getClassLoader().getResource("resources/black.png"));
                }
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Cursor c = toolkit.createCustomCursor(image, new Point(mainGrid.getX(), mainGrid.getY()), "img");
                mainGrid.setCursor(c);
            } catch (IOException ex) {
                Logger.getLogger(CheckerListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void refreshGame(int row, int col) {
        game.setCurrentNode(activeBoard[row][col].getSuccessor());
    }

    public void aiTurn() {
        if (game.getSpace().jumpAvailable(game.getCurrentNode(), game.activePlayer)) {
            jumped = true;
        }
        boolean aiLost = game.aiStep(possibleMoves);
        refreshBoard();
        if (aiLost) {
            JOptionPane.showMessageDialog(mainGrid, "Congratulations! You've won!!!");
        } else if (jumped && !game.newKing() && game.getSpace().jumpAvailable(game.getCurrentNode(), game.activePlayer, game.movedCheckerPosition()[0], game.movedCheckerPosition()[1])) {
            game.printState(game.getCurrentNode());
            possibleMoves = game.getSpace().getJumpsForChecker(game.getCurrentNode(), game.activePlayer, game.movedCheckerPosition()[0], game.movedCheckerPosition()[1]);
            refreshBoard();
            timer = new Timer(1000, this);
            timer.setCoalesce(false);
            timer.setRepeats(false);
            timer.start();
        } else {
            game.printState(game.getCurrentNode());
            game.activePlayer = -game.activePlayer;
            jumped = game.getSpace().jumpAvailable(game.getCurrentNode(), game.activePlayer);
            possibleMoves = game.getSpace().getSuccessors(game.getCurrentNode(), game.activePlayer);
            refreshBoard();
            unlockCheckers();
        }
        refreshColors();
    }

    public void actionPerformed(ActionEvent e) {
        aiTurn();
    }

}

class CheckerListener implements MouseListener {

    int row;
    int col;
    CheckersGUI gui;

    public CheckerListener(int row, int col, CheckersGUI gui) {
        this.row = row;
        this.col = col;
        this.gui = gui;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gui.activeBoard[row][col].isLocked() && !gui.dragging) {
            gui.lockAll();
            gui.unlockPossibleMoves(row, col);
            gui.activeBoard[row][col].changePlayer(null);
            gui.dragging = true;
            gui.changeCursor();
            gui.game.printState(gui.game.getCurrentNode());
            gui.refreshColors();
        } else if (!gui.activeBoard[row][col].isLocked() && gui.dragging) {
            gui.lockAll();
            gui.activeBoard[row][col].changePlayer(gui.activeBoard[row][col].getSuccessor().getState()[row][col]);
            gui.dragging = false;
            gui.changeCursor();
            gui.refreshGame(row, col);

            if (gui.jumped && !gui.game.newKing() && gui.game.getSpace().jumpAvailable(gui.game.getCurrentNode(), gui.game.activePlayer, row, col)) {
                gui.game.printState(gui.game.getCurrentNode());
                gui.possibleMoves = gui.game.getSpace().getJumpsForChecker(gui.game.getCurrentNode(), gui.game.activePlayer, row, col);
                gui.refreshBoard();
                gui.refreshColors();
                gui.unlockCheckers();
            } else {
                gui.jumped = false;
                gui.game.activePlayer = -gui.game.activePlayer;
                gui.possibleMoves = gui.game.getSpace().getSuccessors(gui.game.getCurrentNode(), gui.game.activePlayer);
                gui.refreshBoard();
                gui.resetColors();
                gui.game.printState(gui.game.getCurrentNode());
                gui.timer = new Timer(1000, (ActionListener) gui);
                gui.timer.setCoalesce(false);
                gui.timer.setRepeats(false);
                gui.timer.start();
            }
        } else if (gui.activeBoard[row][col].isLocked() && !gui.dragging) {
            JOptionPane.showMessageDialog(gui.mainGrid, "Sorry, you can't move this one.\nAll available moves has been highlighted.", "Invalid move",
                    JOptionPane.WARNING_MESSAGE);
        } else if (gui.activeBoard[row][col].isLocked() && gui.dragging) {
            JOptionPane.showMessageDialog(gui.mainGrid, "Sorry, you can't move here.\nAll available moves has been highlighted.", "Invalid move",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        if (!gui.activeBoard[row][col].isLocked()) {
//            gui.resetColors();
//            gui.changeCursor();
//            gui.unlockPossibleMoves(row, col);
//            gui.activeBoard[row][col].changePlayer(null);
//            gui.game.printState(gui.game.getCurrentNode());
//        } else if (gui.activeBoard[row][col].isLocked()) {
//            JOptionPane.showMessageDialog(gui.mainGrid, "Sorry, you can't move this one.\nAll available moves has been highlighted.", "Invalid move",
//                    JOptionPane.WARNING_MESSAGE);
//        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        if (!gui.activeBoard[row][col].isLocked()) {
//            gui.resetColors();
//            gui.changeCursor();
//            gui.activeBoard[row][col].changePlayer(gui.activeBoard[row][col].getSuccessor().getState()[row][col]);
//            if (gui.game.getCurrentNode().getState()[row][col] == null) {
//                gui.game.activePlayer = -gui.game.activePlayer;
//                gui.refreshGame(row, col);
//                gui.refreshBoard();
//            }
//            gui.game.printState(gui.game.getCurrentNode());
//            gui.aiTurn();
//            //gui.unlockCheckers();
//        } else if (gui.activeBoard[row][col].isLocked()) {
//            JOptionPane.showMessageDialog(gui.mainGrid, "Sorry, you can't move here.\nAll available moves has been highlighted.", "Invalid move",
//                    JOptionPane.WARNING_MESSAGE);
//        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

}

class ImagePanel extends JPanel {

    private Image img;

    public ImagePanel(String img) {
        this(new ImageIcon(img).getImage());
    }

    public ImagePanel(Image img) {
        this.img = img;
        Dimension size = new Dimension(480, 480);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

}

class ShowMovesListener implements ActionListener {

    CheckersGUI gui;

    public ShowMovesListener(CheckersGUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object[] options = {"ON", "OFF"};
        int decision = JOptionPane.showOptionDialog(gui.mainGrid,
                "This will highlight all checkers that\n"
                + "can legally move.\n"
                + "Do you really want to hightlight them?",
                "Highlight checkers that can move",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);

        if (decision == 0) {
            gui.highlightMoves = true;
            gui.refreshColors();
        }

        if (decision == 1) {
            gui.highlightMoves = false;
            gui.refreshColors();
        }

    }

}

class ShowCheckersListener implements ActionListener {

    CheckersGUI gui;

    public ShowCheckersListener(CheckersGUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object[] options = {"ON", "OFF"};
        int decision = JOptionPane.showOptionDialog(gui.mainGrid,
                "This will highlight all checkers that\n"
                + "can legally move.\n"
                + "Do you really want to hightlight them?",
                "Highlight checkers that can move",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);

        if (decision == 0) {
            gui.highlightCheckers = true;
            gui.refreshColors();
        }

        if (decision == 1) {
            gui.highlightCheckers = false;
            gui.refreshColors();
        }

    }

}

class NewGameListener implements ActionListener {

    CheckersGUI gui;

    public NewGameListener(CheckersGUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object[] options = {"Yes", "No"};
        int decision = JOptionPane.showOptionDialog(gui.mainGrid,
                "If you press YES, the current game will\n"
                + "reset and all progress will be lost.\n"
                + "Do you really want to start new game?",
                "Restart game",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);

        if (decision == 0) {
            restartGame();
        }

    }

    private void restartGame() {
        gui.game.setCurrentNode(gui.game.getSpace().getRoot());
        gui.game.activePlayer = 1;
        gui.dragging = false;
        gui.lockAll();
        gui.refreshBoard();
        gui.changeCursor();
        gui.possibleMoves = gui.game.getSpace().getSuccessors(gui.game.getCurrentNode(), gui.game.activePlayer);
        gui.unlockCheckers();
    }

}

class DifficultyListener implements ChangeListener {

    CheckersGUI gui;

    public DifficultyListener(CheckersGUI gui) {
        this.gui = gui;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            if (source.getValue() == 1) {
                gui.game.difficulty = 0;
            } else if (source.getValue() == 2) {
                gui.game.difficulty = 2;
            } else if (source.getValue() == 3) {
                gui.game.difficulty = 4;
            } else if (source.getValue() == 4) {
                gui.game.difficulty = 7;
            } else if (source.getValue() == 5) {
                gui.game.difficulty = 12;
            }

        }
    }

}
