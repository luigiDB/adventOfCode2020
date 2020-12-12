package exercises;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day12 {

    private static Movement[] directions = new Movement[]{Movement.N, Movement.E, Movement.S, Movement.W};

    public static int es1(Stream<Tuple2<Movement, Integer>> input) {
        List<Tuple2<Movement, Integer>> list = input.collect(Collectors.toList());
        Tuple3<Integer, Integer, Movement> pos = Tuple.tuple(0, 0, Movement.E);
        for (Tuple2<Movement, Integer> movement : list) {
            switch (movement.v1()) {
                case N:
                case S:
                case E:
                case W:
                    pos = moveByDirection(pos, movement);
                    break;
                case L:
                    int index = indexOf(directions, pos.v3());
                    Movement newDirection = directions[(index + (4 - (movement.v2() / 90))) % 4];
                    pos = Tuple.tuple(pos.v1(), pos.v2(), newDirection);
                    break;
                case R:
                    index = indexOf(directions, pos.v3());
                    newDirection = directions[(index + movement.v2() / 90) % 4];
                    pos = Tuple.tuple(pos.v1(), pos.v2(), newDirection);
                    break;
                case F:
                    pos = moveByDirection(pos, Tuple.tuple(pos.v3(), movement.v2()));
                    break;
            }
        }
        return distance(pos);
    }

    public static int es2(Stream<Tuple2<Movement, Integer>> input) {
        List<Tuple2<Movement, Integer>> list = input.collect(Collectors.toList());
        Tuple3<Integer, Integer, Movement> ship = Tuple.tuple(0, 0, Movement.E);
        Tuple3<Integer, Integer, Movement> waypoint = Tuple.tuple(10, 1, Movement.N);

        for (Tuple2<Movement, Integer> movement : list) {
            switch (movement.v1()) {
                case N:
                case S:
                case E:
                case W:
                    waypoint = moveByDirection(waypoint, movement);
                    break;
                case L:
                    waypoint = rotate(waypoint, (4 - (movement.v2() / 90)));
                    break;
                case R:
                    waypoint = rotate(waypoint, movement.v2() / 90);
                    break;
                case F:
                    for (int i = 0; i < movement.v2(); i++) {
                        ship = Tuple.tuple(
                                ship.v1() + waypoint.v1(),
                                ship.v2() + waypoint.v2(),
                                ship.v3()
                        );
                    }
                    break;
            }
        }
        return distance(ship);
    }

    private static Tuple3<Integer, Integer, Movement> rotate(Tuple3<Integer, Integer, Movement> pos, int angle) {
        switch (angle) {
            case 1:
                return Tuple.tuple(pos.v2(), -pos.v1(), pos.v3());
            case 2:
                return Tuple.tuple(-pos.v1(), -pos.v2(), pos.v3());
            case 3:
                return Tuple.tuple(-pos.v2(), pos.v1(), pos.v3());
        }
        throw new UnsupportedOperationException();
    }

    private static Tuple3<Integer, Integer, Movement> moveByDirection(Tuple3<Integer, Integer, Movement> pos, Tuple2<Movement, Integer> movement) {
        switch (movement.v1()) {
            case N:
                return Tuple.tuple(pos.v1(), pos.v2() + movement.v2(), pos.v3());
            case S:
                return Tuple.tuple(pos.v1(), pos.v2() - movement.v2(), pos.v3());
            case E:
                return Tuple.tuple(pos.v1() + movement.v2(), pos.v2(), pos.v3());
            case W:
                return Tuple.tuple(pos.v1() - movement.v2(), pos.v2(), pos.v3());
        }
        throw new UnsupportedOperationException();
    }

    private static int indexOf(Movement[] directions, Movement target) {
        for (int i = 0; i < directions.length; i++) {
            if (directions[i] == target)
                return i;
        }
        throw new UnsupportedOperationException();
    }


    private static int distance(Tuple3<Integer, Integer, Movement> pos) {
        return Math.abs(-pos.v1()) + Math.abs(-pos.v2());
    }


    public enum Movement {
        N, S, E, W, L, R, F
    }

}
