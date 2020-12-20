package exercises;

import org.apache.commons.lang3.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.math.NumberUtils.toInt;
import static utilities.AOCTestFramework.parseMatrix;
import static utilities.AOCTestFramework.parseStrings;

public class Day20 {

    public static List<String> mask = parseStrings(
            "                  # \n" +
                    "#    ##    ##    ###\n" +
                    " #  #  #  #  #  #   ").collect(Collectors.toList());

    public static long es1(Stream<String> input) {
        List<TileEs1> tiles = input.map(TileEs1::new).collect(Collectors.toList());
        List<TileEs1> originalTiles = new ArrayList<>(tiles);

        Map<String, List<TileEs1>> borders = tiles.stream()
                .flatMap(TileEs1::bordersWithTile)
                .collect(
                        Collectors.groupingBy(
                                tuple -> tuple.v1,
                                Collectors.mapping(tuple -> tuple.v2, Collectors.toList())
                        )
                );

        Queue<TileEs1> bfs = new LinkedList<>();
        TileEs1 firstTile = tiles.remove(0);
        bfs.add(firstTile);
        List<Tuple2<TileEs1, TileEs1>> junctions = new ArrayList<>();

        while (!bfs.isEmpty()) {
            TileEs1 poll = bfs.poll();
            for (String direction : poll.availableDirections()) {
                if (poll.free(direction)) {
                    List<TileEs1> neighbors = borders.get(direction);
                    for (TileEs1 neighbor : neighbors) {
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

        Graph<TileEs1, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        for (TileEs1 tile : originalTiles)
            g.addVertex(tile);
        for (Tuple2<TileEs1, TileEs1> junction : junctions)
            g.addEdge(junction.v1, junction.v2);

        long res = 1;
        for (TileEs1 tile : originalTiles) {
            if (g.degreeOf(tile) == 2)
                res *= tile.id;
        }

        return res;

    }

    public static long es2(Stream<String> input) {
        List<Tile> tiles = input
                .map(Tile::new)
                .collect(Collectors.toList());

        Map<Integer, Integer> edgesOccurrences = new HashMap<>();
        for (Tile t : tiles) {
            for (Integer i : t.edges) {
                edgesOccurrences.merge(i, 1, Integer::sum);
            }
        }

        int seaSize = (int) Math.sqrt(tiles.size());
        Tile[][] sea = new Tile[seaSize][seaSize];

        setFirstEdge(tiles, edgesOccurrences, sea);


        fillFirstLine(tiles, seaSize, sea);
        fillSea(tiles, seaSize, sea);

        int viewSize = seaSize * (10 - 2);
        boolean[][] support = new boolean[viewSize][viewSize];

        Boolean[][] view = fillView(seaSize, sea, viewSize);

        int ans = 0;
        for (int i = 0; i < 4; i++) {
            int count = findMonster(view, support);
            int oddities = countOddities(view, support);
            if (count > 0)
                ans = oddities;
            view = rotateMatrix(view, Boolean.class);
        }
        view = verticalMirror(view, Boolean.class);
        for (int i = 0; i < 4; i++) {
            int count = findMonster(view, support);
            int gm = countOddities(view, support);
            if (count > 0)
                ans = gm;
            view = rotateMatrix(view, Boolean.class);
        }
        return ans;
    }

    private static Boolean[][] fillView(int seaSize, Tile[][] sea, int viewSize) {
        Boolean[][] view = new Boolean[viewSize][viewSize];
        for (int tx = 0; tx < seaSize; tx++) {
            for (int ty = 0; ty < seaSize; ty++) {
                for (int x = 0; x < 10 - 2; x++) {
                    for (int y = 0; y < 10 - 2; y++) {
                        view[tx * (10 - 2) + x][ty * (10 - 2) + y] = sea[tx][ty].data[x + 1][y + 1];
                    }
                }
            }
        }
        return view;
    }

    private static void fillSea(List<Tile> tiles, int seaSize, Tile[][] sea) {
        for (int y = 0; y < seaSize; y++) {
            for (int x = 1; x < seaSize; x++) {
                int tgt = sea[x - 1][y].getEdgeVal(1);
                for (Tile t : tiles) {
                    if (t.edges.contains(tgt)) {
                        for (int i = 0; i < 3; i++) {
                            if (t.getEdgeVal(3) == tgt) {
                                break;
                            }
                            t.rotate();
                        }
                        if (t.getEdgeVal(3) != tgt) {
                            t.flipVertical();
                        }
                        for (int i = 0; i < 3; i++) {
                            if (t.getEdgeVal(3) == tgt) {
                                break;
                            }
                            t.rotate();
                        }

                        sea[x][y] = t;
                        tiles.remove(t);
                        break;
                    }
                }
            }
        }
    }

    private static void fillFirstLine(List<Tile> tiles, int seaSize, Tile[][] sea) {
        for (int y = 1; y < seaSize; y++) {
            int tgt = sea[0][y - 1].getEdgeVal(2);
            for (Tile t : tiles) {
                if (t.edges.contains(tgt)) {
                    for (int i = 0; i < 3; i++) {
                        if (t.getEdgeVal(0) == tgt) {
                            break;
                        }
                        t.rotate();
                    }
                    if (t.getEdgeVal(0) != tgt) {
                        t.flipVertical();
                    }
                    for (int i = 0; i < 3; i++) {
                        if (t.getEdgeVal(0) == tgt) {
                            break;
                        }
                        t.rotate();
                    }

                    sea[0][y] = t;
                    tiles.remove(t);
                    break;
                }
            }
        }
    }

    private static void setFirstEdge(List<Tile> tiles, Map<Integer, Integer> edgesOccurrences, Tile[][] sea) {
        for (Tile t : tiles) {
            if (!t.isCorner(edgesOccurrences)) {
                continue;
            }

            while (edgesOccurrences.get(t.getEdgeVal(3)) != 1) {
                t.rotate();
            }
            if (edgesOccurrences.get(t.getEdgeVal(0)) != 1) {
                t.flipVertical();
            }

            sea[0][0] = t;
            tiles.remove(t);
            break;
        }
    }

    public static int countOddities(Boolean[][] grid, boolean[][] usedGrid) {
        int width = grid.length;
        int cnt = 0;
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[x][y] && !usedGrid[x][y]) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    private static int findMonster(Boolean[][] grid, boolean[][] viewMaybe) {
        int monsterWidth = mask.get(0).length();
        int monsterHeight = mask.size();
        int count = 0;
        int width = grid.length;
        for (int y = 0; y < width - (monsterHeight - 1); y++) {
            outer:
            for (int x = 0; x < width - (monsterWidth - 1); x++) {
                for (int ny = 0; ny < monsterHeight; ny++) {
                    String nline = mask.get(ny);
                    for (int nx = 0; nx < monsterWidth; nx++) {
                        if (nline.charAt(nx) != '#') {
                            continue;
                        }

                        if (!grid[x + nx][y + ny]) {
                            continue outer;
                        }
                    }
                }
                count++;

                for (int ny = 0; ny < monsterHeight; ny++) {
                    String nline = mask.get(ny);
                    for (int nx = 0; nx < monsterWidth; nx++) {
                        if (nline.charAt(nx) != '#') {
                            continue;
                        }
                        viewMaybe[x + nx][y + ny] = true;
                    }
                }
            }
        }

        return count;
    }

    static class TileEs1 {
        final int id;
        final Character[][] matrix;
        private final ArrayList<String> occupiedDirections;
        private final List<String> availableDirections;
        private final List<String> possibleDirections;

        public int getId() {
            return id;
        }

        public TileEs1(String tile) {
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

        public Stream<Tuple2<String, TileEs1>> bordersWithTile() {
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

    public static class Tile {
        public int id;
        public List<Integer> edges;
        public Boolean[][] data;
        private final List<Integer> availableDirections;
        private final List<Integer> occupiedDirections;

        public Tile(String tile) {
            String[] lines = tile.split("\n");
            id = toInt(lines[0].split(" ")[1].replace(":", ""));
            int height = 10;
            int width = 10;
            data = new Boolean[width][height];
            for (int y = 0; y < height; y++) {
                String line = lines[y + 1];
                for (int x = 0; x < width; x++) {
                    data[x][y] = (line.charAt(x) == '#');
                }
            }

            edges = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                edges.addAll(getEdges(i));
            }
            availableDirections = new ArrayList<>(edges);
            occupiedDirections = new ArrayList<>();
        }

        public boolean isCorner(Map<Integer, Integer> edgesOccurrences) {
            int cnt = 0;
            for (Integer i : edges) {
                if (edgesOccurrences.get(i).equals(1)) {
                    cnt++;
                }
            }
            return (cnt > 2);
        }

        public int getEdgeVal(int n) {
            return getEdges(n).get(0);
        }

        public List<Integer> getEdges(int n) {
            int width = 10;
            boolean[] d = new boolean[width];

            switch (n) {
                case 0:
                    for (int i = 0; i < width; i++) {
                        d[i] = data[i][0];
                    }
                    break;

                case 1:
                    for (int i = 0; i < width; i++) {
                        d[i] = data[width - 1][i];
                    }
                    break;

                case 2:
                    for (int i = 0; i < width; i++) {
                        d[i] = data[i][width - 1];
                    }
                    break;

                case 3:
                    for (int i = 0; i < width; i++) {
                        d[i] = data[0][i];
                    }
                    break;
            }

            StringBuilder str = new StringBuilder();
            for (boolean b : d) {
                if (b)
                    str.append("1");
                else
                    str.append("0");
            }

            List<Integer> rv = new ArrayList<>();
            rv.add(Integer.valueOf(str.toString(), 2));
            rv.add(Integer.valueOf(StringUtils.reverse(str.toString()), 2));

            return rv;
        }

        public void rotate() {
            data = rotateMatrix(data, Boolean.class);
        }

        public void flipVertical() {
            data = verticalMirror(data, Boolean.class);
        }

        public List<Integer> availableDirections() {
            return availableDirections;
        }

        public boolean free(Integer direction) {
            return !occupiedDirections.contains(direction);
        }

        public void occupy(Integer direction) {
            occupiedDirections.add(direction);
            occupiedDirections.add(Integer.valueOf(StringUtils.reverse(direction.toString()), 2));
        }
    }

    public static <T> T[][] verticalMirror(T[][] grid, Class<T> clazz) {
        int width = grid.length;
        T[][] newData = (T[][]) Array.newInstance(clazz, width, width);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                newData[i][j] = grid[i][width - 1 - j];
            }
        }
        return newData;
    }

    public static <T> T[][] rotateMatrix(T[][] grid, Class<T> clazz) {
        int width = grid.length;
        T[][] newData = (T[][]) Array.newInstance(clazz, width, width);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                newData[i][j] = grid[width - 1 - j][i];
            }
        }
        return newData;
    }
}
