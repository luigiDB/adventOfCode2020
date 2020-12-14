package utilities;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

public class CombinatorialCalculusUtilsTest {

    private final List<Integer> input = ImmutableList.of(0, 1, 2);

    @Test
    public void allCombinations() {
        List<Set<Integer>> collect = CombinatorialCalculusUtils.allCombinations(input)
                .collect(Collectors.toList());
        assertThat(collect, hasSize(7));
        assertThat(collect,
                containsInAnyOrder(
                        ImmutableSet.of(0),
                        ImmutableSet.of(1),
                        ImmutableSet.of(2),
                        ImmutableSet.of(0, 1),
                        ImmutableSet.of(1, 2),
                        ImmutableSet.of(0, 2),
                        ImmutableSet.of(0, 1, 2)
                ));
    }

    @Test
    public void combinations() {
        List<Set<Integer>> combinations = CombinatorialCalculusUtils.combinations(input, 2)
                .collect(Collectors.toList());
        assertThat(combinations, hasSize(3));
        assertThat(combinations, containsInAnyOrder(
                ImmutableSet.of(0, 1),
                ImmutableSet.of(1, 2),
                ImmutableSet.of(0, 2)
        ));
    }

    @Test
    public void permutations() {
        Stream<List<Integer>> permutations = CombinatorialCalculusUtils.permutations(input);
        List<List<Integer>> collect = permutations.collect(Collectors.toList());
        assertThat(collect, hasSize(6));
        assertThat(collect, containsInAnyOrder(
                ImmutableList.of(0, 1, 2),
                ImmutableList.of(0, 2, 1),
                ImmutableList.of(1, 0, 2),
                ImmutableList.of(1, 2, 0),
                ImmutableList.of(2, 0, 1),
                ImmutableList.of(2, 1, 0)
        ));

    }
}