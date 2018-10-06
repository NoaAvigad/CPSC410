package libs;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public abstract class Node {
    protected static Tokenizer tokenizer = Tokenizer.getTokenizer();

    abstract public void parse();


}
