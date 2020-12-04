import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class Day4Test {
    private String testInput = "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\r" +
            "byr:1937 iyr:2017 cid:147 hgt:183cm\r" +
            "\r" +
            "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884\r" +
            "hcl:#cfa07d byr:1929\r" +
            "\r" +
            "hcl:#ae17e1 iyr:2013\r" +
            "eyr:2024\r" +
            "ecl:brn pid:760753108 byr:1931\r" +
            "hgt:179cm\r" +
            "\r" +
            "hcl:#cfa07d eyr:2025 pid:166559648\r" +
            "iyr:2011 ecl:brn hgt:59in";

    @Test
    public void es1Test() {

        assertEquals(2, Day4.es1(testInput));
    }

    @Test
    public void es1() {
        String input = AOCTestFramework.parseString("4");
        System.out.println(Day4.es1(input));
    }

    @Test
    public void es2TestInvalid() {
        String passports = "eyr:1972 cid:100\r" +
                "hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926\r" +
                "\r" +
                "iyr:2019\r" +
                "hcl:#602927 eyr:1967 hgt:170cm\r" +
                "ecl:grn pid:012533040 byr:1946\r" +
                "\r" +
                "hcl:dab227 iyr:2012\r" +
                "ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277\r" +
                "\r" +
                "hgt:59cm ecl:zzz\r" +
                "eyr:2038 hcl:74454a iyr:2023\r" +
                "pid:3556412378 byr:2007";
        assertEquals(0, Day4.es2(passports));
    }

    @Test
    public void es2TestValid() {
        String passports = "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980\r" +
                "hcl:#623a2f\r" +
                "\r" +
                "eyr:2029 ecl:blu cid:129 byr:1989\r" +
                "iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm\r" +
                "\r" +
                "hcl:#888785\r" +
                "hgt:164cm byr:2001 iyr:2015 cid:88\r" +
                "pid:545766238 ecl:hzl\r" +
                "eyr:2022\r" +
                "\r" +
                "iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719";
        assertEquals(4, Day4.es2(passports));
    }

    @Test
    public void es2() {
        String input = AOCTestFramework.parseString("4");
        System.out.println(Day4.es2(input));
    }
}