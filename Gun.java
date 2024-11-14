import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class Gun extends JPanel implements imageManip {
    private BufferedImage sprite;
    private BufferedImage resizedSprite; // resized version of sprite
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
            // Resize sprite to a fixed width and height
            resizedSprite = resizeImage(sprite, 50, 50);
        } catch (Exception e) {
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
            }
        });
    }

    // Method to resize the image
    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        Image tempImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tempImage, 0, 0, null);
        g2d.dispose();
        return resizedImage;
    }

    void rotate() {
        if (rotateFlag == -1) {
            this.rotation -= 1.5;
        }
        if (rotateFlag == 1) {
            this.rotation += 1.5;
        }
    }

    void setRotate() {
        this.targetRotation = Math.round(Math.random() * 360);
        this.targetRotation = Math.ceil(targetRotation / 3) * 3;
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

        // Rotate the resized image
        BufferedImage img = imageManip.rotate(this.resizedSprite, this.rotation);
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
