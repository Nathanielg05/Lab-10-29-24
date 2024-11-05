import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class Gun extends JPanel {
    private BufferedImage sprite;
    private int x;
    private int y;

    Gun() {
        try {
            sprite = ImageIO.read(new File("src/gun.png"));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.x = 0;
        this.y = 0;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //sprite = imageStuff.rotate(this.sprite, 3.0);
        g.drawImage(this.sprite, this.x, this.y, this);
    }
}
