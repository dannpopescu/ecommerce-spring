package com.danpopescu.shop.persistence.validation;

/**
 * Taken from Quarano Project:
 * https://github.com/quarano/quarano-application/blob/develop/backend/src/main/java/quarano/core/validation/Strings.java
 */

public class RegexPatterns {

    static final String LETTERS = "[\\p{L}]*";
    static final String NUMBERS = "[0-9]*";
    static final String LETTERS_AND_NUMBERS = "[\\p{L}\\s\\d]*";
    static final String TEXTUAL = "[\\p{L}\\s\\d\\.\\,\\?\\!\\(\\)\\-\\n\\r]*";

    private static final String LETTERS_AND_SPACES = "\\p{L}\\s";
    private static final String DOTS = "\\.";
    private static final String DASHES = "\\-";
    private static final String UNDERSCORE = "\\_";
    private static final String PARENTHESIS = "\\(\\)";
    private static final String SLASHES = "\\/";

    public static final String CITY = "[" + LETTERS_AND_SPACES + DOTS + DASHES + PARENTHESIS + SLASHES + "]*";
    public static final String STREET = "[" + LETTERS_AND_SPACES + DOTS + DASHES + NUMBERS + "]*";
    public static final String HOUSE_NUMBER = "[" + LETTERS_AND_SPACES + DOTS + DASHES + NUMBERS + SLASHES + "]{0,15}";
    public static final String NAMES = "[" + LETTERS_AND_SPACES + DASHES + "]*";
    public static final String EXT_REFERENCE_NUMBER = "[" + LETTERS + NUMBERS + DASHES + SLASHES + UNDERSCORE + "]*";

    public static final String USERNAME = "[" + LETTERS + NUMBERS + DASHES + UNDERSCORE + "]*";

    public static final String EMAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final String UUID_REGEX = "\\b[0-9a-f]{8}\\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\\b[0-9a-f]{12}\\b";

}
