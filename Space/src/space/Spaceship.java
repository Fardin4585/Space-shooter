/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package space;

import java.awt.*;
import javax.swing.*;

public class Spaceship {
    private int x, y, dx;
    private final int WIDTH = 70, HEIGHT = 50;
    private Image image;

    public Spaceship(int x, int y) {
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.image = new ImageIcon("spaceship.png").getImage(); 
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, WIDTH, HEIGHT, null); 
    }

    public void move() {
        x += dx;
        if (x < 0) x = 0;
        if (x > 800 - WIDTH) x = 800 - WIDTH;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}
