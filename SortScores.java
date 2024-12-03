import java.util.List;


public class SortScores {
    public static void quickSort(List<String> scores, boolean sortByPlayer) {
        if (scores == null || scores.size() <= 1) return; // No need to sort
        quickSort(scores, 0, scores.size() - 1, sortByPlayer);
    }

    private static void quickSort(List<String> scores, int low, int high, boolean sortByPlayer) {
        if (low < high) {
            int pi = partition(scores, low, high, sortByPlayer);
            quickSort(scores, low, pi - 1, sortByPlayer); // Left
            quickSort(scores, pi + 1, high, sortByPlayer); // Right
        }
    }

    private static int partition(List<String> scores, int low, int high, boolean sortByPlayer) {
        String pivot = scores.get(high); // Use the last element as pivot
        int pivotValue = extractScore(pivot, sortByPlayer);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            int currentValue = extractScore(scores.get(j), sortByPlayer);
            if (currentValue > pivotValue) { // Sort descending
                i++;
                swap(scores, i, j);
            }
        }
        swap(scores, i + 1, high);
        return i + 1;
    }

    private static void swap(List<String> scores, int i, int j) {
        String temp = scores.get(i);
        scores.set(i, scores.get(j));
        scores.set(j, temp);
    }

    private static int extractScore(String score, boolean isPlayer) {
        String[] parts = score.split("\\|");
        if (parts.length != 2) return 0;

        String targetPart = isPlayer ? parts[0] : parts[1];
        String scoreValue = targetPart.replaceAll("[^0-9]", ""); // Extract digits
        return Integer.parseInt(scoreValue);
    }
}
