import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class CatchGame extends JPanel {
    private Player player;
    private Player opponent;
    private Bullet[] bulletArray;
    private int scoreplayer = 0;
    private int scoreopponent = 0;
    private Ball ball;
    private final Gun gun;
    private Image backgroundImage;
    int timeMS;

    private final ScoreBoard scoreBoard;
    private final ScorePanel scorePanel;
    private boolean gameEnded = false;

    public CatchGame(JFrame frame) {
        player = new Player(150, 350);
        opponent = new Player(150, 10);
        ball = new Ball(2, 2);
        gun = new Gun(160, 165);

        scoreBoard = new ScoreBoard();
        scorePanel = new ScorePanel();

        bulletArray = new Bullet[5];
        Bullet.populateBullets(bulletArray, (frame.getHeight() / 2), (frame.getWidth() / 2), 4);

        KeyListener listener = new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (!gameEnded) { //Pause movement when game has ended
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        player.setMoveLeftFlag(true);
                    }
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        player.setMoveRightFlag(true);
                    }
                    if (e.getKeyCode() == KeyEvent.VK_SPACE && player.getMoveLeftFlag()) {
                        player.boost();
                        player.setBoostFlag(false);
                        player.setBoostActiveFlag(true);
                    } else if (e.getKeyCode() == KeyEvent.VK_SPACE && player.getMoveRightFlag()) {
                        player.boost();
                        player.setBoostFlag(true);
                        player.setBoostActiveFlag(true);
                    }
                    if (e.getKeyCode() == KeyEvent.VK_D) {
                        opponent.setMoveRightFlag(true);
                    }
                    if (e.getKeyCode() == KeyEvent.VK_A) {
                        opponent.setMoveLeftFlag(true);
                    }
                    if (e.getKeyCode() == KeyEvent.VK_SHIFT && opponent.getMoveLeftFlag()) {
                        opponent.boost();
                        opponent.setBoostFlag(false);
                        opponent.setBoostActiveFlag(true);
                    } else if (e.getKeyCode() == KeyEvent.VK_SHIFT && opponent.getMoveRightFlag()) {
                        opponent.boost();
                        opponent.setBoostFlag(true);
                        opponent.setBoostActiveFlag(true);
                    }
                    if (e.getKeyCode() == KeyEvent.VK_Q) {
                        gun.rotateFlag = -1;
                    }
                    if (e.getKeyCode() == KeyEvent.VK_E) {
                        gun.rotateFlag = 1;
                    }
                }

                // Toggle the ScorePanel with the 'M' key
                if (e.getKeyCode() == KeyEvent.VK_M) {
                    scorePanel.updateScores(scoreBoard.getScores()); // Update scores in ScorePanel
                    scorePanel.toggleVisibility();
                }

                // Check for 'N' key to start a new game
                if (e.getKeyCode() == KeyEvent.VK_N) {
                    resetGame();
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
                    opponent.setMoveRightFlag(false);
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    opponent.setMoveLeftFlag(false);
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

    private void resetGame() {
        player = new Player(150, 350);
        opponent = new Player(150, 10);
        ball = new Ball(2, 2);
        scoreplayer = 0;
        scoreopponent = 0;
        gameEnded = false;
        timeMS = 0;

        System.out.println("Game Reset! New game started.");
    }

    public void update() {
        if (gameEnded) return;

        player.move();
        opponent.move();

        scoreplayer = ball.getPlayerScore();
        scoreopponent = ball.getOpponentScore();

        if (gun.rotation != gun.targetRotation) {
            gun.rotate();
            System.out.println(gun.rotation + " " + gun.targetRotation);
        } else {
            gun.setRotate();
        }

        ball.move();
        int playerCollision = player.catchBallTop(ball);
        int opponentCollision = opponent.catchBallBottom(ball);

        if (playerCollision == 1 || opponentCollision == 1) { // Top/bottom collision
            if (ball.bounceCooldown == 0) {
                ball.yVelocity *= -1;
                ball.bounceCooldown = 15;
            }
        } else if (playerCollision == 2 || opponentCollision == 2) { // Left/right collision
            if (ball.bounceCooldown == 0) {
                ball.xVelocity *= -1;
                ball.bounceCooldown = 15;
            }
        }

        if (ball.bounceCooldown > 0) {
            ball.bounceCooldown--;
        }

        checkWinCondition();
    }

    private void checkWinCondition() {
        if (Math.abs(scoreplayer - scoreopponent) >= 2) {
            gameEnded = true;
            String result = "Player Score: " + scoreplayer + " | Opponent Score: " + scoreopponent;
            scoreBoard.addScore(result);
            System.out.println("Game Over! " + result);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        ImageIcon background = new ImageIcon("src/Pong.png");
        g.drawImage(background.getImage(), 0, 188, null);

        player.draw(g2d);
        opponent.draw(g2d);
        ball.draw(g2d);

        for (Bullet bullet : bulletArray) {
            bullet.draw(g2d);
            bullet.move();
        }

        g2d.drawString("Score: " + scoreplayer, 10, 210);
        g2d.drawString("Score: " + scoreopponent, 10, 180);
        gun.paintComponent(g);

        if (gameEnded) {
            g2d.drawString("Game Over! Press M to see scores, or N for new game", 30, 100);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("PwiC");
        frame.setSize(400, 400);
        CatchGame game = new CatchGame(frame);
        frame.add(game);
        frame.setVisible(true);
        game.setBackground(Color.BLACK);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            game.timeMS++;
            if (game.timeMS % 15 == 0) {
                game.repaint();
            }
            if (game.timeMS % 10 == 0) {
                game.update();
            }
            Thread.sleep(1);
        }
    }
}
