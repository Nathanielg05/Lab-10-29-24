import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ScorePanel extends JFrame {
    private JTextArea scoreArea;

    public ScorePanel() {
        setTitle("Scores");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null); // panel appears on center of the screen

        scoreArea = new JTextArea();
        scoreArea.setEditable(false);
        scoreArea.setFont(new Font("Arial", Font.PLAIN, 14));
        add(new JScrollPane(scoreArea), BorderLayout.CENTER);
    }

    public void updateScores(List<Score> scores) {
        scoreArea.setText(""); // Clears previous scores
        for (Score score : scores) {
            scoreArea.append(score.gamenumber + " " + score.name + " " + score.score + "\n");
        }
    }

    public void toggleVisibility() {
        setVisible(!isVisible());
    }
}
