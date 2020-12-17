package exercises;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;
import org.jooq.lambda.tuple.Tuple4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day17 {
    public static long es1(Character[][] input) {
        Set<Tuple3<Integer, Integer, Integer>> active = new HashSet<>();
        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[0].length; y++) {
                if (input[x][y] == '#') {
                    active.add(Tuple.tuple(x, y, 0));
                }
            }
        }

        for (int i = 0; i < 6; i++) {
            Set<Tuple3<Integer, Integer, Integer>> newActive = new HashSet<>();
            Set<Tuple3<Integer, Integer, Integer>> toBeChecked = new HashSet<>();

            for (Tuple3<Integer, Integer, Integer> activePos : active) {
                toBeChecked.addAll(neighbors(activePos));
            }

            for (Tuple3<Integer, Integer, Integer> pos : toBeChecked) {
                int activeNeighbors = countActiveNeighbors(active, pos);
                if (active.contains(pos)) {
                    if (activeNeighbors == 2 || activeNeighbors == 3)
                        newActive.add(pos);
                } else {
                    if (activeNeighbors == 3)
                        newActive.add(pos);
                }
            }

            active = newActive;
        }

        return active.size();
    }

    public static long es2(Character[][] input) {
        Set<Tuple4<Integer, Integer, Integer, Integer>> active = new HashSet<>();
        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[0].length; y++) {
                if (input[x][y] == '#') {
                    active.add(Tuple.tuple(x, y, 0, 0));
                }
            }
        }

        for (int i = 0; i < 6; i++) {
            Set<Tuple4<Integer, Integer, Integer,  Integer>> newActive = new HashSet<>();
            Set<Tuple4<Integer, Integer, Integer,  Integer>> toBeChecked = new HashSet<>();

            for (Tuple4<Integer, Integer, Integer, Integer> activePos : active) {
                toBeChecked.addAll(neighbors(activePos));
            }

            for (Tuple4<Integer, Integer, Integer,  Integer> pos : toBeChecked) {
                int activeNeighbors = countActiveNeighbors(active, pos);
                if (active.contains(pos)) {
                    if (activeNeighbors == 2 || activeNeighbors == 3)
                        newActive.add(pos);
                } else {
                    if (activeNeighbors == 3)
                        newActive.add(pos);
                }
            }

            active = newActive;
        }

        return active.size();
    }

    private static int countActiveNeighbors(Set<Tuple3<Integer, Integer, Integer>> set, Tuple3<Integer, Integer, Integer> pos) {
        int counter = 0;
        for (Tuple3<Integer, Integer, Integer> nei : neighbors(pos)) {
            if (set.contains(nei))
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

    private static int countActiveNeighbors(Set<Tuple4<Integer, Integer, Integer,  Integer>> set, Tuple4<Integer, Integer, Integer,  Integer> pos) {
        int counter = 0;
        for (Tuple4<Integer, Integer, Integer,  Integer> nei : neighbors(pos)) {
            if (set.contains(nei))
                counter++;
        }
        return counter;
    }

    private static List<Tuple4<Integer, Integer, Integer, Integer>> neighbors(Tuple4<Integer, Integer, Integer, Integer> pos) {
        int[] displacement = new int[]{-1, 0, 1};
        List<Tuple4<Integer, Integer, Integer,  Integer>> neighbors = new ArrayList<>();
        for (int displacementX : displacement) {
            for (int displacementY : displacement) {
                for (int displacementZ : displacement) {
                    for (int displacementW : displacement) {
                        if (displacementX == 0 && displacementY == 0 && displacementZ == 0 && displacementW == 0)
                            continue;
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
}
