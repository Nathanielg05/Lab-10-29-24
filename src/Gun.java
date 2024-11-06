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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img = imageManip.rotate(this.sprite, this.rotation);
        g.drawImage(img, this.x, this.y, this);
    }
}
