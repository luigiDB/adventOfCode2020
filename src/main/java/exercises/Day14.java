package exercises;

import com.google.common.base.Strings;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14 {
    public static BigInteger es1(Stream<String> input) {
        List<String> list = input.collect(Collectors.toList());
        Map<BigInteger, BigInteger> memory = new HashMap<>();

        String currentMask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
        for (String cmd : list) {
            if (isMask(cmd)) {
                currentMask = maskParser(cmd);
            } else {
                Tuple2<String, String> assignment = assignmentBinaryParser(cmd);
                StringBuilder maskedNumber = new StringBuilder(assignment.v2);
                for (int i = 0; i < currentMask.length(); i++) {
                    if (currentMask.charAt(i) == '0') {
                        maskedNumber.setCharAt(i, '0');
                    }
                    if (currentMask.charAt(i) == '1') {
                        maskedNumber.setCharAt(i, '1');
                    }
                }
                memory.put(new BigInteger(assignment.v1, 2), new BigInteger(maskedNumber.toString(), 2));
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
                Tuple2<String, String> assignment = assignmentBinaryParser(cmd);
                StringBuilder maskedAddress = new StringBuilder(assignment.v1);
                for (int i = 0; i < currentMask.length(); i++) {
                    if (currentMask.charAt(i) == '1') {
                        maskedAddress.setCharAt(i, '1');
                    }
                    if (currentMask.charAt(i) == 'X') {
                        maskedAddress.setCharAt(i, 'X');
                    }
                }
                fillMemory(memory, maskedAddress.toString(), assignment.v2);
            }
        }

        return memory.values().stream().reduce(BigInteger::add).orElse(BigInteger.ZERO);
    }

    private static void fillMemory(Map<BigInteger, BigInteger> memory, String addressFamily, String value) {
        Set<String> addresses = new HashSet<>();
        addresses.add(addressFamily);

        while (addresses.iterator().next().contains("X")) {
            Set<String> expandAddresses = new HashSet<>();
            int index = addresses.iterator().next().indexOf("X");
            addresses.forEach(str -> {
                StringBuilder tmp = new StringBuilder(str);
                tmp.setCharAt(index, '1');
                expandAddresses.add(tmp.toString());
                tmp.setCharAt(index, '0');
                expandAddresses.add(tmp.toString());
            });
            addresses = expandAddresses;
        }

        addresses.forEach(val -> memory.put(new BigInteger(val, 2), new BigInteger(value, 2)));
    }


    private static boolean isMask(String in) {
        return in.contains("mask");
    }

    public static String maskParser(String mask) {
        return mask.split(" ")[2];
    }

    public static Tuple2<String, String> assignmentBinaryParser(String cmd) {
        String[] string = cmd.split(" ");
        String address = string[0].replace("mem[", "").replace("]", "");
        String value = string[2];
        return Tuple.tuple(
                Strings.padStart(Integer.toBinaryString(Integer.parseInt(address)), 36, '0'),
                Strings.padStart(Integer.toBinaryString(Integer.parseInt(value)), 36, '0')
        );
    }
}
