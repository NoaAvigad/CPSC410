package ast;

import libs.DecFactory;
import libs.TokenizedLine;
import ui.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

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

    }

    @Override
    public void evaluate() {
        System.out.println("Evaluating folder: " + fullPath);
        if(new File(fullPath).mkdirs()) {
            System.out.println("Created folder " + fullPath);
        } else {
            System.out.println("Folder " + fullPath + " not created since it already exists");
        }
    }
}
