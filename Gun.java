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
    public static double rotation;
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
        setRotate();
        this.rotCooldown = 0;
        this.rotCooldownTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rotCooldown -= 1;
                if (rotCooldown <= 0) {
                    rotCooldownTimer.stop();
                }
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

        double temp;
        temp = Math.round(this.targetRotation/30)*30;
        System.out.println("temp:" + temp);
        if (temp%90 == 0) {
            System.out.println("failed, retrying");
            setRotate();}
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img = imageManip.rotate(this.sprite, this.rotation);
        g.drawImage(img, this.x, this.y, this);
    }


    static double convertDegToMomentum(double deg, char direction) {
        double momentum = 0.00;
        double rads = Math.toRadians(deg);
        if (direction == 'x') {
            momentum = Math.cos(rads);
            momentum *= 3;
        }
        if (direction == 'y') {
            momentum = Math.sin(rads);
            momentum *= 3;
        }
        momentum = Math.floor(momentum*100) / 100;
        return momentum;

    }
}
