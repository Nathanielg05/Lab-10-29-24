import java.awt.Color;
import java.awt.Graphics2D;

public class Player {
    private int x = 180;
    private int y = 350;
    private boolean moveLeftFlag = false;
    private boolean moveRightFlag = false;
    private boolean boostFlag = false;
    private boolean boostActiveFlag = false;
    private int speed = 5;

    public void Player(){}

    public void move() {

        boostActiveFlag = speed > 5;

        if (boostFlag && boostActiveFlag){
            x += speed; //boosts character to the right
        }else if (!boostFlag && boostActiveFlag){
            x -= speed; //boost character to the left
        }else{
            if (moveLeftFlag) {
                x -= speed; // moves character to the left
            }
            if (moveRightFlag) {
                x += speed; //moves character to the right
            }
        }

        if (x < 0) x = 0;
        if (x > 350) x = 350;
        if (speed > 5){speed -= 2;}
    }
    public void boost() {
        speed = 20;
        boostActiveFlag = true;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(this.x, this.y, 70, 10);
    }
    public void setboostflag(boolean flag){
        this.boostFlag = flag;
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

    public void setBoostFlag(boolean boostFlag) {
        this.boostFlag = boostFlag;
    }

    public void setMoveLeftFlag(boolean moveLeftFlag) {
        this.moveLeftFlag = moveLeftFlag;
    }
    public void setMoveRightFlag(boolean moveRightFlag) {
        this.moveRightFlag = moveRightFlag;
    }
    public boolean getMoveLeftFlag() {
        return moveLeftFlag;
    }
    public boolean getMoveRightFlag() {
        return moveRightFlag;
    }
}
