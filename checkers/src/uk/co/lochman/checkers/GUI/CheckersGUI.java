package uk.co.lochman.checkers.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import uk.co.lochman.checkers.Checker;
import uk.co.lochman.checkers.CheckersGame;
import uk.co.lochman.checkers.Node;

/**
 *
 * @author Radek
 */
public class CheckersGUI extends JFrame {

    ImagePanel mainGrid;
    CheckersGame game = new CheckersGame();
    final CheckerSquare[][] activeBoard = new CheckerSquare[8][4];
    boolean dragging = false;
    int clickingPlayer;
    Container pane;
    int fromRow;
    int fromCol;

    public CheckersGUI() {
        super("Checkers by Radek Lochman");
        init();
    }

    public static void main(String[] args) {
        CheckersGUI GameGrid = new CheckersGUI();
    }

    private void init() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //necesary to exit the program upon closing the window
        this.setSize(620, 510); //set the size of the window
        this.setResizable(false); //lock the size of the window

        pane = this.getContentPane();
        pane.setLayout(new BorderLayout());

        JPanel RMenu = new JPanel();
        RMenu.setLayout(new GridLayout(2, 0));
        pane.add("East", RMenu);

        JPanel RMenuT = new JPanel();
        RMenuT.setLayout(new GridLayout(8, 0));
        RMenu.add(RMenuT);

        try {
            mainGrid = new ImagePanel(ImageIO.read(getClass().getClassLoader().getResource("resources/checkersboard.png")));//new JPanel();
        } catch (IOException ex) {
            Logger.getLogger(CheckersGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainGrid.setLayout(new GridLayout(8, 8));
        pane.add("Center", mainGrid);

        JLabel label = new JLabel("Game Controls");
        label.setHorizontalAlignment(JTextField.CENTER);
        label.setFont(new Font("sansserif", Font.BOLD, 18));
        RMenuT.add(label);

        setupSquares();
        setupGameBoard();
        //refreshBoard(game.getState()); // Load buttons and Constraints  to GUI
        setVisible(true); // make the window visible
        
        /*try {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Image image = ImageIO.read(getClass().getClassLoader().getResource("resources/white.png"));
                Cursor c = toolkit.createCustomCursor(image , new Point(mainGrid.getX(),
                mainGrid.getY()), "img");
                mainGrid.setCursor(c);
                } catch (IOException ex) {
                    Logger.getLogger(CheckerListener.class.getName()).log(Level.SEVERE, null, ex);
                }*/
    }

    private void refreshBoard(Checker[][] state) {
        mainGrid.removeAll();
        mainGrid.updateUI();
        /*
         for (int x = 0; x < 4; x++){
         for (int y = 0; y < 4; y++) {
         mainGrid.add(new CheckerSquare(1));
         mainGrid.add(new CheckerSquare(-1));
         }
         for (int y = 0; y < 4; y++) {
         mainGrid.add(new CheckerSquare(-1));
         mainGrid.add(new CheckerSquare(1));
         }
         }*/
    }
    
    public void refreshGame(int row, int col) {
        Checker[][] newState = new Checker[8][4];
        for (int i=0; i<8; i++){
            for (int j=0; j<4; j++){
                if(activeBoard[i][j].player == 0) {
                    newState[i][j] = null;
                } else {
                    newState[i][j] = new Checker(activeBoard[i][j].player);
                }
            }
        }
        game.setCurrentNode(newState, row, col);
    }

    private void setupSquares() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                if (game.getCurrentNode().getState()[i][j] != null) {
                    activeBoard[i][j] = new CheckerSquare(game.getCurrentNode().getState()[i][j].getColor());
                    activeBoard[i][j].addActionListener(new CheckerListener(i, j, this));
                } else {
                    activeBoard[i][j] = new CheckerSquare(0);
                    activeBoard[i][j].addActionListener(new CheckerListener(i, j, this));
                }
            }
        }
    }

    private void setupGameBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                mainGrid.add(new CheckerSquare(0));
                if (game.getCurrentNode().getState()[i * 2][j] != null) {
                    activeBoard[i * 2][j].changePlayer(game.getCurrentNode().getState()[i * 2][j].getColor());
                }
                mainGrid.add(activeBoard[i * 2][j]);
            }
            for (int j = 0; j < 4; j++) {
                if (game.getCurrentNode().getState()[i * 2 + 1][j] != null) {
                    activeBoard[i * 2 + 1][j].changePlayer(game.getCurrentNode().getState()[i * 2 + 1][j].getColor());
                }
                mainGrid.add(activeBoard[i * 2 + 1][j]);
                mainGrid.add(new CheckerSquare(0));
            }
        }
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

