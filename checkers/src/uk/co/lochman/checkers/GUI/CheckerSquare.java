/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Radek
 */
public class CheckerSquare extends JButton {
    
    int player;

    public CheckerSquare(int player) {
        this.player = player;
        this.setPreferredSize(new Dimension(60, 60));
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setBackground(new Color(0f,1f,0f,.5f ));
        changePlayer(player);
    }

    public void changePlayer(int player){
        this.player = player;
        try {
            if (player == 1) {
                Image img = ImageIO.read(getClass().getClassLoader().getResource("resources/white.png"));
                this.setIcon(new ImageIcon(img));
            } else if (player == -1){
                Image img = ImageIO.read(getClass().getClassLoader().getResource("resources/black.png"));
                this.setIcon(new ImageIcon(img));
            } else {
                this.setIcon(null);
            }
        } catch (IOException ex) {
            Logger.getLogger(CheckerSquare.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
