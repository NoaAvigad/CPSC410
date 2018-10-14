package ast;

import libs.Node;
import libs.TokenizedLine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MEMBER extends Node {
    private TokenizedLine tokens;
    private Boolean get = false;
    private Boolean set = false;
    private String type;
    private String name;
    private Set<String> allowedTypes = new HashSet<>(Arrays.asList
            ("int", "double", "char", "String", "Integer", "Character", "Double",
                    "List[String]", "List[Integer]", "List[Character]", "List[Double]"));

    public Boolean pHasGet = false;
    public Boolean pHasSet = false;
    public Boolean isProtect = false;

    public MEMBER(TokenizedLine tokens) {
        this.tokens = tokens;
    }

    public void parse() {
        String type = tokens.pop();
        if (!allowedTypes.contains(type)) {
            String typeUpper = Character.toUpperCase(type.charAt(0)) + type.substring(1);
            if (allowedTypes.contains(typeUpper)) {
                // did you mean ?
                this.kill("Did you mean: " + typeUpper + "?");
            } else {
                this.kill("Unsupported type - " + type);
            }
        }

        this.type = type;
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

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public Boolean hasSetter() {
        return this.set;
    }

    public Boolean hasGetter() {
        return this.get;
    }

}
