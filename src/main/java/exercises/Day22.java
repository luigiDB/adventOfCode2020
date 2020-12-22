package exercises;

import org.apache.commons.lang3.StringUtils;
import org.jooq.lambda.Seq;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day22 {
    public static long es1(Stream<String> rawInput) {
        List<Queue<Long>> players = rawInput
                .map(strings -> {
                    Queue<Long> playerDeck = new LinkedList<>();
                    Seq.seq(Arrays.stream(strings.split("\n")))
                            .skip(1)
                            .forEach(card -> playerDeck.offer(Long.valueOf(card)));
                    return playerDeck;
                })
                .collect(Collectors.toList());

        Queue<Long> player1 = players.get(0);
        Queue<Long> player2 = players.get(1);

        while (!player1.isEmpty() && !player2.isEmpty()) {
            Long poll1 = player1.poll();
            Long poll2 = player2.poll();

            if (poll1 > poll2) {
                player1.offer(poll1);
                player1.offer(poll2);
            } else {
                player2.offer(poll2);
                player2.offer(poll1);
            }
        }

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

    public static long es2(Stream<String> rawInput) {
        List<Queue<Long>> players = rawInput
                .map(strings -> {
                    Queue<Long> playerDeck = new LinkedList<>();
                    Seq.seq(Arrays.stream(strings.split("\n")))
                            .skip(1)
                            .forEach(card -> playerDeck.offer(Long.valueOf(card)));
                    return playerDeck;
                })
                .collect(Collectors.toList());

        Queue<Long> player1 = players.get(0);
        Queue<Long> player2 = players.get(1);

        game2(player1, player2);

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
        Set<String> player1rounds = new HashSet<>();
        Set<String> player2rounds = new HashSet<>();

        while (!player1.isEmpty() && !player2.isEmpty()) {
            if(player1rounds.contains(StringUtils.join(player1, "|")))
                return 1;
            if(player2rounds.contains(StringUtils.join(player2, "|")))
                return 1;
            player1rounds.add(StringUtils.join(player1, "|"));
            player2rounds.add(StringUtils.join(player2, "|"));

            Long poll1 = player1.poll();
            Long poll2 = player2.poll();

            if(player1.size() >= poll1 && player2.size() >= poll2) {
                //new game
                Queue<Long> player1Copy = initQueue(player1, poll1);
                Queue<Long> player2Copy = initQueue(player2, poll2);
                int i = game2(player1Copy, player2Copy);
                if(i == 1 ) {
                    player1.offer(poll1);
                    player1.offer(poll2);
                } else {
                    player2.offer(poll2);
                    player2.offer(poll1);
                }
            } else {
                if (poll1 > poll2) {
                    player1.offer(poll1);
                    player1.offer(poll2);
                } else {
                    player2.offer(poll2);
                    player2.offer(poll1);
                }
            }

        }

        if(player1.isEmpty())
            return 2;
        else
            return 1;
    }

    private static Queue<Long> initQueue(Queue<Long> given, long size) {
        Queue<Long> copyQueue = new LinkedList<>();
        LinkedList<Long> sup = (LinkedList<Long>) given;
        for (int i = 0; i < size; i++) {
            copyQueue.offer(sup.get(i));
        }
        return copyQueue;
    }

}
