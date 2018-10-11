package ast;

import libs.TokenizedLine;
import ui.Main;

public class ABSTRACTDEC extends FILEDEC {

    public ABSTRACTDEC(TokenizedLine tokens) {
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

    }
}
