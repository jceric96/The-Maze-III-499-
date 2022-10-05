import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MazeIIIBFS {

    public String shortestDistance(int[][] maze, int[] ball, int[] hole) {
        // BFS
        // Time:O(xLen * yLen)
        // Space:O(xLen * yLen)
        Queue<Integer> queue = new LinkedList<>();
        int xLen = maze.length, yLen = maze[0].length;
        // create a visitedMap same with maze
        // to record arriving at each position needs how many steps
        int[][] visitedMap = new int[xLen][yLen];

        // create a positionMap same with maze
        // to record arriving at each position how to walk
        String[][] positionMap = new String[xLen][yLen];

        // fill the Max integer into visitedmap as initial value
        // fill the "initial" into positionMap as initial value
        for (int i = 0; i < xLen; i++) {
            Arrays.fill(visitedMap[i], Integer.MAX_VALUE);
            Arrays.fill(positionMap[i], "initial");
        }
        // create two array to save dx and dy steps
        int[] dx = { 1, 0, -1, 0 };
        int[] dy = { 0, -1, 0, 1 };
        String[] direction = { "d", "l", "u", "r" };

        // add start position into queue
        queue.offer(ball[0] * yLen + ball[1]);

        // change start positon of visitedMap to 0
        // change start positon of positionMap to ""
        visitedMap[ball[0]][ball[1]] = 0;
        positionMap[ball[0]][ball[1]] = "";

        while (!queue.isEmpty()) {
            // get ball's current position
            int temp = queue.poll();
            int x = temp / yLen;
            int y = temp % yLen;
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                int add = 0;
                while (nx >= 0 && nx < xLen && ny >= 0 && ny < yLen && maze[nx][ny] == 0) {
                    // if ball drop in the hole
                    // break;
                    if (nx == hole[0] && ny == hole[1]) {
                        nx += dx[i];
                        ny += dy[i];
                        break;
                    }
                    // record steps
                    ++add;
                    nx += dx[i];
                    ny += dy[i];
                }
                // back one step to avoid out of bounds
                nx -= dx[i];
                ny -= dy[i];
                // compare with new path as lexicographically order
                boolean compare = (positionMap[x][y] + direction[i]).compareTo(positionMap[nx][ny]) < 0 ? true : false;
                boolean visited = false;

                // compare and save shortest steps, lexicographically order path
                if ((visitedMap[x][y] + add < visitedMap[nx][ny]) ||
                        (visitedMap[x][y] + add == visitedMap[nx][ny]) && compare) {
                    visitedMap[nx][ny] = visitedMap[x][y] + add;
                    positionMap[nx][ny] = positionMap[x][y] + direction[i];
                    visited = !visited;
                }
                // if update the shorest steps add it into queue and go
                if (visited) {
                    queue.offer(nx * yLen + ny);
                }
            }
        }
        return positionMap[hole[0]][hole[1]].equals("initial") ? "impossible" : positionMap[hole[0]][hole[1]];
    }

    public static void main(String[] args) {
        MazeIIIBFS bfs = new MazeIIIBFS();
        int[][] maze = new int[][] {
                { 0, 0, 0, 0, 0 },
                { 1, 1, 0, 0, 1 },
                { 0, 0, 0, 0, 0 },
                { 0, 1, 0, 0, 1 },
                { 0, 1, 0, 0, 0 }
        };
        int[] start = new int[] { 4, 3 };
        int[] destination = new int[] { 0, 1 };
        System.out.println(bfs.shortestDistance(maze, start, destination));
    }
}
