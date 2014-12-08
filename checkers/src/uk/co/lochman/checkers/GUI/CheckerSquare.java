package uk.co.lochman.checkers.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import uk.co.lochman.checkers.Checker;
import uk.co.lochman.checkers.Node;

/**
 *
 * @author Radek
 */
public class CheckerSquare extends JButton {

    int player;
    private boolean locked = true;
    private Node successor;

    public CheckerSquare(int player) {
        this.player = player;
        this.setPreferredSize(new Dimension(60, 60));
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setBackground(new Color(0f, 1f, 0f, .5f));
        setSquare();
    }

    public boolean isLocked() {
        return locked;
    }

    public void lock() {
        locked = true;
        //this.setOpaque(false);
    }

    public void unlock() {
        locked = false;
        //this.setOpaque(true);
    }

    public void unlock(Node successor) {
        this.successor = successor;
        locked = false;
        //this.setOpaque(true);
    }

    public Node getSuccessor() {
        return successor;
    }

    public void setSquare() {
        try {
            if (player == 1) {
                Image img = ImageIO.read(getClass().getClassLoader().getResource("resources/white.png"));
                this.setIcon(new ImageIcon(img));
            } else if (player == -1) {
                Image img = ImageIO.read(getClass().getClassLoader().getResource("resources/black.png"));
                this.setIcon(new ImageIcon(img));
            } else {
                this.setIcon(null);
            }
        } catch (IOException ex) {
            Logger.getLogger(CheckerSquare.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changePlayer(Checker checker) {
        if (checker == null) {
            this.player = 0;
            this.setIcon(null);
        } else {
            this.player = checker.getColor();
            try {
                if (player == 1) {
                    Image img = ImageIO.read(getClass().getClassLoader().getResource("resources/white.png"));
                    if (checker.isKing()) {
                        img = ImageIO.read(getClass().getClassLoader().getResource("resources/whiteKing.png"));
                    }
                    this.setIcon(new ImageIcon(img));
                } else if (player == -1) {
                    Image img = ImageIO.read(getClass().getClassLoader().getResource("resources/black.png"));
                    if (checker.isKing()) {
                        img = ImageIO.read(getClass().getClassLoader().getResource("resources/blackKing.png"));
                    }
                    this.setIcon(new ImageIcon(img));
                } else {
                    this.setIcon(null);
                }
            } catch (IOException ex) {
                Logger.getLogger(CheckerSquare.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
