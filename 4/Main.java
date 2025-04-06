import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int K = Integer.parseInt(br.readLine());

        for (int k = 0; k < K; k++) {
            if (k > 0) System.out.println();

            int n = Integer.parseInt(br.readLine());
            String[] initial = new String[n];
            String[] desired = new String[n];

            for (int i = 0; i < n; i++) initial[i] = br.readLine();
            for (int i = 0; i < n; i++) desired[i] = br.readLine();

            List<String> operations = new ArrayList<>();
            int desiredPos = 0;
            int currentPos = 0;

            while (desiredPos < n) {
                if (currentPos < n && initial[currentPos].equals(desired[desiredPos])) {
                    desiredPos++;
                    currentPos++;
                } else {
                    int found = -1;
                    for (int i = currentPos + 1; i < n; i++) {
                        if (initial[i].equals(desired[desiredPos])) {
                            found = i;
                            break;
                        }
                    }
                    if (found != -1) {
                        operations.add(initial[found]);
                        String temp = initial[found];
                        for (int i = found; i > currentPos; i--) {
                            initial[i] = initial[i - 1];
                        }
                        initial[currentPos] = temp;
                    }
                    desiredPos++;
                }
            }

            for (String op : operations) {
                System.out.println(op);
            }
        }
    }
}