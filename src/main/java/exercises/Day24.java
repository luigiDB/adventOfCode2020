package exercises;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day24 {
    public static long es1(Stream<String> rawInput) {
        Set<Tuple2<Integer, Integer>> blackTiles = initFloor(rawInput);
        return blackTiles.size();
    }

    private static Set<Tuple2<Integer, Integer>> initFloor(Stream<String> rawInput) {
        List<Tuple2<Integer, Integer>> tiles = rawInput
                .map(string -> {
                    List<String> movesList = new ArrayList<>();
                    char[] chars = string.toCharArray();
                    for (int i = 0; i < chars.length; ) {
                        if (chars[i] == 'n' || chars[i] == 's') {
                            movesList.add(String.valueOf(chars[i]) + chars[i + 1]);
                            i += 2;
                        } else {
                            movesList.add(String.valueOf(chars[i]));
                            i++;
                        }
                    }
                    return movesList;
                })
                .map(moveset -> {
                    int x = 0;
                    int y = 0;
                    for (String move : moveset) {
                        switch (move) {
                            case "e":
                                x += 2;
                                break;
                            case "se":
                                x += 1;
                                y -= 1;
                                break;
                            case "sw":
                                x -= 1;
                                y -= 1;
                                break;
                            case "w":
                                x -= 2;
                                break;
                            case "nw":
                                x -= 1;
                                y += 1;
                                break;
                            case "ne":
                                x += 1;
                                y += 1;
                                break;
                        }
                    }
                    return Tuple.tuple(x, y);
                })
                .collect(Collectors.toList());

        Set<Tuple2<Integer, Integer>> blackTiles = new HashSet<>();
        for (Tuple2<Integer, Integer> tile : tiles) {
            if (blackTiles.contains(tile))
                blackTiles.remove(tile);
            else
                blackTiles.add(tile);
        }
        return blackTiles;
    }

    public static long es2(Stream<String> rawInput, int turns) {
        Set<Tuple2<Integer, Integer>> blackTiles = initFloor(rawInput);

        for (int i = 0; i < turns; i++) {
            Set<Tuple2<Integer, Integer>> newActive = new HashSet<>();
            Set<Tuple2<Integer, Integer>> toBeChecked = new HashSet<>();

            for (Tuple2<Integer, Integer> activePos : blackTiles) {
                toBeChecked.addAll(neighbors(activePos));
            }

            for (Tuple2<Integer, Integer> pos : toBeChecked) {
                int activeNeighbors = countActiveNeighbors(blackTiles, pos);
                if (blackTiles.contains(pos)) {
                    if (activeNeighbors != 0 && activeNeighbors <= 2)
                        newActive.add(pos);
                } else {
                    if (activeNeighbors == 2)
                        newActive.add(pos);
                }
            }

            blackTiles = newActive;
        }

        return blackTiles.size();
    }

    private static int countActiveNeighbors(Set<Tuple2<Integer, Integer>> blackTiles, Tuple2<Integer, Integer> pos) {
        int counter = 0;
        for(Tuple2<Integer, Integer> neighbor: neighbors(pos)) {
            if(blackTiles.contains(neighbor))
                counter++;
        }
        return counter;
    }

    private static Collection<? extends Tuple2<Integer, Integer>> neighbors(Tuple2<Integer, Integer> activePos) {
        List<Tuple2<Integer, Integer>> neighbor = new ArrayList<>();
        neighbor.add(Tuple.tuple(activePos.v1 + 2, activePos.v2));
        neighbor.add(Tuple.tuple(activePos.v1 + 1, activePos.v2 - 1));
        neighbor.add(Tuple.tuple(activePos.v1 - 1, activePos.v2 - 1));
        neighbor.add(Tuple.tuple(activePos.v1 - 2, activePos.v2));
        neighbor.add(Tuple.tuple(activePos.v1 - 1, activePos.v2 + 1));
        neighbor.add(Tuple.tuple(activePos.v1 + 1, activePos.v2 + 1));
        return neighbor;
    }
}
