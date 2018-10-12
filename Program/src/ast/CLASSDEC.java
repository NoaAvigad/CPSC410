package ast;

import libs.TokenizedLine;
import ui.Main;

import java.io.File;
import java.io.IOException;

public class CLASSDEC extends FILEDEC {

    public CLASSDEC(TokenizedLine tokens) {
        super(tokens);
    }

    @Override
    public void parse() {
        super.parse();
    }

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
        System.out.println("In CLASSDEC");

        try {
            /**
             * Since we evaluate in a random order (by iterating over the map), might need to create the folder for each
             *  file as well.
             */
            File parentDir = new File(dirPath);
            parentDir.mkdirs();
            File file = new File(fullPath + ".java");
            file.createNewFile();
        } catch (IOException e) {
            this.kill("Error creating file with the following path: " + fullPath);
        }
    }
}
