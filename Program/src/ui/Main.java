package ui;

import libs.Tokenizer;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static Map<String,Object> symbolTable = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        /*
        GENERAL IDEA: Run parse through everything we can, make MODEL objects filling out the partial data (fields starting w/ _)
        Then call validate on the model, which will loop through and make sure inheritances etc. make sense, and push
        actual FILE objects to the extendList/inheritList member variables as appropriate. This would fail if a class was trying
        to inherit from two classes for example.
        Then call evaluate, and build the output for each file. Need to figure out what member parent classes have that might
        need to be instantiated etc.
        Might need a new Program class or we can just make the calls directly in here.
         */
    }

}
