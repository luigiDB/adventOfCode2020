package exercises;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Day2 {
    public static long es1(Stream<String> input) {
        return input
                .map(PwdEntry::new)
                .filter(PwdEntry::isValidForFirstRuleSet)
                .count();
    }

    public static long es2(Stream<String> input) {
        return input
                .map(PwdEntry::new)
                .filter(PwdEntry::isValidForSecondRuleSet)
                .count();
    }

    private static class PwdEntry {

        private final int min;
        private final int max;
        private final char elem;
        private final String pwd;

        public PwdEntry(String str) {
            String[] s = str.split(" ");
            String[] frequencies = s[0].split("-");
            min = Integer.parseInt(frequencies[0]);
            max = Integer.parseInt(frequencies[1]);
            elem = s[1].charAt(0);
            pwd = s[2];
        }


        public boolean isValidForFirstRuleSet() {
            Map<Character, Integer> counter = new HashMap<>();
            for (int i = 0; i < pwd.length(); i++) {
                char c = pwd.charAt(i);
                Integer currentValue = counter.getOrDefault(c, 0);
                counter.put(c, currentValue + 1);
            }
            if (counter.containsKey(elem))
                return counter.get(elem) >= min && counter.get(elem) <= max;
            else
                return false;
        }

        public boolean isValidForSecondRuleSet() {
            boolean minValid = checkChar(min, elem);
            boolean maxValid = checkChar(max, elem);
            return minValid ^ maxValid;
        }

        private boolean checkChar(int pos, char reference) {
            if (pwd.length() < pos) {
                return false;
            }
            return pwd.charAt(pos - 1) == reference;
        }
    }
}
