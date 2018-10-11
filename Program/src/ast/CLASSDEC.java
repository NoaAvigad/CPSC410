package ast;

import libs.TokenizedLine;
import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class CLASSDEC extends FILEDEC {

    public CLASSDEC(TokenizedLine tokens) {
        super(tokens);
    }

    @Override
    public void parse() {
        super.parse();
    }

    @Override
    public void validate() {

    }

    @Override
    public void evaluate() {
        System.out.println("In CLASSDEC");

        super.evaluate();

        try(FileWriter fw = new FileWriter(fullPath + ".java", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println("public class " + name + " {");
            out.println("}");

        } catch (IOException e) {
            System.out.println("error writing to class file");
        }

    }
}
