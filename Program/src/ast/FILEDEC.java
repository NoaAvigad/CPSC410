package ast;

import libs.TokenizedLine;
import ui.Main;

import java.util.ArrayList;

abstract class FILEDEC extends DEC {
    ArrayList<MEMBER> members = new ArrayList<>();
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
                this.tokens.pop(); // Consume ">";
                this._extends = tokens.pop();

                // if not full path assume same directory
                String extendClassName = this._extends.contains("/") ?
                        this._extends :
                        this.fullPath.substring(this.fullPath.lastIndexOf("/")) + this._extends;

                // if same as class name => kill
                if (extendClassName.equals(this.fullPath)) {
                    this.kill("Class cannot extend itself");
                }

                this._extends = extendClassName; //update to have the full path
            }
        }
        if (this.tokens.checkNext("(")) {
            this.parseMembers();
        }


        if (Main.symbolTable.containsKey(this.fullPath + ".file")) {
            this.kill("Class name already exists in this directory");
        }

        Main.symbolTable.put(this.fullPath + ".file", this);
    }

    // I think these will largely be the same  be the same between abstract and class,
    // so we can put most of the functionality here and call super() plus any details.
    @Override
    public void validate() {
        // check that extended class exists
        if (this._extends != null) {
            if (!Main.symbolTable.containsKey(this._extends + ".file")) {
                this.kill("extended class does not exist");
            }

            // check parent class getter and setters
            FILEDEC parent = (FILEDEC) Main.symbolTable.get(this._extends + ".file");

            for (MEMBER pm : parent.members) {

                for (MEMBER m : this.members) {
                    if (pm.getName().equals(m.getName()) && pm.getType().equals(m.getType())) {
                        // current member matches a parent member
                        m.pHasGet = pm.hasGetter();
                        m.pHasSet = pm.hasSetter();

                        // set parent memeber to be protected
                        pm.isProtect = true;
                    }
                }
            }
        }

        // validation other than superclass validations
    }

    @Override
    public void evaluate() {
        // memeber has 3 flags to tell if the parent has a getter, a setter and if it should be using the keyword protected :)  
    }

    protected void parseMembers() {
        this.tokens.pop(); // Pop open brace
        boolean first = true;
        while (!this.tokens.peek().equals(")")) {
            if(!first) {
                if(this.tokens.checkNext(",")) {
                    this.tokens.pop();
                } else {
                    this.kill("Invalid member list");
                }
            }
            if (this.tokens.eof()) {
                this.kill("Reached eof parsing members");
            }
            MEMBER member = new MEMBER(tokens);
            member.parse();
            this.members.add(member);
            first = false;
        }
        this.tokens.pop(); // Pop close brace
    }
}
