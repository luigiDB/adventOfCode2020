package exercises;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day9 {
    public static BigInteger es1(Stream<BigInteger> input, int retention) {
        List<BigInteger> collect = input.collect(Collectors.toList());
        for (int i = retention; i < collect.size(); i++) {
            BigInteger current = collect.get(i);
            boolean match = find_sum(collect.subList(i - retention, i), current);
            if (!match)
                return current;
        }
        return BigInteger.ZERO;
    }

    public static BigInteger es2(Stream<BigInteger> input, int retention) {
        List<BigInteger> collect = input.collect(Collectors.toList());
        BigInteger target = es1(collect.stream(), retention);

        for (int i = 0; i < collect.size(); i++) {
            BigInteger sum = BigInteger.ZERO;
            int index = i;
            while (sum.compareTo(target) < 0) {
                sum = sum.add(collect.get(index));
                index++;
            }
            if (sum.compareTo(target) == 0 && index - i > 1)
                return sumOfMinAndMax(collect.subList(i, index));
        }

        return BigInteger.ZERO;
    }

    private static BigInteger sumOfMinAndMax(List<BigInteger> list) {
        ArrayList<BigInteger> copyOfInput = new ArrayList<>(list);
        copyOfInput.sort(BigInteger::compareTo);
        return copyOfInput.get(0).add(copyOfInput.get(copyOfInput.size() - 1));
    }


    private static boolean find_sum(List<BigInteger> input, BigInteger target) {
        Set<BigInteger> seen = new HashSet<>();
        for (BigInteger current : input) {
            BigInteger match = target.subtract(current);
            if (seen.contains(match)) {
                return true;
            }
            seen.add(current);
        }
        return false;
    }
}
