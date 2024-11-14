import java.awt.Color;
import java.awt.Graphics2D;

public class Ball {
    private int x = 200; //The ball will be at this X position when the game starts 
    private int y = 200; //The ball will be at this Y position when the game starts 
    private int size = 20;
    private int playerScore = 0;
    private int opponentScore = 0;
    public int xVelocity, yVelocity;
    public int bounceCooldown;


    private long respawnTime;
    private boolean timerStarted;
    private boolean missedTop;

    public Ball(int xVelocity, int yVelocity) {
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    public void move() {
        if (!timerStarted) {
            this.y += yVelocity;
            this.x += xVelocity;
            if (this.y >= 390 || y <= -30) {
                timerStarted = true;
                respawnTime = System.currentTimeMillis() + 2000; //In 2 sec the ball respawns 
            }
            //reversing the direction for hitting the sides
            if (this.x >= 390) {
                xVelocity = -xVelocity;
            } else if (x <= 0) {
                xVelocity = -xVelocity;
            }
            if (this.y >= 390){ //check to see if the ball went through the top
                missedTop = true;
            } else {
                missedTop = false;
            }

        }else{
            if (System.currentTimeMillis() >= respawnTime) {
                resetPosition(); // Reset the ball's position
                timerStarted = false; // Reset the timer
                if (!missedTop) {playerScore++;}
                if(missedTop){opponentScore++;}
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

    public void resetPosition() { //The ball will respawn at the other side of the field to allow the losing playr more time to catch the ball 
        if(missedTop){
            this.x = 200;
            this.y = 100;
        } else if (!missedTop) {
            this.x = 200;
            this.y = 300;
        }
    }
}
