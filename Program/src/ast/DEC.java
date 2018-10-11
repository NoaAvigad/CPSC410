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

    abstract public void validate();
    abstract public void evaluate();
}