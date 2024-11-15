import java.util.LinkedList;
import java.util.List;

public class ScoreBoard {
    private final LinkedList<String> scores;
    private int gameNumber;

    public ScoreBoard() {
        scores = new LinkedList<>();
        gameNumber = 1; //starting game
    }

    public void addScore(String score) {
        String gameScore = "Game " + gameNumber + ": " + score;
        scores.add(gameScore);
        gameNumber++;
    }

    public List<String> getScores() {
        return new LinkedList<>(scores); //returns the list
    }
}
