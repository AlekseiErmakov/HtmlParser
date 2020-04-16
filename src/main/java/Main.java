public class Main {
    public static void main(String[] args) {
        Thread parserApp = new ParserService();
        parserApp.start();
    }
}
