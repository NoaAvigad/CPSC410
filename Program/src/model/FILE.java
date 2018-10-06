package model;

import java.util.ArrayList;

abstract class FILE {
    private String name;
    private String path;
    private ArrayList<String> _member_names; // Strings only, after parse
    private ArrayList<String> _inherit_names; // Strings only, after parse

    public FILE() {

    }

    public abstract void parse();
}
