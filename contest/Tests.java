package contest;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@SuppressWarnings("SameParameterValue")
public class Tests {

    public static void main(String[] args) {
        //replace the following with your own implementation so that you can test it.
        testImplementation(new Implementations_default());
    }

    static void testImplementation(Implementations implementations) {
        //the number bound can be used to limit the test to a certain numeric types only.
        testNumericParser(implementations.getNumberParser(), 7);

        testEscaper(implementations.getEscaper());
    }

    static void testNumericParser(NumberParser numberParser, int bound) {
        Random random = new Random(System.currentTimeMillis());
        long before = System.nanoTime();
        for (int i = 0; i != 100000; i++) {
            String numericText;
            Number expectedConversion;
            switch (random.nextInt(bound)) {
                case 0:
                    expectedConversion = random.nextInt();
                    break;
                case 1:
                    expectedConversion = random.nextLong();
                    break;
                case 2:
                    expectedConversion = random.nextFloat();
                    break;
                case 3:
                    expectedConversion = random.nextDouble();
                    break;
                case 4:
                    expectedConversion = new BigInteger(random.nextLong() + "" + random.nextLong());
                    break;
                case 5:
                    expectedConversion = new BigDecimal(random.nextLong() + "." + random.nextLong());
                    break;
                case 6:
                    expectedConversion = new Number[]{0, -1, 1, Double.MAX_VALUE, Integer.MAX_VALUE, Long.MAX_VALUE}[random.nextInt(5)];
                    break;
                default:
                    continue;
            }
            numericText = expectedConversion.toString();

            Number conversion = numberParser.parse(new StringCharProvider(numericText));
            if (!expectedConversion.equals(conversion)) {
                throw new RuntimeException(expectedConversion.getClass().getSimpleName() + " " +
                        numericText + " was incorrectly parsed as " + conversion.getClass().getSimpleName()
                        + " " + conversion);
            }
        }
        double took = (System.nanoTime() - before) / 1000000d;
        System.out.println("successfully passed all the conversions, took " + took + "ms (including random generations and equality checks)");
    }


    static void testEscaper(Escaper escaper) {
        Set<Character> charsToEscape = new HashSet<>(Arrays.asList('\n', '\r', '\t', '\b', '\f', '\\'));
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i != 1000; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            String array = "{}\"\\(),.:#@!/-=[]abcdefghijklmnopqrstuvwxyz1234567890\n\r\t\b\f";
            for (int x = 0; x != 32; x++) {
                char ch = array.charAt(random.nextInt(array.length()));
                stringBuilder.append(random.nextBoolean() ? Character.toLowerCase(ch) : Character.toUpperCase(ch));
                if (random.nextBoolean()) stringBuilder.append("  ");
                if (random.nextBoolean()) {
                    byte[] bytes = new byte[8];
                    random.nextBytes(bytes);
                    stringBuilder.append(new String(bytes, StandardCharsets.UTF_8));
                }
            }
            String randomString = stringBuilder.toString();
            String escaped = new String(escaper.escape(new StringCharProvider(randomString), charsToEscape, '\\'));
            String unescaped = new String(escaper.unescape(new StringCharProvider(escaped), '\\'));
            if (!unescaped.equals(randomString)) {
                throw new RuntimeException(randomString + " was escaped and unescaped, an became " + unescaped);
            }
            if (escaped.contains("\n") || escaped.contains("\r") || escaped.contains("\t") || escaped.contains("\b")) {
                throw new RuntimeException("escaper has not handled the control chars properly.");
            }
        }
    }
}

class StringCharProvider implements CharProvider {
    int index;
    String string;

    public StringCharProvider(String string) {
        this.string = string;
    }

    @Override
    public char getNext() {
        return string.charAt(index++);
    }

    @Override
    public boolean hasNext() {
        return (index < string.length());
    }
}

