package exercises;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class Day23 {
    public static String es1(String rawInput, int moves) {
        LinkedList<Long> list = new LinkedList<>();
        for (int i = 0; i < rawInput.length(); i++) {
            list.addLast(Long.parseLong(String.valueOf(rawInput.charAt(i))));
        }

        long current = list.get(0);
        for (int move = 0; move < moves; move++) {
            int currentIndex = list.indexOf(current);
            List<Long> nextThree = getNextThree(list, currentIndex);

            long destination = current;
            while (true) {
                destination -= 1;
                if (destination == 0) {
                    destination = 9;
                }
                if (nextThree.contains(destination))
                    continue;
                break;
            }

            list.removeAll(nextThree);
            int destinationIndex = list.indexOf(destination);
            for (int i = 2; i >= 0; i--) {
                list.add(destinationIndex + 1, nextThree.get(i));
            }

            current = list.get((list.indexOf(current) + 1) % list.size());
        }

        int indexOfOne = list.indexOf(1L);
        List<Long> firstHalf = list.subList(0, indexOfOne);
        List<Long> secondHalf = list.subList(indexOfOne + 1, list.size());
        LinkedList<Long> resultList = new LinkedList<>(secondHalf);
        resultList.addAll(firstHalf);
        return StringUtils.join(resultList.toArray());
    }

    private static List<Long> getNextThree(LinkedList<Long> list, int currentIndex) {
        LinkedList<Long> nextThree = new LinkedList<>();
        for (int i = 1; i < 4; i++) {
            nextThree.add(list.get((currentIndex + i) % list.size()));
        }
        return nextThree;
    }

    public static long es2(String rawInput) {
        return 0;
    }

}
