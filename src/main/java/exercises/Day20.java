package exercises;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static utilities.AOCTestFramework.parseMatrix;
import static utilities.MatrixUtils.*;

public class Day20 {

    private static Character[][] mask = parseMatrix(
            "                  # \n" +
                    "#    ##    ##    ###\n" +
                    " #  #  #  #  #  #   "
            , Character.class, Character::charValue);

    private static Character[][] master = parseMatrix(
            ".#.#..#.##...#.##..#####\n" +
                    "###....#.#....#..#......\n" +
                    "##.##.###.#.#..######...\n" +
                    "###.#####...#.#####.#..#\n" +
                    "##.#....#.##.####...#.##\n" +
                    "...########.#....#####.#\n" +
                    "....#..#...##..#.#.###..\n" +
                    ".####...#..#.....#......\n" +
                    "#..#.##..#..###.#.##....\n" +
                    "#.####..#.####.#.#.###..\n" +
                    "###.#.#...#.######.#..##\n" +
                    "#.####....##..########.#\n" +
                    "##..##.#...#...#.#.#.#..\n" +
                    "...#..#..#.#.##..###.###\n" +
                    ".#.#....#.##.#...###.##.\n" +
                    "###.#...#..#.##.######..\n" +
                    ".#.#.###.##.##.#..#.##..\n" +
                    ".####.###.#...###.#..#.#\n" +
                    "..#.#..#..#.#.#.####.###\n" +
                    "#..####...#.#.#.###.###.\n" +
                    "#####..#####...###....##\n" +
                    "#.##..#..#...#..####...#\n" +
                    ".#.###..##..##..####.##.\n" +
                    "...###...##...#...#..###",
            Character.class, Character::charValue

    );

