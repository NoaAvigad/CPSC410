package model;

public class MEMBER {
    private String type;
    private String name;
    private boolean get;
    private boolean set;

    public MEMBER(String type, String name, boolean get, boolean set) {
        this.type = type;
        this.name = name;
        this.get = get;
        this.set = set;
    }
}
