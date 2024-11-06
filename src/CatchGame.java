import java.awt.Graphics;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CatchGame extends JPanel {
    private final Player player;
    private final Player opponent;
    private final ArrayList<Ball> balls;
    private final Gun gun;
    private int score = 0;
    private Image backgroundImage;
    int timeMS;

    public CatchGame(JFrame frame) {
        player = new Player(180, 350);
        opponent = new Player(180, 10);
        balls = new ArrayList<>();
        System.out.println(this.getHeight());
        gun = new Gun((frame.getHeight() / 2) - 50, (frame.getWidth() / 2) - 50);

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
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    opponent.setMoveLeftFlag(true);
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
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

                if (e.getKeyCode() == KeyEvent.VK_D) {
                    opponent.setMoveLeftFlag(false);
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
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

    public void update() {
        player.move();
        opponent.move();
        gun.rotate();

        for (Ball ball : balls) {
            ball.move();
            int playerCollision = player.catchBall(ball);
            int opponentCollision = opponent.catchBall(ball);

            // Check player collision
            if (playerCollision == 1 || opponentCollision == 1) { // Top/bottom collision
                ball.yVelocity = -ball.yVelocity;
                score++;
            } else if (playerCollision == 2 || opponentCollision == 2) { // Left/right collision
                ball.xVelocity = -ball.xVelocity;
                score++;
            }
        }

        // Add a new ball if the conditions are met
        if (Math.random() < 0.01 && balls.size() < 1) {
            balls.add(new Ball(5, 5));
        }




    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        player.draw(g2d);
        opponent.draw(g2d);
        for (Ball ball : balls) {
            ball.draw(g2d);
        }
        g2d.drawString("Score: " + score, 10, 50);
        gun.paintComponent(g);
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("JaPong");
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
                game.update();
            }

            Thread.sleep(1);
        }
    }
}
