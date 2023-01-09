package SnakeGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;;
import java.io.*;
import java.util.Random;

public class Panel extends JPanel implements ActionListener, KeyListener {
    private static final int screenWidth = 600;
    private static final int screenHeight = 600;
    private static final int squareSize = 25;
    private static final int squares = (screenHeight * screenWidth) / squareSize;

    private final int[] x = new int[squares];
    private final int[] y = new int[squares];
    private int snakeParts = 6;
    private int applesEaten;
    private int appleX;
    private int appleY;
    private char directions = 'R';
    private boolean running = false;
    private int delay = 150;
    Timer timer;
    Random random;
    ImageIcon icon;
    ImageIcon icon2;
    Image image;

    public Panel() {
        icon = new ImageIcon("photo/apple.png");
        icon2 = new ImageIcon("photo/SnakeBody.png");
        random = new Random();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            g.setColor(Color.RED);
            g.drawImage(icon.getImage(), appleX, appleY, squareSize, squareSize, null);

            for (int i = 0; i < snakeParts; i++) {
                if (i == 0) {
                    g.drawImage(setImage(), x[i], y[i], squareSize, squareSize, null);
                } else {
                    g.drawImage(icon2.getImage(), x[i], y[i], squareSize, squareSize, null);

                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics fontMetrics = getFontMetrics(g.getFont());
            g.drawString("Score : " + applesEaten, (screenWidth - fontMetrics.stringWidth("Score : " + applesEaten)) / 2, g.getFont().getSize());

        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        appleX = random.nextInt((int) (screenWidth / squareSize)) * squareSize;
        appleY = random.nextInt((int) (screenHeight / squareSize)) * squareSize;
    }

    public void delayIncrease() {
        if (delay > 50)
            delay -= 10;
        timer.setDelay(delay);
    }

    public void move() {
        for (int i = snakeParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (directions) {
            case 'U':
                y[0] = y[0] - squareSize;
                break;
            case 'D':
                y[0] = y[0] + squareSize;
                break;
            case 'L':
                x[0] = x[0] - squareSize;
                break;
            case 'R':
                x[0] = x[0] + squareSize;
                break;
        }
    }

    public Image setImage() {
        String name;
        switch (directions) {
            case 'U':
                name = "snakeHeadUp";
                break;
            case 'D':
                name = "snakeHeadDown";
                break;
            case 'L':
                name = "snakeHeadLeft";
                break;
            case 'R':
                name = "snakeHeadRight";
                break;
            default:
                name = "snakeHeadRight";
                break;
        }
        try {
            image = ImageIO.read(new File("photo/" + name + ".png"));
        } catch (IOException e) {
            System.out.println(" ");
        }
        return image;
    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            snakeParts++;
            applesEaten++;
            newApple();
            delayIncrease();
        }
    }

    public void checkCollisions() {
        for (int i = snakeParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        if (x[0] < 0) running = false;
        if (x[0] > screenWidth) running = false;
        if (y[0] < 0) running = false;
        if (y[0] > screenHeight) running = false;
        if (!running) timer.stop();
    }

    public void gameOver(Graphics g) {


        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics fontMetrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (screenWidth - fontMetrics.stringWidth("Game Over")) / 2, (screenHeight) / 2);

        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics fontMetrics1 = getFontMetrics(g.getFont());
        g.drawString("Score : " + applesEaten, (screenWidth - fontMetrics1.stringWidth("Score : " + applesEaten)) / 2, g.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (directions != 'R') {
                    directions = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (directions != 'L') {
                    directions = 'R';
                }
                break;
            case KeyEvent.VK_UP:
                if (directions != 'D') {
                    directions = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if (directions != 'U') {
                    directions = 'D';
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}