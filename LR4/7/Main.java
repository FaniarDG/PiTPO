import java.util.*;

public class FifteenPuzzle {
    private static final int[][] GOAL = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    static class State {
        int[][] board;
        int zeroRow, zeroCol;
        int cost;
        String moves;

        State(int[][] board, int zeroRow, int zeroCol, int cost, String moves) {
            this.board = new int[4][4];
            for (int i = 0; i < 4; i++)
                System.arraycopy(board[i], 0, this.board[i], 0, 4);
            this.zeroRow = zeroRow;
            this.zeroCol = zeroCol;
            this.cost = cost;
            this.moves = moves;
        }

        boolean isGoal() {
            return Arrays.deepEquals(board, GOAL);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            State state = (State) obj;
            return Arrays.deepEquals(board, state.board);
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(board);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] initial = new int[4][4];
        int zeroRow = 0, zeroCol = 0;

        System.out.println("Введите начальное состояние (0 - пустая клетка):");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                initial[i][j] = scanner.nextInt();
                if (initial[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }

        if (!isSolvable(initial)) {
            System.out.println("Это состояние не имеет решения!");
            return;
        }

        String solution = solvePuzzle(initial, zeroRow, zeroCol);
        System.out.println("Решение: " + solution);
    }

    private static boolean isSolvable(int[][] board) {
        int inversions = 0;
        int[] flat = new int[16];
        int k = 0;
        int zeroRow = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                flat[k++] = board[i][j];
                if (board[i][j] == 0) zeroRow = i;
            }
        }

        for (int i = 0; i < 15; i++) {
            for (int j = i + 1; j < 16; j++) {
                if (flat[i] != 0 && flat[j] != 0 && flat[i] > flat[j]) {
                    inversions++;
                }
            }
        }

        return (zeroRow % 2 == 1) == (inversions % 2 == 0);
    }

    private static String solvePuzzle(int[][] initial, int zeroRow, int zeroCol) {
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(s -> s.cost));
        Set<State> visited = new HashSet<>();
        queue.add(new State(initial, zeroRow, zeroCol, 0, ""));

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        String[] moveNames = {"U", "D", "L", "R"};

        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.isGoal()) return current.moves;
            if (visited.contains(current)) continue;
            visited.add(current);

            for (int i = 0; i < 4; i++) {
                int newRow = current.zeroRow + dirs[i][0];
                int newCol = current.zeroCol + dirs[i][1];
                if (newRow < 0 || newRow >= 4 || newCol < 0 || newCol >= 4) continue;

                int[][] newBoard = new int[4][4];
                for (int x = 0; x < 4; x++)
                    System.arraycopy(current.board[x], 0, newBoard[x], 0, 4);

                newBoard[current.zeroRow][current.zeroCol] = newBoard[newRow][newCol];
                newBoard[newRow][newCol] = 0;

                State newState = new State(newBoard, newRow, newCol, current.cost + 1, current.moves + moveNames[i]);
                if (!visited.contains(newState)) {
                    queue.add(newState);
                }
            }
        }
        return "No solution found";
    }
}