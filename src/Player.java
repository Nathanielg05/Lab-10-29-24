import java.awt.Color;
import java.awt.Graphics2D;

public class Player {
    private int x = 180;
    private int y = 350;
    private int direction = 0;
    private boolean moveLeftFlag = false;
    private boolean moveRightFlag = false;
    private boolean boostFlag = false;
    private int speed = 5;

    public Player() {
    }

    public void move() {
        this.x += this.direction * 5;

        if (boostFlag && speed > 5){
            x += speed;
        }else if(!boostFlag && speed > 5){
            x -= speed;
        }else{
            if (moveLeftFlag){
                x += speed;
            }
            if (moveRightFlag){
                x -= speed;
            }
        } 
        
        if (this.x < 0) {
            this.x = 0;
        }

        if (this.x > 350) {
            this.x = 350;
        }

    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(this.x, this.y, 70, 10);
    }
    public void setboostflag(boolean flag){
        this.boostFlag = flag;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int catchBall(Ball ball) {
        int ballRadius = 20; // Assuming radius of 20 for the ball
        int ballTopY = ball.getY() - ballRadius; // Top of the ball
        int ballBottomY = ball.getY() + ballRadius; // Bottom of the ball
        int ballLeftX = ball.getX() - ballRadius; // Left of the ball
        int ballRightX = ball.getX() + ballRadius; // Right of the ball

        boolean hitTopOrBottom = ballBottomY >= this.y && ballTopY <= this.y + 20;
        boolean hitLeftOrRight = ballRightX >= this.x && ballLeftX <= this.x + 50;

        if (hitTopOrBottom && hitLeftOrRight) {
            // Calculate distances to each side to determine collision type
            int distanceToTop = Math.abs(ballBottomY - this.y);
            int distanceToBottom = Math.abs(ballTopY - (this.y + 20));
            int distanceToLeft = Math.abs(ballRightX - this.x);
            int distanceToRight = Math.abs(ballLeftX - (this.x + 50));

            // Determine the closest side and return corresponding collision type
            if (distanceToTop < distanceToBottom && distanceToTop < distanceToLeft && distanceToTop < distanceToRight) {
                return 1; // Top or bottom collision
            } else if (distanceToBottom < distanceToTop && distanceToBottom < distanceToLeft && distanceToBottom < distanceToRight) {
                return 1; // Top or bottom collision
            } else if (distanceToLeft < distanceToTop && distanceToLeft < distanceToBottom && distanceToLeft < distanceToRight) {
                return 2; // Left or right collision
            } else if (distanceToRight < distanceToTop && distanceToRight < distanceToBottom && distanceToRight < distanceToLeft) {
                return 2; // Left or right collision
            }
        }
        return 0; // No collision
    }
}
