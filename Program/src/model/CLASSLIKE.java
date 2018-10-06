package model;

import java.util.ArrayList;

// Could maybe not extend this with ABSTRACT or CLASS and just make that distinction a member variable
abstract class CLASSLIKE extends FILE {
    private ArrayList<CLASSLIKE> extendList;
    private ArrayList<INTERFACE> implementList;
}
