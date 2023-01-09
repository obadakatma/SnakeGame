package SnakeGame;

import javax.swing.*;

public class Frame extends JFrame {
    public Frame() {
        super("Snake");
        this.add(new Panel());
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
}
