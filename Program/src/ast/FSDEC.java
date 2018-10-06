package ast;

import libs.Node;

public abstract class FSDEC extends Node {
    private String path;
    private String name;

    public FSDEC (String path) {
        this.path = path;
    }
}
