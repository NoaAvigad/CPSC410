package libs;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public abstract class Node {

    abstract public void parse();
    abstract public void validate();
    abstract public void evaluate();

}
