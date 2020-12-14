package utilities;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.SIZED;

public class CombinatorialCalculusUtils {

    /**
     * Evaluates all possible combinations of all size
     *
     * @param list Provided set
     * @param <T>  The type of the list
     * @return A stream of all possible combinations
     */
    public static <T> Stream<Set<T>> allCombinations(List<T> list) {
        final long N = (long) Math.pow(2, list.size());
        return StreamSupport
                .stream(new Spliterators.AbstractSpliterator<Set<T>>(N, SIZED) {
                    long i = 1;

                    @Override
                    public boolean tryAdvance(Consumer<? super Set<T>> action) {
                        if (i < N) {
                            Set<T> out = new HashSet<>(Long.bitCount(i));
                            for (int bit = 0; bit < list.size(); bit++) {
                                if ((i & (1L << bit)) != 0) {
                                    out.add(list.get(bit));
                                }
                            }
                            action.accept(out);
                            ++i;
                            return true;
                        } else {
                            return false;
                        }
                    }
                }, false);
    }

    public static <T> Stream<Set<T>> combinations(List<T> list, int len) {
        return Sets.combinations(Set.copyOf(list), len).stream();
    }

    public static <T> Stream<List<T>> permutations(List<T> originalUnmodifiableList) {
        ArrayList<T> input = new ArrayList<>(originalUnmodifiableList);
        long N = LongStream.rangeClosed(1, originalUnmodifiableList.size())
                .reduce(1, (long x, long y) -> x * y);
        return Stream.concat(
                Stream.of(new ArrayList<>(input)),
                StreamSupport
                        .stream(new Spliterators.AbstractSpliterator<List<T>>(N, SIZED) {
                                    int i = 0;
                                    final int[] indexes = new int[originalUnmodifiableList.size()];

                                    @Override
                                    public boolean tryAdvance(Consumer<? super List<T>> action) {
                                        while (i < originalUnmodifiableList.size()) {
                                            if (indexes[i] < i) {
                                                swap(input, i % 2 == 0 ? 0 : indexes[i], i);
                                                action.accept(new ArrayList<>(input));
                                                indexes[i]++;
                                                i = 0;
                                                return true;
                                            } else {
                                                indexes[i] = 0;
                                                i++;
                                            }
                                        }
                                        return false;
                                    }
                                },
                                false)
        );
    }

    private static <T> void swap(List<T> input, int a, int b) {
        T tmp = input.get(a);
        input.set(a, input.get(b));
        input.set(b, tmp);
    }
}
