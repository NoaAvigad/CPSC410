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
        super.validate();
    }

    @Override
    public void evaluate() {
        System.out.println("In CLASSDEC");

        super.evaluate();

        try(FileWriter fw = new FileWriter(fullPath + ".java", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println("public class " + this.name + " {");
            out.println();
            out.println();
            for(MEMBER mem : this.members) {
                System.out.println("The member: " + mem.name + "is protected: " + mem.isProtect);
                StringBuilder sb = new StringBuilder();
                if(mem.isProtect) {
                    sb.append("protected");
                } else {
                    sb.append("private");
                }

                sb.append(" ").append(mem.type).append(" ").append(mem.name).append(";");
                out.println(sb.toString());
            }
            out.println("}");

        } catch (IOException e) {
            System.out.println("error writing to " + this.name + ".java");
        }

    }
}
