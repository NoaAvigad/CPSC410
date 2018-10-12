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
        System.out.println("Evaluating file: " + fullPath + ".java");

        try(FileWriter fw = new FileWriter(fullPath + ".java", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {

            // TODO: figure out what to do in case of duplicated names for super class. Need to know where to import from.
            // if same dir, just take classname. if different then need to import and use class name in extends
            String inheritanceSignature = "";
            if(this._extends != null) {
                String parentClassDir = this._extends.substring(0, this._extends.lastIndexOf("/"));
                if(!dirPath.equals(parentClassDir)) {
                    out.println("import " + parentClassDir);
                }
                inheritanceSignature = " extends " + this._extends.substring(this._extends.lastIndexOf("/") + 1);

            }


            out.println("public class " + this.name + inheritanceSignature + " {");
            //list for all members that need getter/setter
            List<MEMBER> getterOrSetterMems = new ArrayList<>();

            // append to java file all member variables
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
                }

                if(mem.get || mem.set) {
                    getterOrSetterMems.add(mem);
                }
            }

            // append to java file all variables getters/setters
            for(MEMBER mem : getterOrSetterMems) {
                //TODO: should be similar loop to the first one. Just need to generate getter/setter for member.
                if(mem.get) {
                    out.println(buildGetSetSb(mem, "get", mem.pHasGet).toString());
                }

                if (mem.set) {
                    out.println(buildGetSetSb(mem,"set", mem.pHasSet).toString());
                }
            }

            out.println("}");

        } catch (IOException e) {
            this.kill("Error writing to " + this.name + ".java");
        }
    }

    StringBuilder buildGetSetSb(MEMBER mem, String getOrSet, boolean isOverride) {
        StringBuilder sb = new StringBuilder("\n\t");

        if(isOverride) {
            sb.append("@Override").append("\n\t");
        }

        sb.append("public ").append(mem.type).append(" ")
                .append(getOrSet).append(mem.name.substring(0,1).toUpperCase()).append(mem.name.substring(1));

        if(getOrSet.equals("get")) {
            sb.append("() {").append("\n\t\t").append("return this.").append(mem.name).append(";");
        } else {
            sb.append("(" + mem.type + " " + mem.name + ") {").append("\n\t\t").append("this.").append(mem.name).append(" = ").append(mem.name).append(";");
        }

        sb.append("\n\t}");
        return sb;
    }


}
