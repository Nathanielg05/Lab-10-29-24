import java.awt.Graphics;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
TODO:
Fix top player collision
Make gun fire bullets
Implement Debuffs
 */

public class CatchGame extends JPanel {
    private final Player player;
    private final Player opponent;
    private final Ball ball;
    private final Gun gun;
    private Bullet[] bulletArray;
    private int score = 0;
    private Image backgroundImage;
    int timeMS;


    public CatchGame(JFrame frame) {
        player = new Player(180, 350);
        opponent = new Player(180, 10);
        ball = new Ball(0, 0, frame);
        System.out.println(this.getHeight());
        gun = new Gun((frame.getHeight() / 2) - 50, (frame.getWidth() / 2) - 50);
        bulletArray = new Bullet[5];
        Bullet.populateBullets(bulletArray, (frame.getHeight() / 2) ,(frame.getWidth() / 2) , 4);

        KeyListener listener = new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.setMoveLeftFlag(true);
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.setMoveRightFlag(true);
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE && player.getMoveLeftFlag()){
                    player.boost();
                    player.setBoostFlag(false);
                }else if (e.getKeyCode() == KeyEvent.VK_SPACE && player.getMoveRightFlag()){
                    player.boost();
                    player.setBoostFlag(true);
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    opponent.setMoveLeftFlag(true);
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    opponent.setMoveRightFlag(true);
                }
                if (e.getKeyCode() == KeyEvent.VK_SHIFT && opponent.getMoveLeftFlag()){
                    opponent.boost();
                    opponent.setBoostFlag(false);
                }else if (e.getKeyCode() == KeyEvent.VK_SHIFT && opponent.getMoveRightFlag()){
                    opponent.boost();
                    opponent.setBoostFlag(true);
                }

                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    gun.rotateFlag = -1;
                }
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    gun.rotateFlag = 1;
                }


            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.setMoveLeftFlag(false);
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.setMoveRightFlag(false);
                }

                if (e.getKeyCode() == KeyEvent.VK_A) {
                    opponent.setMoveLeftFlag(false);
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    opponent.setMoveRightFlag(false);
                }

                if (e.getKeyCode() == KeyEvent.VK_Q || e.getKeyCode() == KeyEvent.VK_E) {
                    gun.rotateFlag = 0;
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {}
        };

        addKeyListener(listener);
        setFocusable(true);
    }

    public void update(JFrame frame) {
        player.move();
        opponent.move();
        if (gun.rotation != gun.targetRotation) {
            gun.rotate();

        }
        else {
            // Gun has reached target rotation, ready to fire
            // Handle Gun Firing Here

            // Find new target rotation & Start Cooldown
            gun.rotCooldownTimer.restart();
            gun.rotCooldown = 100;
            gun.setRotate();
            if (ball.initFlag == false) {
                ball.initFlag = true;
                ball.resetPosition(frame);
            }
        }

            ball.move(frame);
            int playerCollision = player.catchBallTop(ball);
            int opponentCollision = opponent.catchBallTop(ball);

            // Check player collision
            if (playerCollision == 1 || opponentCollision == 1) { // Top/bottom collision
                if (ball.bounceCooldown == 0) {
                    ball.yVelocity *= -1;
                    score++;
                    ball.bounceCooldown = 15;
                }
            } else if (playerCollision == 2 || opponentCollision == 2) { // Left/right collision
                if (ball.bounceCooldown == 0) {
                    ball.xVelocity *= -1;
                    score++;
                    ball.bounceCooldown = 15;
                }
            }

            if (ball.bounceCooldown > 0) {
                ball.bounceCooldown--;
            }


        // Add a new ball if the conditions are met
        /*if (Math.random() < 0.01 && balls.size() < 1) {
            balls.add(new Ball(3, 3));
        }*/




    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        player.draw(g2d);
        opponent.draw(g2d);
        ball.draw(g2d);
        g2d.drawString("Score: " + score, 10, 50);
        gun.paintComponent(g);
        for (int i = 0; i < bulletArray.length; i++) {
            bulletArray[i].draw(g2d);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("PwiC");
        // double size window to make game more visually friendly
        frame.setSize(400, 400);
        CatchGame game = new CatchGame(frame);
        frame.add(game);
        frame.setVisible(true);
        game.setBackground(Color.BLACK);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            game.timeMS++;
            if (game.timeMS%15 == 0) {
                game.repaint();
            }
            if (game.timeMS%10 == 0) {
                game.update(frame);
            }

            Thread.sleep(1);
        }
    }
}
