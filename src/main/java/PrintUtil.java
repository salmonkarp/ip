public final class PrintUtil {
    public static String addEnclosingLines(String input) {
        return """
                ____________________________________________________________
                """ + input + "\n" + """
                ____________________________________________________________
                """;
    }

    public static void printWithLines(String input) {
        System.out.println(addEnclosingLines(input));
    }
}