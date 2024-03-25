public class Main {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        int input = 1 * 3 + (4 / 3);
        String in = Integer.toString(input);
        System.out.println("-".repeat(30));
        System.out.println("Parsing input:");
        System.out.println(input);
        System.out.println();

        lexer lexer = new lexer(in);
        parser parser = new parser(lexer.lex());
        parser.parse();

        System.out.println("Success!");
    }
}
