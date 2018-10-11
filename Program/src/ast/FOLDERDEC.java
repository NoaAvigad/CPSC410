package ast;

import libs.DecFactory;
import libs.TokenizedLine;
import ui.Main;

import java.io.File;

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
        System.out.println("LETS TRY CREATING FOLDERS: " + fullPath);
        if(new File(fullPath).mkdirs()) {
            System.out.println("Folder created");
        } else {
            System.out.println("Something went wrong");
        }
    }
}
