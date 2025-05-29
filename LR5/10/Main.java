import java.util.*;

public class BeadNecklace {
    static class Bead {
        int left, right;
        Bead(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        for (int t = 1; t <= T; t++) {
            int N = scanner.nextInt();
            List<Bead> beads = new ArrayList<>();
            Map<Integer, Integer> inDegree = new HashMap<>();
            Map<Integer, Integer> outDegree = new HashMap<>();
            Map<Integer, List<Bead>> graph = new HashMap<>();

            for (int i = 0; i < N; i++) {
                int left = scanner.nextInt();
                int right = scanner.nextInt();
                beads.add(new Bead(left, right));
                outDegree.put(left, outDegree.getOrDefault(left, 0) + 1);
                inDegree.put(right, inDegree.getOrDefault(right, 0) + 1);
                graph.computeIfAbsent(left, k -> new ArrayList<>()).add(new Bead(left, right));
            }

            boolean possible = true;
            for (int color : outDegree.keySet()) {
                if (!inDegree.getOrDefault(color, 0).equals(outDegree.get(color))) {
                    possible = false;
                    break;
                }
            }

            if (!possible) {
                System.out.println("CASE #" + t);
                System.out.println("impossibleee");
                if (t < T) System.out.println();
                continue;
            }

            List<Bead> path = new ArrayList<>();
            Stack<Bead> stack = new Stack<>();
            stack.push(beads.get(0));
            while (!stack.isEmpty()) {
                Bead current = stack.peek();
                List<Bead> edges = graph.get(current.left);
                if (edges != null && !edges.isEmpty()) {
                    Bead next = edges.remove(edges.size() - 1);
                    stack.push(next);
                } else {
                    path.add(stack.pop());
                }
            }

            Collections.reverse(path);
            if (path.size() != N || path.get(0).left != path.get(path.size() - 1).right) {
                System.out.println("CASE #" + t);
                System.out.println("impossibleee");
            } else {
                System.out.println("CASE #" + t);
                for (Bead bead : path) {
                    System.out.println(bead.left + " " + bead.right);
                }
            }
            if (t < T) System.out.println();
        }
        scanner.close();
    }
}