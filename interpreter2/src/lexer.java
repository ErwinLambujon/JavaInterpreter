import java.util.ArrayList;
import java.util.List;

public class lexer {
    private final String input;
    private int currentPos = 0;

    public lexer(String input) {
        this.input = input;
    }

    public List<Token> lex() {
        List<Token> tokens = new ArrayList<>();
        while (currentPos < input.length()) {
            int tokenStartPos = currentPos;
            char lookahead = input.charAt(currentPos);
            if (Character.isWhitespace(lookahead)) {
                currentPos++; // ignore whitespace
            } else if (lookahead == '+') {
                currentPos++;
                tokens.add(new Token(Token.Type.Plus, Character.toString(lookahead), tokenStartPos));
            } else if (Character.isDigit(lookahead)) {
                StringBuilder text = new StringBuilder();
                while (currentPos < input.length() && Character.isDigit(input.charAt(currentPos))) {
                    text.append(input.charAt(currentPos));
                    currentPos++;
                }
                tokens.add(new Token(Token.Type.Num, text.toString(), tokenStartPos));
            } else {
                error("Unknown character '" + lookahead + "' at position " + currentPos);
            }
        }
        tokens.add(new Token(Token.Type.EOF, "<EOF>", currentPos)); // special end marker
        return tokens;
    }

    private void error(String msg) {
        throw new LexException(msg);
    }
}

class Token {
    private final Type type;
    private final String text;
    private final int tokenStartPos;

    public Token(Type type, String text, int tokenStartPos) {
        this.type = type;
        this.text = text;
        this.tokenStartPos = tokenStartPos;
    }

    public Type getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public int getTokenStartPos() {
        return tokenStartPos;
    }

    public enum Type {
        Num, Plus, EOF
    }
}

class LexException extends RuntimeException {
    public LexException(String msg) {
        super(msg);
    }
}
