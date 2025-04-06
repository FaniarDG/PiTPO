import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Чтение словаря
        Set<String> dictionary = new HashSet<>();
        String line;
        while (!(line = reader.readLine()).isEmpty()) {
            dictionary.add(line.trim());
        }

        boolean firstBlock = true;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            if (!firstBlock) {
                System.out.println();
            }
            firstBlock = false;

            String[] pair = line.split(" ");
            String start = pair[0];
            String end = pair[1];

            List<String> chain = findShortestChain(dictionary, start, end);

            if (chain == null) {
                System.out.println("Нет решения");
            } else {
                for (String word : chain) {
                    System.out.println(word);
                }
            }
        }
    }

    private static List<String> findShortestChain(Set<String> dictionary, String start, String end) {
        if (!dictionary.contains(start) || !dictionary.contains(end)) {
            return null;
        }

        Queue<List<String>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        List<String> initialChain = new ArrayList<>();
        initialChain.add(start);
        queue.add(initialChain);
        visited.add(start);

        while (!queue.isEmpty()) {
            List<String> currentChain = queue.poll();
            String lastWord = currentChain.get(currentChain.size() - 1);

            if (lastWord.equals(end)) {
                return currentChain;
            }

            for (String neighbor : getNeighbors(lastWord, dictionary)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    List<String> newChain = new ArrayList<>(currentChain);
                    newChain.add(neighbor);
                    queue.add(newChain);
                }
            }
        }

        return null;
    }

    private static List<String> getNeighbors(String word, Set<String> dictionary) {
        List<String> neighbors = new ArrayList<>();
        char[] chars = word.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char originalChar = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != originalChar) {
                    chars[i] = c;
                    String newWord = new String(chars);
                    if (dictionary.contains(newWord)) {
                        neighbors.add(newWord);
                    }
                }
            }
            chars[i] = originalChar;
        }

        return neighbors;
    }
}