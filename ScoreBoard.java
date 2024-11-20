import java.util.LinkedList;
import java.util.List;

public class ScoreBoard {
    private final LinkedList<Score> scores;
    private int gameNumber;

    public ScoreBoard() {
        scores = new LinkedList<>();
        gameNumber = 1; //starting game
    }

    public void addScore(String score, String name) {
        Score gameScore = new Score(score, gameNumber, name);
        scores.add(gameScore);
        gameNumber++;
    }

    public List<Score> getScores() {
        return new LinkedList<>(scores); //returns the list
    }
}

