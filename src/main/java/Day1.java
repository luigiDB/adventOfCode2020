import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day1 {
    public static int es1(List<Integer> input) {
        return find_sum(input, 2020);
    }

    private static int find_sum(List<Integer> input, int target) {
        Set<Integer> seen = new HashSet<>();
        for (int current : input) {
            int match = target - current;
            if (seen.contains(match)) {
                return current * match;
            }
            seen.add(current);
        }
        return 0;
    }

    public static int es2(List<Integer> input) {
        for (int i = 0; i < input.size() - 1; i++) {
            int sum = find_sum(input.subList(i + 1, input.size()), 2020 - input.get(i));
            if(sum != 0)
                return sum*input.get(i);
        }
        return 0;
    }

}
