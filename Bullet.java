import java.awt.*;
import java.util.Random;

public class Bullet {
    private int x, y;
    private int resetPosX, resetPosY;
    private int xVelocity = 5;
    private int yVelocity = 5;
    private int size = 10;
    private boolean slow = false;
    private boolean speed = false;
    private boolean inverse = false;

    public Bullet(int x, int y) {
        Random rand = new Random();
        yVelocity = rand.nextInt((1)+5);
        xVelocity = rand.nextInt((1)+5);
        resetPosX = x;
        resetPosY = y;
        this.x = (int)x;
        this.y = (int)y;
        /*if (rand.nextBoolean()) {
            speed = true;
        }else{
            slow = true;
        }/*else{
            inverse = true;
        }*/

        speed = true;
    }

    public void move(){

        this.x += xVelocity;
        this.y += yVelocity;

        if (this.x >= 390) {
            xVelocity *= -1;
        } else if (x <= 0) {
            xVelocity *= -1;
        }
        if (y <= 5){ //check to see if the ball went through the top
            yVelocity *= -1;
        } else if(y >= 350){
            yVelocity *= -1;
        }
    }

    public void resetBullet(){
        this.x = resetPosX;
        this.y = resetPosY;
    }

    public static void populateBullets(Bullet[] bullets, int x, int y, int i){
        if (i >= 0){
            bullets[i] = new Bullet(x, y);
            i--;
            Bullet.populateBullets( bullets, x, y, i);
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, this.size, this.size);
    }

    public float getX(){return this.x;}
    public float getY(){return this.y;}
    public boolean isSlow(){return this.slow;}
    public boolean isSpeed(){return this.speed;}
    public boolean isInverse(){return this.inverse;}
}
