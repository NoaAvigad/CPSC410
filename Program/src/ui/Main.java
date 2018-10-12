package ui;

import ast.DEC;
import ast.FOLDERDEC;
import libs.DecFactory;
import libs.ParseManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static Map<String, DEC> symbolTable = new HashMap<>();
    public static ParseManager parseManager;
    private static String ROOT_DIR = "./output";

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
        String input = "";

        try {
            input = new String(Files.readAllBytes(Paths.get("input.perc")), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Didn't find file :" + e);
            System.exit(0);
        }
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(input.split("\n")));
        Main.parseManager = new ParseManager(lines, ROOT_DIR);
        while(!Main.parseManager.eof()) {
            DEC rootDec = DecFactory.getDec(parseManager.yieldTokenizedLine());
            rootDec.parse();
        }

        System.out.println("STARTING VALIDATE");
        for(DEC dec : symbolTable.values()) {
            dec.validate();
        }
        System.out.println("ENDING VALIDATE");


        System.out.println("STARTING EVALUATE");
        DEC.deleteFolderContents();
        for(DEC dec : symbolTable.values()) {
            dec.evaluate();
        }
        System.out.println("ENDING EVALUATE");


        System.out.println("Done");
    }

}
