package utilities;

import java.math.BigInteger;

import static java.util.Arrays.stream;

/**
 * Reference coderosetta
 * http://www.rosettacode.org/wiki/Chinese_remainder_theorem
 */
public class ChineseRemainderTheorem {

    /**
     * Resolve an equation system in the format of chinese remainder theorem
     * <pre>
     * x = n1 * (mod a1)
     * x = n2 * (mod a2)
     * ...
     * </pre>
     *
     * @param n The array of modulus coefficients
     * @param a The array of equation coefficients
     * @return The solution of the system
     */
    public static BigInteger chineseRemainder(BigInteger[] n, BigInteger[] a) {
        BigInteger prod = stream(n).reduce(BigInteger.ONE, BigInteger::multiply);

        BigInteger p, sm = BigInteger.ZERO;
        for (int i = 0; i < n.length; i++) {
            p = prod.divide(n[i]);
            sm = sm.add(
                    a[i].multiply(mulInv(p, n[i])).multiply(p));
        }
        return sm.mod(prod);
    }

    private static BigInteger mulInv(BigInteger a, BigInteger b) {
        BigInteger b0 = b;
        BigInteger x0 = BigInteger.ZERO;
        BigInteger x1 = BigInteger.ONE;

        if (b.compareTo(BigInteger.ONE) == 0)
            return BigInteger.ONE;

        while (a.compareTo(BigInteger.ONE) > 0) {
            BigInteger q = a.divide(b);
            BigInteger amb = a.mod(b);
            a = b;
            b = amb;
            BigInteger xqx = x1.subtract(q.multiply(x0));
            x1 = x0;
            x0 = xqx;
        }

        if (x1.compareTo(BigInteger.ZERO) < 0)
            x1 = x1.add(b0);

        return x1;
    }

}
