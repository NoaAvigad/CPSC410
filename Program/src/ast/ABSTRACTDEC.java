package ast;

import libs.TokenizedLine;
import ui.Main;

import java.io.*;

public class ABSTRACTDEC extends FILEDEC {

    public ABSTRACTDEC(TokenizedLine tokens) {
        super(tokens);
    }
    @Override
    public void parse() {
        super.parse();
    }

    @Override
    public void validate() {
        super.validate();
    }

    @Override
    public void evaluate() {

        super.evaluate();

        try(FileWriter fw = new FileWriter(fullPath+ ".java", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println("public abstract class " + name + " {");
            out.println("}");

        } catch (IOException e) {
            System.out.println("error writing to abstract class file");
        }

    }
}
