import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int m = n * (n - 1) / 2;
            int[] sums = new int[m];
            for (int i = 0; i < m; i++) {
                sums[i] = sc.nextInt();
            }
            Arrays.sort(sums);

            int[] result = solve(n, sums);
            if (result == null) {
                System.out.println("Impossibleeee");
            } else {
                for (int num : result) {
                    System.out.print(num + " ");
                }
                System.out.println();
            }
        }
    }

    private static int[] solve(int n, int[] sums) {
        int[] res = new int[n];
        res[0] = (sums[0] + sums[1] - sums[n-1]) / 2;

        for (int i = 1; i < n; i++) {
            res[i] = sums[i-1] - res[0];
        }

        if (!validate(res, sums)) {
            return null;
        }
        Arrays.sort(res);
        return res;
    }

    private static boolean validate(int[] nums, int[] expectedSums) {
        List<Integer> actualSums = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                actualSums.add(nums[i] + nums[j]);
            }
        }
        Collections.sort(actualSums);

        int[] expected = expectedSums.clone();
        Arrays.sort(expected);

        if (actualSums.size() != expected.length) {
            return false;
        }

        for (int i = 0; i < expected.length; i++) {
            if (actualSums.get(i) != expected[i]) {
                return false;
            }
        }
        return true;
    }
}