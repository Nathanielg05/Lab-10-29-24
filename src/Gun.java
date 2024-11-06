import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class Gun extends JPanel implements imageManip {
    private BufferedImage sprite;
    private int x;
    private int y;
    public double rotation;
    public int rotateFlag;

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

    }

    void rotate() {
        if (rotateFlag == -1) {
            this.rotation -= 3.0;
        }
        if (rotateFlag == 1) {
            this.rotation += 3.0;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img = imageManip.rotate(this.sprite, this.rotation);
        g.drawImage(img, this.x, this.y, this);
    }
}
