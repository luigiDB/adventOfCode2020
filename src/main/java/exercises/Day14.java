package exercises;

import com.google.common.base.Strings;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14 {
    public static BigInteger es1(Stream<String> input) {
        List<String> list = input.collect(Collectors.toList());
        Map<Integer, BigInteger> memory = new HashMap<>();

        String currentMask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
        for (String cmd : list) {
            if (isMask(cmd)) {
                currentMask = maskParser(cmd);
            } else {
                Tuple2<Integer, String> assigment = maskAssignment(cmd);
                String binaryNumber = Integer.toBinaryString(Integer.parseInt(assigment.v2));
                String paddedNumber = Strings.padStart(binaryNumber, 36, '0');
                StringBuilder a = new StringBuilder(paddedNumber);
                for (int i = 0; i < currentMask.length(); i++) {
                    char c = currentMask.charAt(currentMask.length() - 1 - i);
                    if (c == '0') {
                        a.setCharAt(a.length() - 1 - i, '0');
                    }
                    if (c == '1') {
                        a.setCharAt(a.length() - 1 - i, '1');
                    }
                }
                memory.put(assigment.v1, new BigInteger(a.toString(), 2));
            }
        }

        return memory.values().stream().reduce(BigInteger::add).orElse(BigInteger.ZERO);

    }

    public static BigInteger es2(Stream<String> input) {
        List<String> list = input.collect(Collectors.toList());
        Map<BigInteger, BigInteger> memory = new HashMap<>();

        String currentMask = "000000000000000000000000000000000000";
        for (String cmd : list) {
            if (isMask(cmd)) {
                currentMask = maskParser(cmd);
            } else {
                Tuple2<String, BigInteger> assigment = maskAssignmentString(cmd);

                String binaryNumber = Integer.toBinaryString(Integer.parseInt(assigment.v1));
                String paddedNumber = Strings.padStart(binaryNumber, 36, '0');
                StringBuilder a = new StringBuilder(paddedNumber);
                for (int i = 0; i < currentMask.length(); i++) {
                    char c = currentMask.charAt(currentMask.length() - 1 - i);
                    if (c == '1') {
                        a.setCharAt(a.length() - 1 - i, '1');
                    }
                    if (c == 'X') {
                        a.setCharAt(a.length() - 1 - i, 'X');
                    }
                }
                fillMemory(memory, a.toString(), assigment.v2);
            }
        }

        return memory.values().stream().reduce(BigInteger::add).orElse(BigInteger.ZERO);
    }

    private static void fillMemory(Map<BigInteger, BigInteger> memory, String addressFamily, BigInteger value) {
        Set<String> values = new HashSet<>();
        values.add(addressFamily);

        while (values.iterator().next().contains("X")) {
            Set<String> support = new HashSet<>();
            int index = values.iterator().next().indexOf("X");
            values.forEach(str -> {
                StringBuilder tmp = new StringBuilder(str);
                tmp.setCharAt(index, '1');
                support.add(tmp.toString());
                tmp.setCharAt(index, '0');
                support.add(tmp.toString());
            });
            values = support;
        }

        values.forEach(val -> memory.put(new BigInteger(val, 2), value));
    }


    private static boolean isMask(String in) {
        return in.contains("mask");
    }

    public static String maskParser(String mask) {
        return mask.split(" ")[2];
    }

    public static Tuple2<Integer, String> maskAssignment(String mask) {
        String[] string = mask.split(" ");
        String address = string[0].replace("mem[", "").replace("]", "");
        Integer numericAddress = Integer.valueOf(address);
        String value = string[2];
        return Tuple.tuple(
                numericAddress, value
        );
    }

    private static Tuple2<String, BigInteger> maskAssignmentString(String cmd) {
        String[] string = cmd.split(" ");
        String address = string[0].replace("mem[", "").replace("]", "");
        return Tuple.tuple(
                address, new BigInteger(string[2])
        );
    }
}
