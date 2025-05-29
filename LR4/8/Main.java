import java.util.*;

public class TurtleTower {
    static class Turtle {
        int weight;
        int strength;

        Turtle(int weight, int strength) {
            this.weight = weight;
            this.strength = strength;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Turtle> turtles = new ArrayList<>();

        while (scanner.hasNextInt()) {
            int weight = scanner.nextInt();
            if (!scanner.hasNextInt()) break;
            int strength = scanner.nextInt();
            turtles.add(new Turtle(weight, strength));
        }

        turtles.sort(Comparator.comparingInt(t -> t.weight + t.strength));

        int[] dp = new int[turtles.size() + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        int maxHeight = 0;

        for (Turtle turtle : turtles) {
            for (int i = maxHeight; i >= 0; i--) {
                if (dp[i] <= turtle.strength && dp[i] + turtle.weight < dp[i + 1]) {
                    dp[i + 1] = dp[i] + turtle.weight;
                    if (i + 1 > maxHeight) {
                        maxHeight = i + 1;
                    }
                }
            }
        }

        System.out.println(maxHeight);
    }
}