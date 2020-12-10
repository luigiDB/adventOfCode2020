package exercises;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day10 {
    public static int es1(Stream<Integer> input) {
        List<Integer> sorted = input.collect(Collectors.toList());
        sorted.add(0);
        Collections.sort(sorted);
        int dist1 = 0;
        int dist2 = 0;
        int dist3 = 0;
        for (int i = 1; i < sorted.size(); i++) {
            switch (sorted.get(i) - sorted.get(i - 1)) {
                case 1:
                    dist1++;
                    break;
                case 2:
                    dist2++;
                    break;
                case 3:
                    dist3++;
                    break;
                default:
                    throw new UnsupportedOperationException("Not supported distance");
            }
        }
        return dist1 * (dist3 + 1);
    }


    public static BigInteger es2Dp(Stream<Integer> input) {
        List<Integer> sorted = input.collect(Collectors.toList());
        sorted.add(0);
        Collections.sort(sorted);
        sorted.add(sorted.get(sorted.size() - 1) + 3);
        Collections.sort(sorted);

        BigInteger[] counter = new BigInteger[sorted.size()];
        Arrays.fill(counter, BigInteger.ZERO);
        int lastIndex = counter.length - 1;
        counter[lastIndex] = BigInteger.ONE;

        for (int i = lastIndex - 1; i >= 0; i--) {
            Integer current = sorted.get(i);
            BigInteger counterSoFar = BigInteger.ZERO;
            int j = 1;
            while (i + j <= lastIndex) {
                Integer jump = sorted.get(i + j);
                if (jump - current > 3)
                    break;
                counterSoFar = counterSoFar.add(counter[i + j]);
                j++;
            }
            counter[i] = counterSoFar;
        }

        return counter[0];
    }

    public static BigInteger es2Recursive(Stream<Integer> input) {
        List<Integer> sorted = input.collect(Collectors.toList());
        sorted.add(0);
        Collections.sort(sorted);
        sorted.add(sorted.get(sorted.size() - 1) + 3);
        Collections.sort(sorted);

        Set<Integer> set = new HashSet<>(sorted);
        Map<Integer, BigInteger> memoization = new HashMap<>();
        BigInteger next = next(set, 0, memoization);
        return next;
    }

    private static BigInteger next(Set<Integer> set, int current, Map<Integer, BigInteger> memoization) {
        if (memoization.containsKey(current))
            return memoization.get(current);
        BigInteger possibleOutcomes = BigInteger.ZERO;

        for (int i = 1; i < 4; i++) {
            if (set.contains(current + i))
                possibleOutcomes = possibleOutcomes.add(next(set, current + i, memoization));
        }

        if (possibleOutcomes.compareTo(BigInteger.ZERO) == 0) {
            memoization.put(current, BigInteger.ONE);
            return BigInteger.ONE;
        }

        memoization.put(current, possibleOutcomes);
        return possibleOutcomes;
    }


}
