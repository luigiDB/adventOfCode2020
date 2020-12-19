package exercises;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day19 {
    public static long es1(Stream<String> input) {
        List<String> list = input.collect(Collectors.toList());
        Map<Integer, String> sortedRules = parseRules(list);

        String regex = parse(sortedRules, 0);

        String matches = list.get(1);
        return Arrays.stream(matches.split("\n"))
                .filter(m -> m.matches(regex))
                .count();
    }

    /**
     * Expanding the above by hand we can notice that
     * rule 8 match something in the form of 42 42 42 42 ...
     * rule 11 something in the form of 42 42 42 ... 31 31 31 with equals number of 42 and 31
     * thus after creating the regex for 42 and 31 we simply need to count the number of sequential occurrences of 42
     * first and 31 after if we count more occurrences of 42 and nothing more remains in the string we have a match.
     */
    public static long es2(Stream<String> input) {
        List<String> list = input.collect(Collectors.toList());
        Map<Integer, String> sortedRules = parseRules(list);
        sortedRules.put(8, " 42 | 42 8");
        sortedRules.put(11, " 42 31 | 42 11 31");
        String regex42 = parse(sortedRules, 42);
        String regex31 = parse(sortedRules, 31);

        Pattern startWith42 = Pattern.compile("^" + regex42);
        Pattern startWith31 = Pattern.compile("^" + regex31);
        Pattern total = Pattern.compile("^" + regex42 + "*" + regex31 + "*$");

        int valid = 0;
        for (String test : list.get(1).split("\n")) {
            Matcher matcher = total.matcher(test);
            if(!matcher.find())
                continue;
            Tuple2<Integer, String> tmp = countHowManyStart(test, startWith42);
            Tuple2<Integer, String> tmp2 = countHowManyStart(tmp.v2, startWith31);
            if (tmp2.v2.equals("") && tmp2.v1 > 0 && tmp.v1 > tmp2.v1) {
                valid++;
            }
        }
        return valid;
    }

    private static Tuple2<Integer,String> countHowManyStart(String in, Pattern pattern) {
        int count = 0;
        while (true) {
            Matcher matcher = pattern.matcher(in);
            if (matcher.find()) {
                in = matcher.replaceFirst("");
                count++;
            } else
                break;
        }
        return Tuple.tuple(count, in);
    }

    private static String parse(Map<Integer, String> rules, int startIndex) {
        StringBuilder builder = new StringBuilder();
        recursiveParse(rules, builder, startIndex);
        return builder.toString();
    }

    private static void recursiveParse(Map<Integer, String> rules, StringBuilder builder, int index) {
        String rule = rules.get(index);
        if (rule.contains("\"")) {
            builder.append(rule.replaceAll("\"", "").replaceAll(" ", ""));
            return;
        }

        String[] ors = rule.split("\\|");
        if (ors.length == 1) {
            // AND
            for (Integer next : parseNumbers(ors[0]))
                recursiveParse(rules, builder, next);
        } else {
            builder.append("(");
            for (String or : ors) {
                // OR
                for (Integer next : parseNumbers(or))
                    recursiveParse(rules, builder, next);
                builder.append("|");
            }
            // remove one | too much at the end
            builder.setLength(builder.length() - 1);
            builder.append(")");
        }
    }

    private static List<Integer> parseNumbers(String in) {
        List<Integer> list = new ArrayList<>();
        for (String test : in.split(" ")) {
            try {
                list.add(Integer.valueOf(test));
            } catch (NumberFormatException ignored) {
            }
        }
        return list;
    }

    private static Map<Integer, String> parseRules(List<String> list) {
        String rules = list.get(0);
        Map<Integer, String> map = new HashMap<>();
        Arrays.stream(rules.split("\n"))
                .forEach(s -> {
                    String[] split = s.split(":");
                    map.put(Integer.valueOf(split[0]), split[1]);
                });
        return map;
    }
}
