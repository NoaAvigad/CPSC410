package ast;

import libs.TokenizedLine;
import ui.Main;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

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
                        this.dirPath + "/" + this._extends;

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
                    if (pm.name.equals(m.name) && pm.type.equals(m.type)) {
                        // current member matches a parent member
                        m.pHasGet = pm.get;
                        m.pHasSet = pm.set;

                        // set parent memeber to be protected
                        pm.isProtect = true;
                        m.isInSuper = true;
                    }
                }
            }
        }

        // validation other than superclass validations
    }

    @Override
    public void evaluate() {
        System.out.println("Evaluating file: " + fullPath + ".java");
        // memeber has 3 flags to tell if the parent has a getter, a setter and if it should be using the keyword protected :)
        try {
            /**
             * Since we evaluate in a random order (by iterating over the map), might need to create the folder for each
             *  file as well.
             */

            File parentDir = new File(dirPath);
            parentDir.mkdirs();
            //Files.deleteIfExists(Paths.get(fullPath + ".java"));
            File file = new File(fullPath + ".java");
            file.createNewFile();
        } catch (IOException e) {
            this.kill("Error creating file with the following path: " + fullPath);
        }

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
