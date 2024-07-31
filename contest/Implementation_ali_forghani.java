package contest;

public class Implementation_ali_forghani implements Implementations {
    @Override
    public NumberParser getNumberParser() {
        return new NumberParser_Ali_Forghani();
    }

    @Override
    public Escaper getEscaper() {
        return null;
    }
}
