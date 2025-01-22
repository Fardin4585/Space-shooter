/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package space;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Spaceship spaceship;
    private ArrayList<Bullet> bullets;
    private ArrayList<Alien> aliens;
    private int alienDirection = 1;
    private boolean gameOver = false;
    private boolean playerWon = false;
    private Image backgroundImage;
    private AudioPlayer audioPlayer;

    public GamePanel() {
        setFocusable(true);
        setPreferredSize(new Dimension(800, 600));
        addKeyListener(this);

        spaceship = new Spaceship(400, 500);
        bullets = new ArrayList<>();
        aliens = new ArrayList<>();

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 10; col++) {
                aliens.add(new Alien(50 + col * 60, 30 + row * 60));
            }
        }

        backgroundImage = new ImageIcon("background.jpg").getImage();
        audioPlayer = new AudioPlayer();
        audioPlayer.play("background_music.wav"); // Path to your music file

        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            if (playerWon) {
                g.drawString("You Win!", 300, 300);
            } else {
                g.drawString("You Lose!", 300, 300);
            }
            audioPlayer.stop(); // Stop the music when the game is over
        } else {
            spaceship.draw(g);

            for (Bullet bullet : bullets) {
                bullet.draw(g);
            }

            for (Alien alien : aliens) {
                alien.draw(g);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            spaceship.move();

            for (Bullet bullet : bullets) {
                bullet.move();
            }

            moveAliens();
            checkCollisions();

            bullets.removeIf(bullet -> bullet.getY() < 0);

            if (aliens.isEmpty()) {
                gameOver = true;
                playerWon = true;
                timer.stop();
            }

            repaint();
        }
    }

    private void moveAliens() {
        boolean changeDirection = false;

        for (Alien alien : aliens) {
            alien.move(alienDirection);

            // Check if any alien has reached the bottom of the screen or collided with the spaceship
            if (alien.getY() + alien.getHeight() >= getHeight() || alien.getBounds().intersects(spaceship.getBounds())) {
                gameOver = true;
                playerWon = false;
                timer.stop();
                audioPlayer.stop(); // Stop the music when the game is over
                return;
            }

            if (alien.getX() <= 0 || alien.getX() >= getWidth() - alien.getWidth()) {
                changeDirection = true;
            }
        }

        if (changeDirection) {
            alienDirection *= -1;
            for (Alien alien : aliens) {
                alien.setY(alien.getY() + alien.getHeight());
            }
        }
    }

    private void checkCollisions() {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            Iterator<Alien> alienIterator = aliens.iterator();
            while (alienIterator.hasNext()) {
                Alien alien = alienIterator.next();
                if (bullet.getBounds().intersects(alien.getBounds())) {
                    bulletIterator.remove();
                    alienIterator.remove();
                    break;
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            spaceship.setDx(-2);
        }
        if (key == KeyEvent.VK_RIGHT) {
            spaceship.setDx(2);
        }
        if (key == KeyEvent.VK_SPACE) {
            bullets.add(new Bullet(spaceship.getX() + spaceship.getWidth() / 2, spaceship.getY()));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            spaceship.setDx(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
