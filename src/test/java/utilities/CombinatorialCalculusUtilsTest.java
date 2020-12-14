package utilities;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

public class CombinatorialCalculusUtilsTest {

    @Test
    public void combinations() {
        Set<Set<Integer>> combinations = Sets.combinations(ImmutableSet.of(0, 1, 2), 2);
        assertThat(combinations, hasSize(3));
        assertThat(combinations, containsInAnyOrder(
                ImmutableSet.of(0, 1),
                ImmutableSet.of(1, 2),
                ImmutableSet.of(0, 2)
        ));
    }

    @Test
    public void permutations() {
        List<Integer> list = Arrays.asList(0, 1, 2);
        Stream<List<Integer>> permutations = CombinatorialCalculusUtils.permutations(list);
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