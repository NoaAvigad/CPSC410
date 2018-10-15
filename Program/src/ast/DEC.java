package ast;

import libs.Node;
import libs.TokenizedLine;
import ui.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public abstract class DEC extends Node {
    protected String name;
    protected String dirPath;
    protected String fullPath;
    protected TokenizedLine tokens;

    DEC(TokenizedLine tokens) {
        String name = tokens.pop(); // TODO probably something else
        this.name = name;
        this.dirPath = Main.parseManager.getFilepath();
        this.fullPath = this.dirPath + "/" + name;
        this.tokens = tokens;
    }

    abstract public void validate();
    abstract public void evaluate();

    public static void deleteFolderContents() {
        try {
            System.out.println("Deleting contents of root folder");
            Files.walk(Paths.get("./output/"))
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            System.out.println("Could not delete folder contents");
        }
    }
}
