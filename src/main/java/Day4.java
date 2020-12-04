import java.util.Arrays;
import java.util.regex.Pattern;

public class Day4 {

    public static long es1(String testInput) {
        String[] split = testInput.split("\r\r");
        return Arrays.stream(split)
                .map(Passport::new)
                .filter(Passport::fieldsPresenceValidation)
                .count();
    }

    public static long es2(String testInput) {
        String[] split = testInput.split("\r\r");
        return Arrays.stream(split)
                .map(Passport::new)
                .filter(Passport::fieldsValidation)
                .count();
    }

    private static class Passport {
        private String byr = "";
        private String iyr = "";
        private String eyr = "";
        private String hgt = "";
        private String hcl = "";
        private String ecl = "";
        private String pid = "";
        private String cid = "";

        public Passport(String init) {
            for (String line : init.split("\r")) {
                for (String field : line.split(" ")) {
                    String[] value = field.split(":");
                    switch (value[0]) {
                        case "byr":
                            byr = value[1];
                            break;
                        case "iyr":
                            iyr = value[1];
                            break;
                        case "eyr":
                            eyr = value[1];
                            break;
                        case "hgt":
                            hgt = value[1];
                            break;
                        case "hcl":
                            hcl = value[1];
                            break;
                        case "ecl":
                            ecl = value[1];
                            break;
                        case "pid":
                            pid = value[1];
                            break;
                        case "cid":
                            cid = value[1];
                            break;
                    }
                }
            }
        }

        public boolean fieldsPresenceValidation() {
            return !byr.equals("") &&
                    !iyr.equals("") &&
                    !eyr.equals("") &&
                    !hgt.equals("") &&
                    !hcl.equals("") &&
                    !ecl.equals("") &&
                    !pid.equals("");

        }

        public boolean fieldsValidation() {
            return fieldsPresenceValidation() &&
                    rangeValidation(byr, 1920, 2002) &&
                    rangeValidation(iyr, 2010, 2020) &&
                    rangeValidation(eyr, 2020, 2030) &&
                    heightValidation(hgt) &&
                    hairValidation(hcl) &&
                    eyeValidation(ecl) &&
                    pidValidation(pid);

        }

        private boolean rangeValidation(String str, int min, int max) {
            try {
                int value = Integer.parseInt(str);
                return value >= min && value <= max;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        private boolean heightValidation(String str) {
            if (str.contains("cm")) {
                String s = str.replace("c", "");
                s = s.replace("m", "");
                return rangeValidation(s, 150, 193);
            }
            if (str.contains("in")) {
                String s = str.replace("i", "");
                s = s.replace("n", "");
                return rangeValidation(s, 59, 76);
            }
            return false;

        }

        private boolean hairValidation(String str) {
            return Pattern.matches("#(\\d|[a-f]){6}", str);
        }

        private boolean eyeValidation(String str) {
            return Pattern.matches("amb|blu|brn|gry|grn|hzl|oth", str);
        }

        private boolean pidValidation(String str) {
            return Pattern.matches("\\d{9}", str);
        }
    }
}
