import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class FibonacciNumbersInRange {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<BigInteger> fibs = generateFibonacciUpTo1e100();

        while (true) {
            String aStr = scanner.next();
            String bStr = scanner.next();

            BigInteger a = new BigInteger(aStr);
            BigInteger b = new BigInteger(bStr);

            if (a.equals(BigInteger.ZERO) && b.equals(BigInteger.ZERO)) {
                break;
            }

            int count = 0;
            for (BigInteger fib : fibs) {
                if (fib.compareTo(a) >= 0 && fib.compareTo(b) <= 0) {
                    count++;
                }
                if (fib.compareTo(b) > 0) {
                    break;
                }
            }
            System.out.println(count);
        }
        scanner.close();
    }

    private static ArrayList<BigInteger> generateFibonacciUpTo1e100() {
        ArrayList<BigInteger> fibs = new ArrayList<>();
        fibs.add(BigInteger.ONE);
        fibs.add(BigInteger.TWO);     

        BigInteger limit = new BigInteger("10").pow(100);
        while (true) {
            BigInteger nextFib = fibs.get(fibs.size() - 1).add(fibs.get(fibs.size() - 2));
            if (nextFib.compareTo(limit) > 0) {
                break;
            }
            fibs.add(nextFib);
        }
        return fibs;
    }
}