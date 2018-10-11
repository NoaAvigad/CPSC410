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
