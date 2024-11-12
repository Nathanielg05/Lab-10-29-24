import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JFrame;

public class Ball {
    private int x;
    private int y;
    private int size = 20;
    public double xVelocity, yVelocity;
    public int bounceCooldown;
    public Boolean initFlag = false;

    private long respawnTime;
    private boolean timerStarted;
    private boolean missedTop;

    public Ball(double xVelocity, double yVelocity, JFrame frame) {
        this.x = frame.getWidth() / 2;
        this.y = frame.getHeight() / 2;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    public void move(JFrame frame) {
        if (!timerStarted) {
            this.y += yVelocity;
            this.x += xVelocity;
            if (this.y >= 390 || y <= -30) {
                timerStarted = true;
                respawnTime = System.currentTimeMillis() + 3000;
            }
            //reversing the direction for hitting the sides
            if (this.x >= 390) {
                xVelocity *= -1;
            } else if (x <= 0) {
                xVelocity *= -1;
            }
            if (this.y >= 390){ //check to see if the ball went through the top
                missedTop = true;
            } else {
                missedTop = false;
            }

        }else{
            if (System.currentTimeMillis() >= respawnTime) {
                resetPosition(frame); // Reset the ball's position
                timerStarted = false; // Reset the timer
            }
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval(this.x, this.y, this.size, this.size);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void resetPosition(JFrame frame) {
       if(missedTop){
           this.x = frame.getWidth() / 2;
           this.y = frame.getHeight() / 2;
       } else if (!missedTop) {
           this.x = frame.getWidth() / 2;
           this.y = frame.getHeight() / 2;
       }
       this.xVelocity = Gun.convertDegToMomentum(Gun.rotation-270, 'x');;
       this.yVelocity = Gun.convertDegToMomentum(Gun.rotation-270, 'y');
    }
}
