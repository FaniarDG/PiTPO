import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<int[]> rounds = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            int[] dice = Arrays.stream(line.split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            rounds.add(dice);

            if (rounds.size() == 13) {
                int[] bestScores = calculateBestScores(rounds);
                printResults(bestScores);
                rounds.clear();
            }
        }
    }

    private static int[] calculateBestScores(List<int[]> rounds) {
        int[] bestScores = new int[13];
        boolean[] usedCategories = new boolean[13];

        for (int[] dice : rounds) {
            int bestCategory = -1;
            int bestScore = -1;

            for (int cat = 0; cat < 13; cat++) {
                if (usedCategories[cat]) continue;

                int score = calculateCategoryScore(dice, cat);
                if (score > bestScore) {
                    bestScore = score;
                    bestCategory = cat;
                }
            }

            if (bestCategory != -1) {
                bestScores[bestCategory] = bestScore;
                usedCategories[bestCategory] = true;
            }
        }

        return bestScores;
    }

    private static int calculateCategoryScore(int[] dice, int category) {
        int[] counts = new int[7];
        for (int num : dice) counts[num]++;

        switch (category) {
            case 0: return sumDiceOfValue(dice, 1);
            case 1: return sumDiceOfValue(dice, 2);
            case 2: return sumDiceOfValue(dice, 3);
            case 3: return sumDiceOfValue(dice, 4);
            case 4: return sumDiceOfValue(dice, 5);
            case 5: return sumDiceOfValue(dice, 6);
            case 6: return Arrays.stream(dice).sum(); // Шанс
            case 7: return hasNOfAKind(counts, 3) ? Arrays.stream(dice).sum() : 0;
            case 8: return hasNOfAKind(counts, 4) ? Arrays.stream(dice).sum() : 0;
            case 9: return isShortStraight(counts) ? 25 : 0;
            case 10: return isLongStraight(counts) ? 35 : 0;
            case 11: return isFullHouse(counts) ? 40 : 0;
            case 12: return isYahtzee(counts) ? 50 : 0;
            default: return 0;
        }
    }

    private static int sumDiceOfValue(int[] dice, int value) {
        return Arrays.stream(dice).filter(x -> x == value).sum();
    }

    private static boolean hasNOfAKind(int[] counts, int n) {
        return Arrays.stream(counts).anyMatch(c -> c >= n);
    }

    private static boolean isShortStraight(int[] counts) {
        return (counts[1] > 0 && counts[2] > 0 && counts[3] > 0 && counts[4] > 0) ||
                (counts[2] > 0 && counts[3] > 0 && counts[4] > 0 && counts[5] > 0) ||
                (counts[3] > 0 && counts[4] > 0 && counts[5] > 0 && counts[6] > 0);
    }

    private static boolean isLongStraight(int[] counts) {
        return (counts[1] > 0 && counts[2] > 0 && counts[3] > 0 && counts[4] > 0 && counts[5] > 0) ||
                (counts[2] > 0 && counts[3] > 0 && counts[4] > 0 && counts[5] > 0 && counts[6] > 0);
    }

    private static boolean isFullHouse(int[] counts) {
        boolean hasThree = false;
        boolean hasTwo = false;
        for (int count : counts) {
            if (count == 3) hasThree = true;
            if (count == 2) hasTwo = true;
        }
        return hasThree && hasTwo;
    }

    private static boolean isYahtzee(int[] counts) {
        return Arrays.stream(counts).anyMatch(c -> c == 5);
    }

    private static void printResults(int[] scores) {
        int upperSum = IntStream.range(0, 6).map(i -> scores[i]).sum();
        int bonus = upperSum >= 63 ? 35 : 0;
        int total = IntStream.of(scores).sum() + bonus;

        System.out.println(
                Arrays.stream(scores).mapToObj(String::valueOf)
                        .collect(Collectors.joining(" ")) +
                        " " + bonus + " " + total
        );
    }
}