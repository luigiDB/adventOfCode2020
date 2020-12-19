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

    public static long es2(Stream<String> input) {
        List<String> list = input.collect(Collectors.toList());
        Map<Integer, String> sortedRules = parseRules(list);
        sortedRules.put(8, " 42 | 42 8");
        sortedRules.put(11, " 42 31 | 42 11 31");
        String regex42 = parse(sortedRules, 42);
        String regex31 = parse(sortedRules, 31);

        String matches = list.get(1);
        Pattern startWith42 = Pattern.compile("^" + regex42);
        Pattern startWith31 = Pattern.compile("^" + regex31);

        long count = Arrays.stream(matches.split("\n"))
                .filter(m -> {
                    Pattern total = Pattern.compile("^" + regex42 + "*" + regex31 + "*$");
                    if (total.matcher(m).find()) {
                        return true;
                    }
                    return false;
                }).count();

        int valid = 0;
        for (String test : matches.split("\n")) {
            Pattern total = Pattern.compile("^" + regex42 + "*" + regex31 + "*$");
            if(!total.matcher(test).find())
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
        String support = in;
        while (true) {
            Matcher matcher = pattern.matcher(support);
            if (matcher.find()) {
                support = support.substring(matcher.end());
                count++;
            } else
                break;
        }
        return Tuple.tuple(count, support);
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
            for (Integer next : parseNumbers(ors[0]))
                recursiveParse(rules, builder, next);
        } else {
            builder.append("(");
            for (String or : ors) {
                for (Integer next : parseNumbers(or))
                    recursiveParse(rules, builder, next);
                builder.append("|");
            }
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
