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

        if (Main.symbolTable.containsKey(this.fullPath + ".folder")) {
            this.kill("Folder name already exists in this directory");
        }
        Main.symbolTable.put(this.fullPath + ".folder", this);
    }

    @Override
    public void validate() {
        // duplicate folder name is validated in parse
    }

    @Override
    public void evaluate() {

    }
}
