import java.util.List;

public class parser {
    private List<Token> tokens;
    private Token lookahead;

    public parser(List<Token> allTokens) {
        this.tokens = allTokens;
        this.lookahead = tokens.get(0);
    }

    public void parse() {
        expr();
        if (lookahead.getType() != Token.Type.EOF) {
            error("Unknown token '" + lookahead.getText() + "' at position " + lookahead.getTokenStartPos());
        }
    }

    private void expr() {
        eat(Token.Type.Num);
        exprOpt();
    }

    private void exprOpt() {
        if (lookahead.getType() == Token.Type.Plus) {
            eat(Token.Type.Plus);
            eat(Token.Type.Num);
            exprOpt();
        }
        // else: end recursion, epsilon
    }

    private void eat(Token.Type type) {
        if (lookahead.getType() != type) {
            error("Expected: " + type + ", got: " + lookahead.getType() + " at position " + lookahead.getTokenStartPos());
        }
        tokens = tokens.subList(1, tokens.size());
        lookahead = tokens.get(0);
    }

    private void error(String msg) {
        throw new ParseException(msg);
    }
}

class ParseException extends RuntimeException {
    public ParseException(String msg) {
        super(msg);
    }
}
