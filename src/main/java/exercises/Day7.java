package exercises;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 {
    public static int es1(Stream<Bag> input) {
        List<Bag> collect = input.collect(Collectors.toList());
        Map<String, Bag> bags = bagDefinition(collect);
        Map<String, Set<String>> graph = buildUndirectedGraph(collect.stream());

        Set<String> chain = new HashSet<>();

        Queue<String> visit = new LinkedList<>();
        visit.add("shiny gold");
        while (!visit.isEmpty()) {
            String poll = visit.poll();
            for (String next : graph.get(poll)) {
                /*
                I build an undirected graph of a direct one to follow opposite paths but at each step I need to check
                that the jump is valid in the original graph.
                 */
                if (bags.get(next).contains(poll)) {
                    chain.add(next);
                    visit.add(next);
                }
            }
        }

        return chain.size();
    }

    public static int es2(Stream<Bag> input) {
        List<Bag> collect = input.collect(Collectors.toList());
        Map<String, Bag> inputMap = bagDefinition(collect);

        return visit(inputMap, "shiny gold") - 1;
    }

    private static int visit(Map<String, Bag> inputMap, String node) {
        Bag bag = inputMap.get(node);
        if (bag.terminal())
            return 1;
        int sum = 0;
        for (Pair<Integer, String> next : bag.getContent()) {
            int oneBagContent = visit(inputMap, next.getRight()) * next.getLeft();
            sum += oneBagContent;
        }
        sum += 1;
        return sum;
    }

    private static Map<String, Bag> bagDefinition(List<Bag> collect) {
        Map<String, Bag> graph = new HashMap<>();
        for (Bag bag : collect) {
            graph.put(bag.getHolder(), bag);
        }
        return graph;
    }

    private static Map<String, Set<String>> buildUndirectedGraph(Stream<Bag> input) {
        Map<String, Set<String>> graph = new HashMap<>();

        input.forEach(bag -> {
            if (!graph.containsKey(bag.getHolder())) {
                graph.put(bag.getHolder(), new HashSet<>());
            }
            graph.get(bag.getHolder()).addAll(bag.getBags());

            for (String innerBag : bag.getBags()) {
                if (!graph.containsKey(innerBag)) {
                    graph.put(innerBag, new HashSet<>());
                }
                graph.get(innerBag).add(bag.getHolder());
            }
        });

        return graph;
    }

    public static Bag parser(String input) {
        String[] split = input.split("contain");
        List<Pair<Integer, String>> bags = new ArrayList<>();
        if (split[1].contains("no other bags"))
            return new Bag(removedTrailers(split[0]), Collections.emptyList());
        for (String contained : split[1].split(",")) {
            String[] bag = contained.trim().split(" ", 2);
            bags.add(Pair.of(Integer.valueOf(bag[0].trim()), removedTrailers(bag[1])));
        }
        return new Bag(removedTrailers(split[0]), bags);
    }

    private static String removedTrailers(String s) {
        String bagColor = s.trim();
        String replace = bagColor.replace("bags", "");
        String replace2 = replace.replace("bag", "");
        return replace2.replace(".", "").trim();
    }

    public static class Bag {
        private final String container;
        List<Pair<Integer, String>> bags;

        public Bag(String container, List<Pair<Integer, String>> bags) {
            this.container = container;
            this.bags = bags;
        }

        public String getHolder() {
            return container;
        }

        public List<Pair<Integer, String>> getContent() {
            return bags;
        }

        public boolean terminal() {
            return bags.size() == 0;
        }

        public List<String> getBags() {
            return bags.stream()
                    .map(Pair::getRight)
                    .collect(Collectors.toList());
        }

        public boolean contains(String right) {
            long count = bags.stream()
                    .map(Pair::getRight)
                    .filter(x -> x.equals(right))
                    .count();
            return count != 0L;
        }
    }
}
