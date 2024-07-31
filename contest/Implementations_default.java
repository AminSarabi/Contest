package contest;

/*
* Create a new class replacing "default" with your name. send that class to me.
* Do not create any other file, all your code needs to be inside a single file.
* */
class Implementations_default implements Implementations {

    @Override
    public NumberParser getNumberParser() {
        return charProvider -> 0;
    }

    @Override
    public Escaper getEscaper() {
        return null;
    }
}
