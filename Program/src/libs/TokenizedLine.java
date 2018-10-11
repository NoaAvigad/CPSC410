package libs;

import java.util.*;

public class TokenizedLine {
    private static String[] punctuation = {"/", ",", "(", ")", "[", "]"};
    private Stack<String> tokens = new Stack<>();

    public TokenizedLine(String source) {
        String current = source.trim().replaceAll("([\r\n\t])", "");
        for (String s : this.punctuation) {
            current = current.replace(s," "+s+" ");
        }
        current.replaceAll("([ ]+)", " ");
        ArrayList<String> asList = new ArrayList<>(Arrays.asList(current.split(" ")));
        asList.removeIf(item -> item == null || "".equals(item));
        Collections.reverse(asList);
        this.tokens.addAll(asList);
    }

    public String pop() {
        try {
            return this.tokens.pop();
        } catch (EmptyStackException e) {
            System.out.println("Tried to pop empty stack");
            System.exit(1);
        }
    }

    public String peek() {
        return this.tokens.peek();
    }

    public boolean eof() { return this.tokens.empty(); }

    public boolean checkNext(String comparator) {
        return !this.eof() && !this.peek().equals(comparator);
    }


}
