import java.util.*;

public class TouristTransport {
    static class Edge {
        int to;
        int capacity;

        Edge(int to, int capacity) {
            this.to = to;
            this.capacity = capacity;
        }
    }

    static class Node implements Comparable<Node> {
        int city;
        int maxCapacity;

        Node(int city, int maxCapacity) {
            this.city = city;
            this.maxCapacity = maxCapacity;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(other.maxCapacity, this.maxCapacity);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int scenario = 1;

        while (true) {
            int N = scanner.nextInt();
            int R = scanner.nextInt();
            if (N == 0 && R == 0) break;

            List<List<Edge>> graph = new ArrayList<>();
            for (int i = 0; i <= N; i++) {
                graph.add(new ArrayList<>());
            }

            for (int i = 0; i < R; i++) {
                int C1 = scanner.nextInt();
                int C2 = scanner.nextInt();
                int P = scanner.nextInt();
                graph.get(C1).add(new Edge(C2, P));
                graph.get(C2).add(new Edge(C1, P));
            }

            int S = scanner.nextInt();
            int D = scanner.nextInt();
            int T = scanner.nextInt();

            int[] maxCapacity = new int[N + 1];
            Arrays.fill(maxCapacity, -1);
            maxCapacity[S] = Integer.MAX_VALUE;

            PriorityQueue<Node> pq = new PriorityQueue<>();
            pq.add(new Node(S, maxCapacity[S]));

            while (!pq.isEmpty()) {
                Node current = pq.poll();
                if (current.city == D) break;

                for (Edge edge : graph.get(current.city)) {
                    int newCapacity = Math.min(current.maxCapacity, edge.capacity);
                    if (newCapacity > maxCapacity[edge.to]) {
                        maxCapacity[edge.to] = newCapacity;
                        pq.add(new Node(edge.to, newCapacity));
                    }
                }
            }

            int minTrips = (int) Math.ceil((double) T / (maxCapacity[D] - 1));
            System.out.println("Scenario #" + scenario);
            System.out.println("Minimum Number of Trips = " + minTrips);
            System.out.println();
            scenario++;
        }
        scanner.close();
    }
}