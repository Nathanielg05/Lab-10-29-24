import java.awt.*;
import java.util.Random;

public class Bullet {
    private int x, y;
    private int resetPosX, resetPosY;
    public double xVelocity = 5;
    public double yVelocity = 5;
    private int size = 10;
    private boolean slow = false;
    private boolean speed = false;
    private boolean inverse = false;
    private Color color;
    public int bounceCooldown;

    public Bullet(double xVelocity, double yVelocity) {
        Random rand = new Random();
        resetPosX = 175;
        resetPosY = 175;
        this.x = (int)x;
        this.y = (int)y;
        if (rand.nextBoolean()) {
            speed = true;
            this.color = color.GREEN;
        }else{
            slow = true;
            this.color = color.RED;
        }/*else{
            inverse = true;
        }*/

    }

    public void move(){

        /*if (Math.abs(this.yVelocity) < 1) {
            this.yVelocity += 1;
        }*/
        this.x += xVelocity;
        this.y += yVelocity;

        if (this.x >= 400) {
            xVelocity = -xVelocity;
        } else if (x <= 0) {
            xVelocity = -xVelocity;
        }
        if (this.y <= 5) {
            resetBullet();
        }
        else if (y >= 400) {
            resetBullet();
        }

    }

    public void resetBullet(){
        this.x = resetPosX;
        this.y = resetPosY;
        this.xVelocity = 0;
        this.yVelocity = 0;

        speed = false;
        slow = false;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            speed = true;
        }else{
            slow = true;
        }

    }

    public static void populateBullets(Bullet[] bullets, int x, int y, int i){
        if (i >= 0){
            bullets[i] = new Bullet(x, y);
            i--;
            Bullet.populateBullets( bullets, x, y, i);
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(this.color);
        g.fillOval(x, y, this.size, this.size);
    }

    public float getX(){return this.x;}
    public float getY(){return this.y;}
    public boolean isSlow(){return this.slow;}
    public boolean isSpeed(){return this.speed;}
    public boolean isInverse(){return this.inverse;}
}
