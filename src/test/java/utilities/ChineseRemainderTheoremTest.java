package utilities;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class ChineseRemainderTheoremTest {
    @Test
    public void chineseRemainder() {
        BigInteger[] n = {BigInteger.valueOf(3), BigInteger.valueOf(5), BigInteger.valueOf(7)};
        BigInteger[] a = {BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(2)};
        assertEquals(BigInteger.valueOf(23), ChineseRemainderTheorem.chineseRemainder(n, a));
    }
}