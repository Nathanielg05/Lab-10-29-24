import java.awt.Color;
import java.awt.Graphics2D;

//ball gets stuck on the x axis sometimes

public class Ball {
    private int x = 175;
    private int y = 175;
    public float speed = 1;
    public int size = 20;
    private int playerScore = 0;
    private int opponentScore = 0;
    public double xVelocity, yVelocity;
    public int bounceCooldown;
    public Boolean initFlag = false;


    private long respawnTime;
    private boolean timerStarted;

    public Ball(int xVelocity, int yVelocity) {
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    public void move() {
        if (!timerStarted) {
            this.y += yVelocity*speed;
            this.x += xVelocity*speed;
            if (this.initFlag) {
                if (Math.abs(this.yVelocity) < 1) {
                    this.yVelocity = Math.ceil(this.yVelocity);
                }
                if (Math.abs(this.xVelocity) < 1) {
                    this.xVelocity = Math.ceil(this.xVelocity);
                }
                if (this.xVelocity == -0.0) {
                    this.xVelocity = 0.2;
                }
                if (this.yVelocity == -0.0) {
                    this.yVelocity = 0.2;
                }
            }
            if (this.y >= 400 || y <= -30) {
                timerStarted = true;
                respawnTime = System.currentTimeMillis() + 2000;
                if (y <= -30) {
                    playerScore++;
                }
                else {
                    opponentScore++;
                }
            }
            //reversing the direction for hitting the sides
            if (this.x >= (400-this.size)) {
                xVelocity = -xVelocity;
            } else if (x <= 0) {
                xVelocity = -xVelocity;
            }

        }else {
            if (System.currentTimeMillis() >= respawnTime) {
                resetPosition(); // Reset the ball's position
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

    public int getPlayerScore() { //internal score for player
        return this.playerScore;
    }
    public int getOpponentScore() { //internal score for opponent
        return this.opponentScore;
    }

    public void resetPosition() {
        this.x = 175;
        this.y = 175;
        this.speed = 1;
        this.initFlag = false;
        this.xVelocity = 0;//Gun.convertDegToMomentum(Gun.rotation-270, 'x');
        this.yVelocity = 0;//Gun.convertDegToMomentum(Gun.rotation-270, 'y');
    }
}
