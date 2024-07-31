package contest;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumberParser_Ali_Forghani implements NumberParser {
    @Override
    public Number parse(CharProvider charProvider) {
        BigDecimal bigDecimal = new BigDecimal(BigInteger.ZERO);
        int floatPointCount = 0;
        boolean isNegative = false;
        boolean isOnFloatPart = false;
        while (charProvider.hasNext()) {
            char next = charProvider.getNext();
            switch (next) {
                case '-':
                    isNegative = true;
                    break;
                case '.':
                    isOnFloatPart = true;
                    break;
                default:
                    if (Character.isDigit(next))
                        if (isOnFloatPart) {
                            floatPointCount++;
                        }
                    bigDecimal = bigDecimal.multiply(BigDecimal.TEN).add(BigDecimal.valueOf(((int) next - '0')));
            }
        }

        if (isNegative)
            bigDecimal = bigDecimal.negate();

        bigDecimal = bigDecimal.movePointLeft(floatPointCount);
        return bigDecimal;
    }
}
