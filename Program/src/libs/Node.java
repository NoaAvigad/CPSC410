package libs;

public abstract class Node {

    abstract public void parse();

    protected void kill(String message) {
        System.out.println(message);
        System.exit(1);
    }
}
