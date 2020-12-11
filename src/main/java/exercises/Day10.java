package exercises;

import javax.swing.plaf.TableHeaderUI;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day10 {

    private static BigInteger THREE = BigInteger.valueOf(3);;

    public static BigInteger es1(Stream<BigInteger> input) {
        List<BigInteger> sorted = input.collect(Collectors.toList());
        sorted.add(BigInteger.ZERO);
        sorted.add(Collections.max(sorted).add(THREE));
        Collections.sort(sorted);

        BigInteger dist1 = BigInteger.ZERO;
        BigInteger dist3 = BigInteger.ZERO;
        for (int i = 1; i < sorted.size(); i++) {
            switch (sorted.get(i).subtract(sorted.get(i - 1)).intValue()) {
                case 1:
                    dist1 = dist1.add(BigInteger.ONE);
                    break;
                case 2:
                    break;
                case 3:
                    dist3 = dist3.add(BigInteger.ONE);
                    break;
                default:
                    throw new UnsupportedOperationException("Not supported distance");
            }
        }
        return dist1.multiply(dist3);
    }


    public static BigInteger es2Dp(Stream<BigInteger> input) {
        List<BigInteger> sorted = input.collect(Collectors.toList());
        sorted.add(BigInteger.ZERO);
        sorted.add(Collections.max(sorted).add(THREE));
        Collections.sort(sorted);

        BigInteger[] counter = new BigInteger[sorted.size()];
        Arrays.fill(counter, BigInteger.ZERO);
        int lastIndex = counter.length - 1;
        counter[lastIndex] = BigInteger.ONE;

        for (int i = lastIndex - 1; i >= 0; i--) {
            BigInteger current = sorted.get(i);
            BigInteger counterSoFar = BigInteger.ZERO;
            int j = 1;
            while (i + j <= lastIndex) {
                BigInteger jump = sorted.get(i + j);
                if (jump.subtract(current).compareTo(THREE) > 0)
                    break;
                counterSoFar = counterSoFar.add(counter[i + j]);
                j++;
            }
            counter[i] = counterSoFar;
        }

        return counter[0];
    }

    public static BigInteger es2Recursive(Stream<BigInteger> input) {
        List<BigInteger> sorted = input.collect(Collectors.toList());
        sorted.add(BigInteger.ZERO);
        sorted.add(Collections.max(sorted).add(THREE));
        Collections.sort(sorted);

        Set<BigInteger> set = new HashSet<>(sorted);
        Map<BigInteger, BigInteger> memoization = new HashMap<>();
        return next(set, BigInteger.ZERO, memoization);
    }

    private static BigInteger next(Set<BigInteger> set, BigInteger current, Map<BigInteger, BigInteger> memoization) {
        if (memoization.containsKey(current))
            return memoization.get(current);
        BigInteger possibleOutcomes = BigInteger.ZERO;

        for (BigInteger i = BigInteger.ONE; i.compareTo(THREE) <= 0; i = i.add(BigInteger.ONE)) {
            if (set.contains(current.add(i)))
                possibleOutcomes = possibleOutcomes.add(next(set, current.add(i), memoization));
        }

        if (possibleOutcomes.compareTo(BigInteger.ZERO) == 0) {
            memoization.put(current, BigInteger.ONE);
            return BigInteger.ONE;
        }

        memoization.put(current, possibleOutcomes);
        return possibleOutcomes;
    }


}
