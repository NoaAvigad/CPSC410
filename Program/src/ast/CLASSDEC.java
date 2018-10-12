package ast;

import libs.TokenizedLine;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
        super.evaluate();

        try(FileWriter fw = new FileWriter(fullPath + ".java", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {

            // TODO: figure out what to do in case of duplicated names for super class. Need to know where to import from.
            String inheritanceSignature = this._extends != null ? " extends " + this._extends.substring(this._extends.lastIndexOf("/") + 1) : "";
            out.println("public class " + this.name + inheritanceSignature);
            out.println();
            //list for all members that need getter/setter
            List<MEMBER> getterOrSetterMems = new ArrayList<>();

            for(MEMBER mem : this.members) {
                StringBuilder sb = new StringBuilder();
                if(!mem.isInSuper) {
                    sb.append("\t");
                    if (mem.isProtect) {
                        sb.append("protected");
                    } else {
                        sb.append("private");
                    }
                    sb.append(" ").append(mem.type).append(" ").append(mem.name).append(";");
                    out.println(sb.toString());
                    if(mem.get || mem.set) {
                        getterOrSetterMems.add(mem);
                    }
                }
            }


            for(MEMBER mem : getterOrSetterMems) {
                //TODO: should be similar loop to the first one. Just need to generate getter/setter for member.


            }

            out.println("}");

        } catch (IOException e) {
            System.out.println("error writing to " + this.name + ".java");
        }

    }
}
