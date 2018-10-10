package libs;

import java.util.ArrayList;
import java.util.Stack;

public class ParseManager {
    private Stack<String> pathStack = new Stack<>();
    private Stack<String> lineStack = new Stack<>();
    private int currentIndent = 0;
    private int spacesPerIndent;

    public ParseManager(ArrayList<String> lines, String root) {
        this.pathStack.push(root);
        this.lineStack.addAll(lines);
        for(String line : lines) {
            int spaces = this.countSpaces(line);
            if(spaces > 0) {
                if(spaces % 2 != 0) {
                    System.out.println("Why are you using an odd number of spaces to indent. Absolutely barbaric.");
                    System.exit(1);
                }
                this.spacesPerIndent = spaces;
                return;
            }
        }
        this.spacesPerIndent = 4; // Arbitrary default that won't be used
    }

    public void enterDirectory(String dirname) {
        this.pathStack.push(dirname);
        this.currentIndent++;
    }

    public void leaveDirectory() {
        this.pathStack.pop();
        this.currentIndent--;
    }

    public boolean eof() {
        return this.lineStack.empty();
    }

    public boolean nextIsSameDirectory() {
        return this.isSameDirectory(this.lineStack.peek());
    }

    private boolean isSameDirectory(String line) {
        return this.indentLevel(line) == this.currentIndent;
    }

    public TokenizedLine yieldTokenizedLine() {
        return new TokenizedLine(this.lineStack.pop());
    }

    public String getFilepath() {
        String partial = this.pathStack.toString().replace(", ", "/");
        return partial.substring(1, partial.length() - 1);
    }

    private int countSpaces(String line) {
        return line.indexOf(line.trim()); // StackOverflow says this works and it does
    }

    private int indentLevel(String line) {
        return this.countSpaces(line) / this.spacesPerIndent;
    }


}
