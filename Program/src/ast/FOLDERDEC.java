package ast;

import libs.DecFactory;
import libs.TokenizedLine;
import ui.Main;

public class FOLDERDEC extends DEC {
    public FOLDERDEC(TokenizedLine tokens) {
        super(tokens);
    }

    @Override
    public void parse() {
        System.out.println("Parsing " + this.name);
        Main.parseManager.enterDirectory(this.name);
        while(!Main.parseManager.eof() && Main.parseManager.nextIsSameDirectory()) {
            DEC child = DecFactory.getDec(Main.parseManager.yieldTokenizedLine());
            child.parse();
        }
        Main.parseManager.leaveDirectory();
        Main.symbolTable.put(this.name, this);
    }

    @Override
    public void validate() {

    }

    @Override
    public void evaluate() {

    }
}