    public static long es1(Stream<String> input) {
        List<Tile> tiles = input.map(Tile::new).collect(Collectors.toList());
        List<Tile> originalTiles = new ArrayList<>(tiles);

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

    public static long es2(Stream<String> input, int size) {
        List<Tile> tiles = input.map(Tile::new).collect(Collectors.toList());
        List<Tile> originalTiles = new ArrayList<>(tiles);

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
        List<Tuple3<Tile, Tile, String>> junctions = new ArrayList<>();

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
                            junctions.add(Tuple.tuple(poll, neighbor, direction));
                        }
                    }
                }
            }
        }

        Graph<Tile, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        for (Tile tile : originalTiles)
            g.addVertex(tile);
        for (Tuple3<Tile, Tile, String> junction : junctions)
            g.addEdge(junction.v1, junction.v2);

        Tile[][] sea = rebuildMatrix(originalTiles, junctions, g, size);

        List<Tile[][]> seas = transform(sea, Tile.class);
        for (Tile[][] transoformedsea : seas) {
            int originalTileSize = firstTile.matrix.length;
            int newTileSize = originalTileSize - 2;

            Character[][] transformedView = fillView(size, transoformedsea, originalTileSize, newTileSize);

            if(transoformedsea[0][0].id == 1951 && transoformedsea[0][2].id == 3079) {
                printMatrix(transoformedsea, tile -> String.valueOf(tile.getId()));
                printMatrix(transformedView);
            }

            boolean good = goodView(transformedView, mask);
            if (good) {
                System.out.println("match");
                replaceMask(transformedView, mask, 'O');
                System.out.println(countOccurrences(transformedView, '#'));
            }
//            }
        }

        return 0;
    }

    private static void replaceMask(Character[][] view, Character[][] mask, char replace) {
        for (int i = 0; i <= view.length - mask.length; i++) {
            for (int j = 0; j <= view[0].length - mask[0].length; j++) {
                if (matchMask(view, i, j)) {
                    replaceMask(view, i, j, replace);
                }
            }
        }
    }

    private static void replaceMask(Character[][] view, int startX, int startY, char replace) {
        for (int x = 0; x < mask.length; x++) {
            for (int y = 0; y < mask[0].length; y++) {
                if (mask[x][y].equals('#'))
                    view[startX + x][startY + y] = replace;
            }
        }
    }

    public static boolean goodView(Character[][] view, Character[][] mask) {
        for (int i = 0; i <= view.length - mask.length; i++) {
            for (int j = 0; j <= view[0].length - mask[0].length; j++) {
                if (matchMask(view, i, j))
                    return true;
            }
        }
        return false;
    }

    public static boolean matchMask(Character[][] view, int startX, int startY) {
        for (int x = 0; x < mask.length; x++) {
            for (int y = 0; y < mask[0].length; y++) {
                if (mask[x][y].equals('#')) {
                    if (!view[startX + x][startY + y].equals('#'))
                        return false;
                }
            }
        }
        return true;
    }

    private static <T> List<T[][]> transform(T[][] sea, Class<T> clazz) {
        List<T[][]> list = new ArrayList<>();
        T[][] reference = sea;

        int count = 0;
        do {
            list.add(reference);
            list.add(horizzotalMirror(reference, clazz));
            T[][] vm = verticalMirror(reference, clazz);
            list.add(vm);
            list.add(horizzotalMirror(vm, clazz));
            count++;
            reference = rotateMatrix(reference, clazz);
        } while (count < 4);
        return list;
    }

    private static Character[][] fillView(int size, Tile[][] sea, int originalTileSize, int newTileSize) {
        Character[][] view = new Character[size * newTileSize][size * newTileSize];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                // fill view from
                try {
                    Character[][] toBeCopied = sea[x][y].matrix;
                    for (int i = 1; i < originalTileSize - 1; i++) {
                        for (int j = 1; j < originalTileSize - 1; j++) {
                            view[(x * newTileSize) + i - 1][(y * newTileSize) + j - 1] = toBeCopied[i][j];
                        }
                    }
                } catch (Exception e) {
                    throw new UnsupportedOperationException();
                }
                printMatrix(view);
            }
        }
        return view;
    }

    private static Tile[][] rebuildMatrix(List<Tile> originalTiles, List<Tuple3<Tile, Tile, String>> junctions, Graph<Tile, DefaultEdge> g, int size) {
        Tile[][] sea = new Tile[size][size];
        Set<Tile> toBeUsed = new HashSet<>(originalTiles);
        Queue<Tuple2<Integer, Integer>> bfs2 = new LinkedList<>();
        for (Tile tile : originalTiles) {
            if (g.degreeOf(tile) == 2) {
                bfs2.add(Tuple.tuple(0, 0));
                sea[0][0] = tile;
                toBeUsed.remove(tile);
                break;
            }
        }

        int counter = 0;
        while (!bfs2.isEmpty()) {
            Tuple2<Integer, Integer> poll = bfs2.poll();
            List<Tuple3<Integer, Integer, Tile>> tmp = cardinalNeighbors(sea, poll, counter).collect(Collectors.toList());
            for (Tuple3<Integer, Integer, Tile> direction : tmp) {
                Tuple2<Integer, Integer> next = Tuple.tuple(direction.v1, direction.v2);

                if (matrixGet(sea, next) != null)
                    continue;

                List<Tile> neighbors = cardinalNeighbors(sea, next)
                        .filter(tuple -> tuple.v3 != null)
                        .map(tuple -> tuple.v3)
                        .collect(Collectors.toList());

                HashSet<Tile> possibilities = new HashSet<>(toBeUsed);
                for (Tile tile : neighbors) {
                    possibilities.retainAll(connections(junctions, tile));
                }
                Tile used = possibilities.iterator().next();
                matrixSet(sea, next, used);
                toBeUsed.remove(used);
                bfs2.add(next);
            }
            counter++;
        }
        return sea;
    }

    private static Set<Tile> connections(List<Tuple3<Tile, Tile, String>> junctions, Tile start) {
        Set<Tile> res = new HashSet<>();
        for (Tuple3<Tile, Tile, String> junction : junctions) {
            if (junction.v1.equals(start))
                res.add(junction.v2);
            if (junction.v2.equals(start))
                res.add(junction.v1);
        }
        return res;
    }

    private static Stream<Tuple3<Integer, Integer, Tile>> cardinalNeighbors(Tile[][] matrix, Tuple2<Integer, Integer> pos, int counter) {
        int height = matrix.length;
        int wide = matrix[0].length;
        List<Tuple2<Integer, Integer>> cardinalDirections = cardinalDirections(counter);
        Seq<Optional<Tuple2<Integer, Integer>>> tuples = Seq.seq(cardinalDirections)
                .map(tuple -> {
                    int neiX = pos.v1() + tuple.v1;
                    int neiY = pos.v2() + tuple.v2;
                    if (neiX >= 0 && neiX < height && neiY >= 0 && neiY < wide) {
                        return Optional.of(Tuple.tuple(neiX, neiY));
                    }
                    return Optional.empty();
                });
        return tuples
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(tuple2 -> Tuple.tuple(tuple2.v1, tuple2.v2, matrix[tuple2.v1][tuple2.v2]));
    }

    public static Stream<Tuple3<Integer, Integer, Tile>> cardinalNeighbors(Tile[][] matrix, Tuple2<Integer, Integer> pos) {
        return cardinalNeighbors(matrix, pos, 1);
    }

    private static List<Tuple2<Integer, Integer>> cardinalDirections(int counter) {
        int[] directions;
        if (counter % 2 == 0)
            directions = new int[]{0, 1, 0, -1, 0};
        else
            directions = new int[]{1, 0, -1, 0, 1};
        List<Tuple2<Integer, Integer>> cardinalDirections = new ArrayList<>();
        for (int i = 0; i < directions.length - 1; i++) {
            cardinalDirections.add(Tuple.tuple(directions[i], directions[i + 1]));
        }
        return cardinalDirections;
    }

    public static <T> T[][] rotateMatrix(T[][] matrix, Class<T> clazz) {
        int row = matrix.length;
        T[][] out = cloneMatrix(matrix, clazz);
        //first find the transpose of the matrix.
        for (int i = 0; i < row; i++) {
            for (int j = i; j < row; j++) {
                T temp = out[i][j];
                out[i][j] = out[j][i];
                out[j][i] = temp;
            }
        }
        //reverse each row
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row / 2; j++) {
                T temp = out[i][j];
                out[i][j] = out[i][row - 1 - j];
                out[i][row - 1 - j] = temp;
            }
        }
        return out;
    }

    public static <T> T[][] horizzotalMirror(T[][] in, Class<T> clazz) {
        int height = in.length;
        int width = in[0].length;
        T[][] out = (T[][]) Array.newInstance(clazz, height, width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                out[i][width - j - 1] = in[i][j];
            }
        }
        return out;
    }

    public static <T> T[][] verticalMirror(T[][] in, Class<T> clazz) {
        int height = in.length;
        int width = in[0].length;
        T[][] out = (T[][]) Array.newInstance(clazz, height, width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                out[height - i - 1][j] = in[i][j];
            }
        }
        return out;
    }

    static class Tile {
        final int id;
        final Character[][] matrix;
        private final ArrayList<String> occupiedDirections;
        private final List<String> availableDirections;
        private final List<String> possibleDirections;

        public int getId() {
            return id;
        }

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

        public Stream<String> occupiedDirections() {
            return occupiedDirections.stream();
        }

        public boolean used(Set<String> mustBeUsed) {
            return occupiedDirections.containsAll(mustBeUsed);
        }
    }
}
