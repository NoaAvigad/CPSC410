package ast;

import libs.TokenizedLine;
import ui.Main;

import java.util.ArrayList;

abstract class FILEDEC extends DEC {
    ArrayList<MEMBER> members;
    String _extends = null;

    public FILEDEC(TokenizedLine tokens) {
        super(tokens);
    }

    @Override
    public void parse() {
        System.out.println("Parsing " + this.name);
        if (this.tokens.checkNext(">")) {
            if (this.tokens.eof()) {
                this.kill("Failed inheritance");
            } else {
                this._extends = tokens.pop();
            }
        } else if (this.tokens.checkNext("(")) {
            this.parseMembers();
        }
        Main.symbolTable.put(this.name, this);
    }

    // I think these will largely be the same  be the same between abstract and class,
    // so we can put most of the functionality here and call super() plus any details.
    @Override
    public void validate() {

    }

    @Override
    public void evaluate() {

    }

    protected void parseMembers() {
        this.tokens.pop(); // Pop open brace
        while (!this.tokens.peek().equals(")")) {
            if (this.tokens.eof()) {
                this.kill("Reached eof parsing members");
            }
            this.parseMember();
        }
        this.tokens.pop(); // Pop close brace
    }

    protected void parseMember() {
        Boolean get = false;
        Boolean set = false;
        String type = tokens.pop();
        String name = tokens.pop();
    }


}
