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
            String inheritanceSignature = this._extends != null ? " extends " + this._extends.substring(this._extends.lastIndexOf("/") + 1) : "";
            out.println("public class " + this.name + inheritanceSignature + " {");
            out.println();
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
                StringBuilder sb = new StringBuilder();
                if(mem.get) {
                    if(mem.pHasGet) {
                        //override
                        out.println(buildOverrideGetSetSb(mem, "get").toString());
                    } else {
                        //normal getter
                        out.println(buildGetSetSb(mem, "get").toString());
                    }

                }

                if (mem.set) {
                    if(mem.pHasSet) {
                        //override
                        out.println(buildOverrideGetSetSb(mem,"set").toString());
                    } else {
                        //normal setter
                        out.println(buildGetSetSb(mem, "set"));
                    }
                }
            }

            out.println("}");

        } catch (IOException e) {
            System.out.println("Error writing to " + this.name + ".java");
        }
    }

    StringBuilder buildOverrideGetSetSb(MEMBER mem, String getOrSet) {
        return new StringBuilder("\n\t")
                .append("@Override").append("\n\t")
                .append("public ").append(mem.type).append(" ")
                .append(getOrSet).append(mem.name.substring(0,1).toUpperCase()).append(mem.name.substring(1))
                .append("() {").append("\n\t\t")
                .append("return this.").append(mem.name).append(";")
                .append("\n\t}");
    }

    StringBuilder buildGetSetSb (MEMBER mem, String getOrSet) {
        return new StringBuilder("\n\t")
                .append("public ").append(mem.type).append(" ")
                .append(getOrSet).append(mem.name.substring(0,1).toUpperCase()).append(mem.name.substring(1))
                .append("() {").append("\n\t\t")
                .append("return this.").append(mem.name).append(";")
                .append("\n\t}");
    }
}
