package ast;

import libs.Node;
import libs.TokenizedLine;
import ui.Main;

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

    protected void kill(String message) {
        System.out.println(message);
        System.exit(1);
    }

}
