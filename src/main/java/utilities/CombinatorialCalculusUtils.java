package utilities;

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
    public static <T> Stream<List<T>> combinations(List<T> list) {
        final long N = (long) Math.pow(2, list.size());
        return StreamSupport
                .stream(new Spliterators.AbstractSpliterator<List<T>>(N, SIZED) {
                    long i = 1;

                    @Override
                    public boolean tryAdvance(Consumer<? super List<T>> action) {
                        if (i < N) {
                            List<T> out = new ArrayList<T>(Long.bitCount(i));
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

//    public static <T> Stream<List<T>> combinations(List<T> list, int len) {
//        Set<T>
//        Set<Set<Integer>> combinations = Sets.combinations(new HashSet<>(list), 2);
//        final long N = (long) Math.pow(2, list.size());
//        return StreamSupport
//                .stream(new Spliterators.AbstractSpliterator<List<T>>(N, SIZED) {
//                    long i = 1;
//
//                    @Override
//                    public boolean tryAdvance(Consumer<? super List<T>> action) {
//                        if (i < N) {
//                            List<T> out = new ArrayList<T>(Long.bitCount(i));
//                            for (int bit = 0; bit < list.size(); bit++) {
//                                if ((i & (1L << bit)) != 0) {
//                                    out.add(list.get(bit));
//                                }
//                            }
//                            action.accept(out);
//                            ++i;
//                            return true;
//                        } else {
//                            return false;
//                        }
//                    }
//                }, false);
//    }

    public static <T> Stream<List<T>> permutations(List<T> list) {
        long N = LongStream.rangeClosed(1, list.size())
                .reduce(1, (long x, long y) -> x * y);
        return Stream.concat(
                Stream.of(new ArrayList<>(list)),
                StreamSupport
                        .stream(new Spliterators.AbstractSpliterator<List<T>>(N, SIZED) {
                                    int i = 0;
                                    final int[] indexes = new int[list.size()];

                                    @Override
                                    public boolean tryAdvance(Consumer<? super List<T>> action) {
                                        while (i < list.size()) {
                                            if (indexes[i] < i) {
                                                swap(list, i % 2 == 0 ? 0 : indexes[i], i);
                                                action.accept(new ArrayList<>(list));
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
