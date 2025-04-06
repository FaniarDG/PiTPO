import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int i = sc.nextInt();
        int j = sc.nextInt();

        System.out.print(i);
        int max_cnt = calculateMaxSequenceLength(i, j);
        System.out.print(" " + j + " " + max_cnt);
    }

    private static int calculateSequenceLength(int n) {
        int cnt = 0;
        while (n != 1) {
            n = (n % 2 == 0) ? n / 2 : n * 3 + 1;
            cnt++;
        }
        return cnt + 1;
    }

    private static int calculateMaxSequenceLength(int i, int j) {
        int max_cnt = 0;
        for (int current = i; current <= j; current++) {
            int currentLength = calculateSequenceLength(current);
            if (currentLength > max_cnt) {
                max_cnt = currentLength;
            }
        }
        return max_cnt;
    }
}