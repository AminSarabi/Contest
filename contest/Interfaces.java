package contest;


import java.util.Set;

@SuppressWarnings("unused")
interface NumberParser {

    /**
     * get a charProvider and parse the number that it represents. return a number based on the range of the input.
     * Create the least possible amount of objects, do not store the characters in an array.
     * example: "7091"  int    7091
     * "7.091" float 7091
     * double
     * long
     * BigDecimal
     */
    Number parse(CharProvider charProvider);


}

/**
 * Escape a certain set of characters in a text. must handle escaping control characters as well as regular characters.
 * DO NOT USE STRING METHODS. do not create unnecessary objects.
 * you can grow the array of chars if required.
 * */
interface Escaper {

    char[] escape(CharProvider charProvider, Set<Character> charsToEscape, char escapeChar);

    char[] unescape(CharProvider charProvider, char escapeChar);
}


/**
 * a helper interface, this enforces char by char advancement of the methods. no going back, no fast forwards.
 */
@SuppressWarnings("unused")
interface CharProvider {
    /**
     * returns: char if there is any, throws if EOF
     */
    char getNext();

    boolean hasNext();
}


interface Implementations {
    NumberParser getNumberParser();

    Escaper getEscaper();
}