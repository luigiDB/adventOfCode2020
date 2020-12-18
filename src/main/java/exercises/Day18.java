package exercises;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day18 {

    public static long es1(Stream<String> input) {
        List<String> expressions = input.collect(Collectors.toList());

        List<Long> res = new ArrayList<>();
        for (String expression : expressions) {
            long partial = resolveExpression(expression);
            res.add(partial);
        }

        return res.stream().reduce(0L, Long::sum);
    }

    private static long resolveExpression(String expression) {
        Stack<Tuple2<Long, Character>> stack = new Stack<>();
        long partial = 0;
        Character lastOperand = null;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            long numericValue = Character.getNumericValue(c);
            if (numericValue > 0) {
                if (lastOperand == null)
                    partial = numericValue;
                else
                    partial = operation(partial, numericValue, String.valueOf(lastOperand));
            }
            switch (c) {
                case ' ':
                    continue;
                case '+':
                case '*':
                    lastOperand = c;
                    break;
                case '(':
                    stack.push(Tuple.tuple(partial, lastOperand));
                    partial = 0;
                    lastOperand = null;
                    break;
                case ')':
                    Tuple2<Long, Character> pop = stack.pop();
                    if (pop.v2 == null)
                        if (pop.v1 != 0) {
                            partial = pop.v1;
                        } else {
                            partial = partial;
                        }
                    else
                        partial = operation(partial, pop.v1, String.valueOf(pop.v2));
                    break;
            }
        }
        return partial;
    }

    public static long operation(long left, long right, String operator) {
        if (operator.equals("+")) {
            return left + right;
        } else if (operator.equals("*")) {
            return left * right;
        }
        throw new UnsupportedOperationException();
    }


    public static long es2(Stream<String> input) {
        List<String[]> lines = input
                .map(s -> s.replaceAll(" ", "").split(""))
                .collect(Collectors.toList());

        List<String> part2 = new ArrayList<>();
        for (String[] line : lines) {
            part2.add(resolveExpression(line));
        }
        return part2.stream().map(Long::valueOf).reduce(0L, Long::sum);
    }

    public static String resolveExpression(String[] line) {
        Stack<List<String>> stack = new Stack<>();
        List<String> current = new ArrayList<>();
        for (String token : line) {
            switch (token) {
                case "(":
                    stack.push(current);
                    current = new ArrayList<>();
                    break;
                case ")":
                    List<String> pop = stack.pop();
                    pop.add(resolveSums(current));
                    current = pop;
                    break;
                default:
                    current.add(token);
            }
        }
        return resolveSums(current);
    }

    public static String resolveSums(List<String> line) {
        List<String> resultExpression = new ArrayList<>();

        for (int i = 0; i < line.size(); ++i) {
            String current = line.get(i);
            if (current.equals("+")) {
                long last = Long.parseLong(resultExpression.remove(resultExpression.size() - 1));
                long right = Long.parseLong(line.get(i + 1));
                resultExpression.add(String.valueOf(operation(last, right, current)));
                i++;
            } else {
                resultExpression.add(line.get(i));
            }
        }

        long total = Long.parseLong(resultExpression.get(0));
        for (int i = 1; i < resultExpression.size(); i++) {
            String current = resultExpression.get(i);
            if (current.equals("*")) {
                long right = Long.parseLong(resultExpression.get(i + 1));
                total = operation(total, right, current);
            }
        }
        return String.valueOf(total);
    }

}
