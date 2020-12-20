package exercises;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static utilities.AOCTestFramework.parseMatrix;

public class Day20 {


    public static long es1(Stream<String> input) {
        List<Tile> tiles = input.map(Tile::new).collect(Collectors.toList());
        List<Tile> originalTiles = new ArrayList<>(tiles);

//        Map<String, List<String>> collect = tiles.stream()
//                .flatMap(tile -> tile.bordersString().stream())
//                .collect(
//                        Collectors.groupingBy(
//                                s -> s,
//                                Collectors.mapping(Function.identity(), Collectors.toList())
//                        )
//                );

        Map<String, List<Tile>> borders = tiles.stream()
                .flatMap(Tile::bordersWithTile)
                .collect(
                        Collectors.groupingBy(
                                tuple -> tuple.v1,
                                Collectors.mapping(tuple -> tuple.v2, Collectors.toList())
                        )
                );

        Queue<Tile> bfs = new LinkedList<>();
        Tile firstTile = tiles.remove(0);
        bfs.add(firstTile);
        List<Tuple2<Tile, Tile>> junctions = new ArrayList<>();

        while (!bfs.isEmpty()) {
            Tile poll = bfs.poll();
            for (String direction : poll.availableDirections()) {
                if (poll.free(direction)) {
                    List<Tile> neighbors = borders.get(direction);
                    for (Tile neighbor : neighbors) {
                        if (!neighbor.equals(poll) && neighbor.free(direction)) {
                            poll.occupy(direction);
                            neighbor.occupy(direction);
                            bfs.add(neighbor);
                            junctions.add(Tuple.tuple(poll, neighbor));
                        }
                    }
                }
            }
        }

        Graph<Tile, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        for (Tile tile : originalTiles)
            g.addVertex(tile);
        for (Tuple2<Tile, Tile> junction : junctions)
            g.addEdge(junction.v1, junction.v2);

        long res = 1;
        for (Tile tile : originalTiles) {
            if (g.degreeOf(tile) == 2)
                res *= tile.id;
        }

        return res;
    }

    public static long es2(Stream<String> input) {
        return 0;
    }

    static class Tile {
        final int id;
        final Character[][] matrix;
        private final ArrayList<String> occupiedDirections;
        private final List<String> availableDirections;
        private final List<String> possibleDirections;

        public Tile(String tile) {
            String[] split = tile.split("\n");
            id = Integer.parseInt(split[0].split(" ")[1].replaceAll(":", ""));
            matrix = parseMatrix(tile.substring(tile.indexOf("\n") + 1), Character.class, Character::valueOf);

            char[] up = new char[matrix.length];
            for (int i = 0; i < matrix.length; i++) {
                up[i] = matrix[0][i];
            }
            String u = String.valueOf(up);

            char[] right = new char[matrix.length];
            for (int i = 0; i < matrix.length; i++) {
                right[i] = matrix[i][matrix.length - 1];
            }
            String r = String.valueOf(right);

            char[] down = new char[matrix.length];
            for (int i = 0; i < matrix.length; i++) {
                down[i] = matrix[matrix.length - 1][matrix.length - 1 - i];
            }
            String d = String.valueOf(down);

            char[] left = new char[matrix.length];
            for (int i = 0; i < matrix.length; i++) {
                left[i] = matrix[matrix.length - 1 - i][0];
            }
            String l = String.valueOf(left);

            availableDirections = new ArrayList<>();
            availableDirections.add(u);
            availableDirections.add(new StringBuilder(u).reverse().toString());
            availableDirections.add(r);
            availableDirections.add(new StringBuilder(r).reverse().toString());
            availableDirections.add(d);
            availableDirections.add(new StringBuilder(d).reverse().toString());
            availableDirections.add(l);
            availableDirections.add(new StringBuilder(l).reverse().toString());
            possibleDirections = new ArrayList<>(availableDirections);
            occupiedDirections = new ArrayList<>();

        }

        public Stream<Tuple2<String, Tile>> bordersWithTile() {
            return possibleDirections.stream()
                    .map(s -> Tuple.tuple(s, this));
        }

        public List<String> availableDirections() {
            return availableDirections;
        }

        public boolean free(String direction) {
            return !occupiedDirections.contains(direction);
        }

        public void occupy(String direction) {
            occupiedDirections.add(direction);
            occupiedDirections.add(new StringBuilder(direction).reverse().toString());

        }
    }
}
