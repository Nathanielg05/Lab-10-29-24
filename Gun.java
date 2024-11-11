import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;


public class Gun extends JPanel implements imageManip {
    private BufferedImage sprite;
    private int x;
    private int y;
    public double rotation;
    public int rotateFlag;
    public double targetRotation;
    public Timer rotCooldownTimer;
    public int rotCooldown;

    Gun(int xPos, int yPos) {
        try {
            sprite = ImageIO.read(new File("src/gun.png"));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.x = xPos;
        this.y = yPos;
        this.rotation = 0.00;
        this.rotCooldown = 0;
        this.rotCooldownTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rotCooldown -= 1;
                if (rotCooldown <= 0) {
                    rotCooldownTimer.stop();
                }
                System.out.println(rotCooldown);
            }
        });

    }

    void rotate() {
        if (this.rotCooldown == 0) {
            if (rotateFlag == -1) {
                this.rotation -= 1.5;
            }
            if (rotateFlag == 1) {
                this.rotation += 1.5;
            }
        }
        //this.rotation = Math.round(this.rotation);
    }

    void setRotate() {
        this.targetRotation = Math.round(Math.random() * 360); // get random rotation 0-360
        if (Math.abs(this.targetRotation - this.rotation) < 30) {this.targetRotation+= 50;}
        this.targetRotation = Math.ceil(targetRotation/3)*3;
        double cmp = Math.round(this.rotation) - this.targetRotation;
        if (cmp < 0) {this.rotateFlag = 1;}
        else {this.rotateFlag = -1;}
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img = imageManip.rotate(this.sprite, this.rotation);
        g.drawImage(img, this.x, this.y, this);
    }
}
