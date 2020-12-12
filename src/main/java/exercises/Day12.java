package exercises;

import org.apache.commons.lang3.tuple.Pair;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day12 {

    public enum Movement {
        N, S, E, W, L, R, F
    }

    private static final Movement[] directions = new Movement[]{Movement.N, Movement.E, Movement.S, Movement.W};

    public static int es1(Stream<Tuple2<Movement, Integer>> input) {
        List<Tuple2<Movement, Integer>> list = input.collect(Collectors.toList());
        Tuple3<Integer, Integer, Movement> ship = Tuple.tuple(0, 0, Movement.E);
        for (Tuple2<Movement, Integer> movement : list) {
            switch (movement.v1()) {
                case N:
                case S:
                case E:
                case W:
                    ship = moveByDirection(ship, movement);
                    break;
                case L:
                    int index = indexOf(ship.v3());
                    Movement newDirection = directions[(index + (4 - (movement.v2() / 90))) % 4];
                    ship = Tuple.tuple(ship.v1(), ship.v2(), newDirection);
                    break;
                case R:
                    index = indexOf(ship.v3());
                    newDirection = directions[(index + movement.v2() / 90) % 4];
                    ship = Tuple.tuple(ship.v1(), ship.v2(), newDirection);
                    break;
                case F:
                    ship = moveByDirection(ship, Tuple.tuple(ship.v3(), movement.v2()));
                    break;
            }
        }
        return manhattanDistance(ship);
    }

    public static int es2(Stream<Tuple2<Movement, Integer>> input) {
        List<Tuple2<Movement, Integer>> list = input.collect(Collectors.toList());
        Tuple3<Integer, Integer, Movement> ship = Tuple.tuple(0, 0, Movement.E);
        //Movement is ignored for the waypoint
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
                    waypoint = rotateAroundPoint(waypoint, (4 - (movement.v2() / 90)));
                    break;
                case R:
                    waypoint = rotateAroundPoint(waypoint, movement.v2() / 90);
                    break;
                case F:
                    for (int i = 0; i < movement.v2(); i++) {
                        ship = moveByDirection(ship, waypoint);
                    }
                    break;
            }
        }
        return manhattanDistance(ship);
    }

    private static Tuple3<Integer, Integer, Movement> rotateAroundPoint(Tuple3<Integer, Integer, Movement> pos, int angle) {
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

    private static Tuple3<Integer, Integer, Movement> moveByDirection(Tuple3<Integer, Integer, Movement> pos, Tuple3<Integer, Integer, Movement> direction) {
        return Tuple.tuple(
                pos.v1() + direction.v1(),
                pos.v2() + direction.v2(),
                pos.v3()
        );
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

    private static int indexOf(Movement target) {
        for (int i = 0; i < Day12.directions.length; i++) {
            if (Day12.directions[i] == target)
                return i;
        }
        throw new UnsupportedOperationException();
    }


    private static int manhattanDistance(Tuple3<Integer, Integer, Movement> pos) {
        return Math.abs(-pos.v1()) + Math.abs(-pos.v2());
    }

    public static Tuple2<Movement, Integer> parser(String s) {
        Day12.Movement movement = Day12.Movement.valueOf(s.substring(0, 1));
        Integer amount = Integer.valueOf(s.substring(1));
        return Tuple.tuple(movement, amount);
    }
}
