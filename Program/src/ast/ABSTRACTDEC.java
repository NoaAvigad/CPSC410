package ast;

import libs.TokenizedLine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
        System.out.println("Evaluating file: " + fullPath + ".java");

        try(FileWriter fw = new FileWriter(fullPath + ".java", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {

            // if same dir, just take classname. if different then need to import and use class name in extends
            String inheritanceSignature = this.buildInheritanceSignature(out);

            // check if need to import list

            if(this.doesHaveList) {
                out.println("import java.util.ArrayList;");
                out.println("import java.util.LinkedList;");
                out.println("\n");
            }

            out.println("public abstract class " + this.name + inheritanceSignature + " {");
            //list for all members that need getter/setter
            List<MEMBER> getterOrSetterMems = new ArrayList<>();
            this.buildMembers(this.members, out, getterOrSetterMems);
            this.buildGettersAndSetters(out, getterOrSetterMems);
            out.println("}");

        } catch (IOException e) {
            this.kill("Error writing to " + this.name + ".java");
        }
    }



}
