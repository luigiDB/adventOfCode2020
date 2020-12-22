package exercises;

import org.apache.commons.lang3.StringUtils;
import org.jooq.lambda.Seq;

import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day22 {
    public static long es1(Stream<String> rawInput) {
        List<Queue<Long>> players = initPlayers(rawInput);
        Queue<Long> player1 = players.get(0);
        Queue<Long> player2 = players.get(1);

        while (!player1.isEmpty() && !player2.isEmpty()) {
            Long poll1 = player1.poll();
            Long poll2 = player2.poll();
            fillWinningDeck(player1, player2, poll1, poll2, () -> poll1 > poll2);
        }

        return evaluateResult(players);
    }

    public static long es2(Stream<String> rawInput) {
        List<Queue<Long>> players = initPlayers(rawInput);
        Queue<Long> player1 = players.get(0);
        Queue<Long> player2 = players.get(1);

        game2(player1, player2);

        return evaluateResult(players);
    }

    private static long evaluateResult(List<Queue<Long>> players) {
        long result = 0;
        for (Queue<Long> player : players) {
            if (!player.isEmpty()) {
                while (!player.isEmpty()) {
                    int multiplier = player.size();
                    result += (player.poll() * multiplier);
                }
                break;
            }
        }
        return result;
    }

    private static int game2(Queue<Long> player1, Queue<Long> player2) {
        Set<String> rounds = new HashSet<>();

        while (!player1.isEmpty() && !player2.isEmpty()) {
            String deck1 = StringUtils.join(player1, "|");
            String deck2 = StringUtils.join(player2, "|");
            if(rounds.contains(deck1) || rounds.contains(deck2))
                return 1;
            rounds.add(deck1);
            rounds.add(deck2);

            Long poll1 = player1.poll();
            Long poll2 = player2.poll();

            if(player1.size() >= poll1 && player2.size() >= poll2) {
                //new game
                Queue<Long> player1Copy = initQueueForSubGame(player1, poll1);
                Queue<Long> player2Copy = initQueueForSubGame(player2, poll2);
                int i = game2(player1Copy, player2Copy);
                fillWinningDeck(player1, player2, poll1, poll2, () -> i == 1);
            } else
                fillWinningDeck(player1, player2, poll1, poll2, () -> poll1 > poll2);
        }

        if(player1.isEmpty())
            return 2;
        else
            return 1;
    }

    private static void fillWinningDeck(Queue<Long> player1, Queue<Long> player2, Long poll1, Long poll2, BooleanSupplier firstPlayerWinningCondition) {
        if (firstPlayerWinningCondition.getAsBoolean()) {
            player1.offer(poll1);
            player1.offer(poll2);
        } else {
            player2.offer(poll2);
            player2.offer(poll1);
        }
    }

    private static Queue<Long> initQueueForSubGame(Queue<Long> given, long size) {
        Queue<Long> copyQueue = new LinkedList<>();
        LinkedList<Long> sup = (LinkedList<Long>) given;
        for (int i = 0; i < size; i++) {
            copyQueue.offer(sup.get(i));
        }
        return copyQueue;
    }

    private static List<Queue<Long>> initPlayers(Stream<String> rawInput) {
        return rawInput
                .map(strings -> {
                    Queue<Long> playerDeck = new LinkedList<>();
                    Seq.seq(Arrays.stream(strings.split("\n")))
                            .skip(1)
                            .forEach(card -> playerDeck.offer(Long.valueOf(card)));
                    return playerDeck;
                })
                .collect(Collectors.toList());
    }

}
