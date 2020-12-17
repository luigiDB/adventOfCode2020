package exercises;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;
import org.jooq.lambda.tuple.Tuple4;

import java.util.ArrayList;
import java.util.List;

public class Day17Matrix {
    public static long es1(Character[][] input) {
        int xmax = input.length + 14;
        int ymax = input[0].length + 14;
        int zmax = 15;

        boolean[][][] space = new boolean[xmax][ymax][zmax];

        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[0].length; y++) {
                if (input[x][y] == '#') {
                    Tuple3<Integer, Integer, Integer> arrival = Tuple.tuple(7 + x, 7 + y, 7);
                    set(space, arrival, true);
                }
            }
        }

        for (int i = 0; i < 6; i++) {
            boolean[][][] clone = new boolean[xmax][ymax][zmax];
            for (int x = 1; x < xmax - 1; x++) {
                for (int y = 1; y < ymax - 1; y++) {
                    for (int z = 1; z < zmax - 1; z++) {
                        Tuple3<Integer, Integer, Integer> pos = Tuple.tuple(x, y, z);
                        if (get(space, pos)) {
                            int neighbors = countActiveNeighbors(space, pos);
                            set(clone, pos, neighbors == 2 || neighbors == 3);
                        } else
                            set(clone, pos, countActiveNeighbors(space, pos) == 3);
                    }
                }
            }
            space = clone;
        }

        long count = 0;
        for (int x = 0; x < xmax; x++) {
            for (int y = 0; y < ymax; y++) {
                for (int z = 0; z < zmax; z++) {
                    if (get(space, Tuple.tuple(x, y, z)))
                        count++;
                }
            }
        }

        return count;
    }

    private static int countActiveNeighbors(boolean[][][] space, Tuple3<Integer, Integer, Integer> pos) {
        int counter = 0;
        for (Tuple3<Integer, Integer, Integer> nei : neighbors(pos)) {
            if (get(space, nei))
                counter++;
        }
        return counter;
    }

    private static List<Tuple3<Integer, Integer, Integer>> neighbors(Tuple3<Integer, Integer, Integer> pos) {
        int[] displacement = new int[]{-1, 0, 1};
        List<Tuple3<Integer, Integer, Integer>> neighbors = new ArrayList<>();
        for (int displacementX : displacement) {
            for (int displacementY : displacement) {
                for (int displacementZ : displacement) {
                    if (displacementX == 0 && displacementY == 0 && displacementZ == 0)
                        continue;
                    neighbors.add(Tuple.tuple(
                            pos.v1() + displacementX,
                            pos.v2() + displacementY,
                            pos.v3() + displacementZ
                    ));
                }
            }
        }
        return neighbors;
    }

    private static void set(boolean[][][] space, Tuple3<Integer, Integer, Integer> pos, boolean c) {
        space[pos.v1][pos.v2][pos.v3] = c;
    }

    private static boolean get(boolean[][][] space, Tuple3<Integer, Integer, Integer> pos) {
        return space[pos.v1][pos.v2][pos.v3];
    }

    public static long es2(Character[][] input) {
        int xmax = input.length + 14;
        int ymax = input[0].length + 14;
        int zmax = 15;
        int wmax = 15;

        boolean[][][][] space = new boolean[xmax][ymax][zmax][wmax];

        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[0].length; y++) {
                if (input[x][y] == '#') {
                    Tuple4<Integer, Integer, Integer, Integer> arrival = Tuple.tuple(7 + x, 7 + y, 7, 7);
                    set(space, arrival, true);
                }
            }
        }

        for (int i = 0; i < 6; i++) {
            boolean[][][][] clone = new boolean[xmax][ymax][zmax][wmax];
            for (int x = 1; x < xmax - 1; x++) {
                for (int y = 1; y < ymax - 1; y++) {
                    for (int z = 1; z < zmax - 1; z++) {
                        for (int w = 1; w < wmax - 1; w++) {
                            Tuple4<Integer, Integer, Integer, Integer> pos = Tuple.tuple(x, y, z, w);
                            if (get(space, pos)) {
                                int neighbors = countActiveNeighbors(space, pos);
                                set(clone, pos, neighbors == 2 || neighbors == 3);
                            } else
                                set(clone, pos, countActiveNeighbors(space, pos) == 3);
                        }
                    }
                }
            }
            space = clone;
        }

        long count = 0;
        for (int x = 0; x < xmax; x++) {
            for (int y = 0; y < ymax; y++) {
                for (int z = 0; z < zmax; z++) {
                    for (int w = 0; w < wmax; w++) {
                        if (get(space, Tuple.tuple(x, y, z, w))) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }

    private static int countActiveNeighbors(boolean[][][][] space, Tuple4<Integer, Integer, Integer, Integer> pos) {
        int counter = 0;
        for (Tuple4<Integer, Integer, Integer, Integer> nei : neighbors(pos)) {
            if (get(space, nei))
                counter++;
        }
        return counter;
    }

    private static List<Tuple4<Integer, Integer, Integer, Integer>> neighbors(Tuple4<Integer, Integer, Integer, Integer> pos) {
        int[] displacement = new int[]{-1, 0, 1};
        List<Tuple4<Integer, Integer, Integer, Integer>> neighbors = new ArrayList<>();
        for (int displacementX : displacement) {
            for (int displacementY : displacement) {
                for (int displacementZ : displacement) {
                    for (int displacementW : displacement) {
                        if (displacementX == 0 && displacementY == 0 && displacementZ == 0 && displacementW == 0) {
                            continue;
                        }
                        neighbors.add(Tuple.tuple(
                                pos.v1() + displacementX,
                                pos.v2() + displacementY,
                                pos.v3() + displacementZ,
                                pos.v4() + displacementW
                        ));
                    }
                }
            }
        }
        return neighbors;
    }

    private static void set(boolean[][][][] space, Tuple4<Integer, Integer, Integer, Integer> pos, boolean c) {
        space[pos.v1][pos.v2][pos.v3][pos.v4] = c;
    }

    private static boolean get(boolean[][][][] space, Tuple4<Integer, Integer, Integer, Integer> pos) {
        return space[pos.v1][pos.v2][pos.v3][pos.v4];
    }
}
