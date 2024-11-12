import java.awt.*;

public class Bullet {
    private int x, y;
    private int resetPosX, resetPosY;
    private int xVelocity = 5;
    private int yVelocity = 5;
    private int size = 10;

    public Bullet(int x, int y){
        this.resetPosX = x;
        this.resetPosY = y;
        this.x = (int)x;
        this.y = (int)y;
    }

    public void bulletMovement(){

        this.x += xVelocity;
        this.y += yVelocity;

        if (this.x >= 390) {
            xVelocity *= -1;
        } else if (x <= 0) {
            xVelocity *= -1;
        }
    }

    public void resetBullet(){
        this.x = (int)resetPosX;
        this.y = (int)resetPosY;
    }

    public static void populateBullets(Bullet[] bullets, int x, int y, int i){
        if (i > 0){
            i--;
            Bullet.populateBullets( bullets, x, y, i);
        }
        bullets[i] = new Bullet(x, y);
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, this.size, this.size);
    }

    public float getX(){return this.x;}
    public float getY(){return this.y;}
}
