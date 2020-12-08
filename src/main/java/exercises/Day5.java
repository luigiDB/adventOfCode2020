package exercises;

import org.apache.commons.lang3.tuple.Pair;

import java.util.OptionalInt;
import java.util.stream.Stream;

public class Day5 {
    public static OptionalInt es1(Stream<String> input) {
        return input
                .map(Day5::evaluatePosition)
                .mapToInt(Day5::evaluateId)
                .max();
    }

    private static int evaluateId(Pair<Integer, Integer> pos) {
        return (pos.getLeft() * 8) + pos.getRight();
    }

    public static int es2(Stream<String> input) {
        Pair<Integer, Integer> lastConsecutiveOccupiedPlace = input
                .map(Day5::evaluatePosition)
                .sorted()
                .reduce(Pair.of(0, 0), Day5::areSequential);

        return evaluateId(nextSequentialPlace(lastConsecutiveOccupiedPlace));
    }

//    public static int es2Redo(Stream<String> input) {
//        List<String> collect = input.collect(Collectors.toList());
//        ContinuousMatrix<Boolean> matrix = new ContinuousMatrix(Boolean.class, collect.size(), collect.get(0).length(), false);
//
//        collect.stream()
//                .map(Day5::evaluatePosition)
//                .forEach(pos -> matrix.set(pos, true));
//
//        while (matrix.hasNext()) {
//            Optional<Boolean> next = matrix.next();
//            if (matrix.previous().get() &&
//                next.get() &&
//                    matrix.next().get())
//                return matrix.nextIndex().
//        }
//    }

    private static Pair<Integer, Integer> areSequential(Pair<Integer, Integer> base, Pair<Integer, Integer> next) {
        if (base.equals(Pair.of(0, 0)))
            return next;

        Pair<Integer, Integer> evaluatedNExt = nextSequentialPlace(base);
        if (next.equals(evaluatedNExt))
            return next;
        else
            return base;
    }

    private static Pair<Integer, Integer> nextSequentialPlace(Pair<Integer, Integer> base) {
        int nextPos = (base.getRight() + 1) % 8;
        return Pair.of(
                nextPos == 0 ? base.getLeft() + 1 : base.getLeft(),
                nextPos);
    }

    private static Pair<Integer, Integer> evaluatePosition(String passport) {
        int row = getPos('F', 'B', passport.substring(0, 7));
        int column = getPos('L', 'R', passport.substring(7, 10));
        return Pair.of(row, column);
    }

    private static int getPos(char lower, char upper, String string) {
        int start = 0;
        int end = (int) Math.pow(2, string.length()) - 1;
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            int mid = (start + end) / 2;
            if (c == lower) {
                end = mid;
            } else if (c == upper) {
                start = mid + 1;
            } else {
                throw new RuntimeException(c + "not recognised");
            }
        }
        return start;
    }
}
