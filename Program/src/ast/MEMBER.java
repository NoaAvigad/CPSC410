package ast;

import libs.Node;
import libs.TokenizedLine;

public class MEMBER extends Node {
    private TokenizedLine tokens;
    private Boolean get = false;
    private Boolean set = false;
    private String type;
    private String name;

    public MEMBER(TokenizedLine tokens) {
        this.tokens = tokens;
    }

    public void parse() {
        this.type = tokens.pop();
        this.name = tokens.pop();
        if(this.tokens.checkNext("[")) { // has getters/setters
            this.handleGetSet();
        }
    }

    private void handleGetSet() {
        this.tokens.pop(); // Consume "["
        if(this.tokens.checkNext("get")) {
            this.get = true;
        } else if(this.tokens.checkNext("set")) {
            this.set = true;
        } else if(this.tokens.checkNext("get/set")) {
            this.get = true;
            this.set = true;
        } else {
            this.kill("Invalid get/set");
        }
        this.tokens.pop(); // Consume getter/setter
        if (!this.tokens.checkNext("]")) {
            this.kill("Missed close brace");
        }
        this.tokens.pop(); // Consume close brace
    }
}
