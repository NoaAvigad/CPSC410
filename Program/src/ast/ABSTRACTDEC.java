package ast;

import libs.TokenizedLine;
import ui.Main;

public class ABSTRACTDEC extends FILEDEC {

    public ABSTRACTDEC(TokenizedLine tokens) {
        super(tokens);
    }
    @Override
    public void parse() {
        System.out.println("Parsing " + this.name);
        Main.symbolTable.put(this.name, this);
    }

    @Override
    public void validate() {

    }

    @Override
    public void evaluate() {

    }
}
