package ui;

/**
 * This class contains constants and functions relating to ANSI Escape Sequences that are useful in the Client display
 */
public class EscapeSequences {

    private static final String UNICODE_ESCAPE = "\u001b";
    private static final String ANSI_ESCAPE = "\033";

    public static final String ERASE_SCREEN = UNICODE_ESCAPE + "[H" + UNICODE_ESCAPE + "[2J";
    public static final String ERASE_LINE = UNICODE_ESCAPE + "[2K";

    public static final String SET_TEXT_BOLD = UNICODE_ESCAPE + "[1m";
    public static final String SET_TEXT_FAINT = UNICODE_ESCAPE + "[2m";
    public static final String RESET_TEXT_BOLD_FAINT = UNICODE_ESCAPE + "[22m";
    public static final String SET_TEXT_ITALIC = UNICODE_ESCAPE + "[3m";
    public static final String RESET_TEXT_ITALIC = UNICODE_ESCAPE + "[23m";
    public static final String SET_TEXT_UNDERLINE = UNICODE_ESCAPE + "[4m";
    public static final String RESET_TEXT_UNDERLINE = UNICODE_ESCAPE + "[24m";
    public static final String SET_TEXT_BLINKING = UNICODE_ESCAPE + "[5m";
    public static final String RESET_TEXT_BLINKING = UNICODE_ESCAPE + "[25m";

    private static final String SET_TEXT_COLOR = UNICODE_ESCAPE + "[38;5;";
    private static final String SET_BG_COLOR = UNICODE_ESCAPE + "[48;5;";

    public static final String SET_TEXT_COLOR_BLACK = SET_TEXT_COLOR + "0m";
    public static final String SET_TEXT_COLOR_LIGHT_GREY = SET_TEXT_COLOR + "242m";
    public static final String SET_TEXT_COLOR_DARK_GREY = SET_TEXT_COLOR + "235m";
    public static final String SET_TEXT_COLOR_RED = SET_TEXT_COLOR + "160m";
    public static final String SET_TEXT_COLOR_GREEN = SET_TEXT_COLOR + "46m";
    public static final String SET_TEXT_COLOR_YELLOW = SET_TEXT_COLOR + "226m";
    public static final String SET_TEXT_COLOR_BLUE = SET_TEXT_COLOR + "12m";
    public static final String SET_TEXT_COLOR_MAGENTA = SET_TEXT_COLOR + "5m";
    public static final String SET_TEXT_COLOR_WHITE = SET_TEXT_COLOR + "15m";
    public static final String SET_TEXT_COLOR_ORANGE = SET_TEXT_COLOR + "214m";
    public static final String SET_TEXT_COLOR_PINK = SET_TEXT_COLOR + "205m";
    public static final String RESET_TEXT_COLOR = UNICODE_ESCAPE + "[39m";

    public static final String SET_BG_COLOR_BLACK = SET_BG_COLOR + "0m";
    public static final String SET_BG_COLOR_LIGHT_GREY = SET_BG_COLOR + "242m";
    public static final String SET_BG_COLOR_DARK_GREY = SET_BG_COLOR + "235m";
    public static final String SET_BG_COLOR_RED = SET_BG_COLOR + "160m";
    public static final String SET_BG_COLOR_GREEN = SET_BG_COLOR + "46m";
    public static final String SET_BG_COLOR_DARK_GREEN = SET_BG_COLOR + "22m";
    public static final String SET_BG_COLOR_YELLOW = SET_BG_COLOR + "226m";
    public static final String SET_BG_COLOR_BLUE = SET_BG_COLOR + "12m";
    public static final String SET_BG_COLOR_MAGENTA = SET_BG_COLOR + "5m";
    public static final String SET_BG_COLOR_WHITE = SET_BG_COLOR + "15m";
    public static final String SET_BG_COLOR_PINK = SET_BG_COLOR + "205m";
    public static final String SET_BG_COLOR_ORANGE = SET_BG_COLOR + "214m";
    public static final String SET_BG_COLOR_LIGHT_LAVENDER = SET_BG_COLOR + "141m";
    public static final String SET_BG_COLOR_MUSTARD = SET_BG_COLOR + "178m"; // mustard yellow (very solid choice)
    public static final String SET_BG_COLOR_MUSTARD2 = SET_BG_COLOR + "179m";
    public static final String SET_BG_COLOR_PURPLE_1 = SET_BG_COLOR + "93m";   // soft violet
    public static final String SET_BG_COLOR_PURPLE_2 = SET_BG_COLOR + "99m";   // balanced purple
    public static final String SET_BG_COLOR_PURPLE_3 = SET_BG_COLOR + "135m";  // muted lavender
    public static final String SET_BG_COLOR_PURPLE_4 = SET_BG_COLOR + "141m";
    public static final String SET_BG_COLOR_LIGHT_BLUE_1 = SET_BG_COLOR + "117m"; // sky blue
    public static final String SET_BG_COLOR_LIGHT_BLUE_2 = SET_BG_COLOR + "123m"; // bright cyan-blue
    public static final String SET_BG_COLOR_LIGHT_BLUE_3 = SET_BG_COLOR + "153m"; // pale blue
    public static final String SET_BG_COLOR_LIGHT_BLUE_4 = SET_BG_COLOR + "159m"; // very soft blue
    public static final String SET_BG_COLOR_DARK_BLUE_1 = SET_BG_COLOR + "18m";  // deep navy
    public static final String SET_BG_COLOR_DARK_BLUE_2 = SET_BG_COLOR + "19m";  // slightly brighter navy
    public static final String SET_BG_COLOR_DARK_BLUE_3 = SET_BG_COLOR + "25m";  // strong blue
    public static final String SET_BG_COLOR_DARK_BLUE_4 = SET_BG_COLOR + "31m";  // rich blue
    public static final String RESET_BG_COLOR = UNICODE_ESCAPE + "[49m";

    public static final String WHITE_KING = " ♔ ";
    public static final String WHITE_QUEEN = " ♕ ";
    public static final String WHITE_BISHOP = " ♗ ";
    public static final String WHITE_KNIGHT = " ♘ ";
    public static final String WHITE_ROOK = " ♖ ";
    public static final String WHITE_PAWN = " ♙ ";
    public static final String BLACK_KING = " ♚ ";
    public static final String BLACK_QUEEN = " ♛ ";
    public static final String BLACK_BISHOP = " ♝ ";
    public static final String BLACK_KNIGHT = " ♞ ";
    public static final String BLACK_ROOK = " ♜ ";
    public static final String BLACK_PAWN = " ♟ ";
    public static final String EMPTY = " \u2003 ";

    public static String moveCursorToLocation(int x, int y) { return UNICODE_ESCAPE + "[" + y + ";" + x + "H"; }
}
