package exercises;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Day23 {
    public static String es1(String rawInput, long moves) {
        LinkedList<Long> list = initGamePart1(rawInput);

        game(list, moves, 9);

        int indexOfOne = list.indexOf(1L);
        List<Long> firstHalf = list.subList(0, indexOfOne);
        List<Long> secondHalf = list.subList(indexOfOne + 1, list.size());
        LinkedList<Long> resultList = new LinkedList<>(secondHalf);
        resultList.addAll(firstHalf);
        return StringUtils.join(resultList.toArray());
    }

    public static long es2(String rawInput, long moves, int size) {
        DoubleLinkedNode<Long> last = null;
        Map<Long, DoubleLinkedNode<Long>> cups = new HashMap<>();

        long currentValue = 0;
        for (int i = 0; i < rawInput.length(); i++) {
            long value = Long.parseLong(String.valueOf(rawInput.charAt(i)));
            DoubleLinkedNode<Long> newElem = new DoubleLinkedNode<>(value);
            if (last == null) {
                currentValue = value;
                last = newElem;
            } else
                last = last.insertAfter(newElem);
            cups.put(value, newElem);
        }

        for (long i = 10; i <= size; i++) {
            DoubleLinkedNode<Long> n = new DoubleLinkedNode<>(i);
            last = last.insertAfter(n);
            cups.put(i, n);
        }

        for (int i = 0; i < moves; i++) {
            DoubleLinkedNode<Long> currentCup = cups.get(currentValue);
            DoubleLinkedNode<Long> next1 = currentCup.next.remove();
            DoubleLinkedNode<Long> next2 = currentCup.next.remove();
            DoubleLinkedNode<Long> next3 = currentCup.next.remove();

            long destination = currentValue;
            while (true) {
                destination -= 1;
                if (destination == 0) {
                    destination = size;
                }
                if (next1.value == destination || next2.value==destination || next3.value == destination)
                    continue;
                break;
            }

            DoubleLinkedNode<Long> destinationCup = cups.get(destination);
            destinationCup.insertAfter(next3);
            destinationCup.insertAfter(next2);
            destinationCup.insertAfter(next1);
            currentValue = currentCup.next.value;
        }

        DoubleLinkedNode<Long> cupOne = cups.get(1L);
        return cupOne.next.value * cupOne.next.next.value;
    }

    private static void game(LinkedList<Long> inputList, long moves, long maximumNumber) {
        long current = inputList.get(0);
        for (int move = 0; move < moves; move++) {
            int currentIndex = inputList.indexOf(current);
            List<Long> nextThree = getNextThree(inputList, currentIndex);

            long destination = current;
            while (true) {
                destination -= 1;
                if (destination == 0) {
                    destination = maximumNumber;
                }
                if (nextThree.contains(destination))
                    continue;
                break;
            }

            inputList.removeAll(nextThree);
            int destinationIndex = inputList.indexOf(destination);
            for (int i = 2; i >= 0; i--) {
                inputList.add(destinationIndex + 1, nextThree.get(i));
            }

            current = inputList.get((inputList.indexOf(current) + 1) % inputList.size());
        }
    }

    private static LinkedList<Long> initGamePart1(String rawInput) {
        LinkedList<Long> list = new LinkedList<>();
        for (int i = 0; i < rawInput.length(); i++) {
            list.addLast(Long.parseLong(String.valueOf(rawInput.charAt(i))));
        }
        return list;
    }

    private static List<Long> getNextThree(LinkedList<Long> list, int currentIndex) {
        LinkedList<Long> nextThree = new LinkedList<>();
        for (int i = 1; i < 4; i++) {
            nextThree.add(list.get((currentIndex + i) % list.size()));
        }
        return nextThree;
    }

    private static class DoubleLinkedNode<T> {
        public DoubleLinkedNode<T> next = this;
        public DoubleLinkedNode<T> previous = this;

        public final T value;

        public DoubleLinkedNode(T value) {
            this.value = value;
        }

        public DoubleLinkedNode<T> remove() {
            previous.next = next;
            next.previous = previous;
            return this;
        }

        public DoubleLinkedNode<T> insertAfter(DoubleLinkedNode<T> node) {
            next.previous = node;
            node.previous = this;
            node.next = next;
            this.next = node;

            return node;
        }
    }
}
