/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package space;

import java.awt.*;
import javax.swing.ImageIcon;

public class Alien {
    private int x, y;
    private final int WIDTH = 40, HEIGHT = 40;
    private Image image;

    public Alien(int x, int y) {
        this.x = x;
        this.y = y;
        this.image = new ImageIcon("alien.png").getImage(); 
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, WIDTH, HEIGHT, null); 
    }

    public void move(int direction) {
        x += direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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
