package ast;

import libs.TokenizedLine;

import java.util.ArrayList;

abstract class FILEDEC extends DEC {
    ArrayList<MEMBER> members;

    public FILEDEC(TokenizedLine tokens) {
        super(tokens);
    }

    @Override
    public void parse() {

    }

    // I think these will largely be the same  be the same between abstract and class,
    // so we can put most of the functionality here and call super() plus any details.
    @Override
    public void validate() {

    }

    @Override
    public void evaluate() {

    }
}