class CheckerListener implements ActionListener {

    int row;
    int col;
    CheckersGUI gui;

    public CheckerListener(int row, int col, CheckersGUI gui) {
        this.row = row;
        this.col = col;
        this.gui = gui;
    }

    public void actionPerformed(ActionEvent e) {
        if(!gui.dragging && gui.clickingPlayer == 0){
            gui.clickingPlayer = gui.activeBoard[row][col].player;
            gui.activeBoard[row][col].changePlayer(0);
            gui.dragging = true;
            gui.game.printState(gui.game.getCurrentNode());
            gui.fromRow = row;
            gui.fromCol = col;
            if(gui.clickingPlayer == 1){
                try {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Image image = ImageIO.read(getClass().getClassLoader().getResource("resources/white.png"));
                Cursor c = toolkit.createCustomCursor(image , new Point(gui.mainGrid.getX(),
                gui.mainGrid.getY()), "img");
                gui.mainGrid.setCursor(c);
                } catch (IOException ex) {
                    Logger.getLogger(CheckerListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if(gui.clickingPlayer == -1){
                try {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Image image = ImageIO.read(getClass().getClassLoader().getResource("resources/black.png"));
                Cursor c = toolkit.createCustomCursor(image , new Point(gui.mainGrid.getX(),
                gui.mainGrid.getY()), "img");
                gui.mainGrid.setCursor(c);
                } catch (IOException ex) {
                    Logger.getLogger(CheckerListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            Vector<Node> successors = gui.game.getSpace().getSuccessors(gui.game.getCurrentNode(), gui.clickingPlayer);
            ColorPossibleMoves(gui, successors, row, col);
            
        } else if (gui.dragging && gui.clickingPlayer != 0) {
            gui.activeBoard[row][col].changePlayer(gui.clickingPlayer);
            gui.mainGrid.updateUI();
            gui.dragging = false;
            gui.mainGrid.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            gui.clickingPlayer = 0;
            resetColors();
            gui.refreshGame(gui.fromRow, gui.fromCol);
            gui.game.printState(gui.game.getCurrentNode());
        }
    }

    private void ColorPossibleCheckers(CheckersGUI gui, Vector<Node> successors, int row, int col) {
        for (Node successor: successors){
            if (successor.getState()[row][col] == null){
                for (int i=0; i<8; i++){
                    for (int j=0; j<4; j++){
                        if(successor.getState()[i][j] != null && gui.activeBoard[i][j].player == 0){
                            gui.activeBoard[i][j].setOpaque(true);
                            gui.mainGrid.updateUI();
                        }
                    }
                }
            }
        }
    }

    private void ColorPossibleMoves(CheckersGUI gui, Vector<Node> successors, int row, int col) {
        for (Node successor: successors){
            if (successor.getState()[row][col] == null){
                for (int i=0; i<8; i++){
                    for (int j=0; j<4; j++){
                        if(successor.getState()[i][j] != null && gui.activeBoard[i][j].player == 0){
                            gui.activeBoard[i][j].setOpaque(true);
                            gui.mainGrid.updateUI();
                        }
                    }
                }
            }
        }
    }
    
    private void resetColors() {
        for (int i=0; i<8; i++){
            for (int j=0; j<4; j++){
                gui.activeBoard[i][j].setOpaque(false);
                gui.mainGrid.updateUI();
            }
        }
    }
}
