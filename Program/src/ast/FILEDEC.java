package ast;

import libs.TokenizedLine;
import ui.Main;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

abstract class FILEDEC extends DEC {
    ArrayList<MEMBER> members = new ArrayList<>();
    String _extends = null;
    Boolean doesHaveList = false;

    public FILEDEC(TokenizedLine tokens) {
        super(tokens);
    }

    @Override
    public void parse() {
        this.name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
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

                String parentClassDir = extendClassName.substring(0, extendClassName.lastIndexOf("/") + 1);
                String parentClassName = extendClassName.substring(extendClassName.lastIndexOf("/") + 1);
                String fullParentClassPath = parentClassDir + Character.toUpperCase(parentClassName.charAt(0)) + parentClassName.substring(1);

                this._extends = fullParentClassPath; //update to have the full path
            }
        }
        if (this.tokens.checkNext("(")) {
            this.parseMembers();
        }

        if (Main.symbolTable.containsKey(this.fullPath + ".file")) {
            this.kill("Class name already exists in this directory");
        }

        System.out.println(this.fullPath + ".file");
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

                        // set parent member to be protected
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
        try {
            /**
             * Since we evaluate in a random order (by iterating over the map), might need to create the folder for each
             *  file as well.
             */

            File parentDir = new File(dirPath);
            parentDir.mkdirs();
            //Files.deleteIfExists(Paths.get(fullPath + ".java"));
            File file = new File(dirPath + "/" + name + ".java");
            file.createNewFile();
        } catch (IOException e) {
            this.kill("Error creating file with the following path: " + fullPath);
        }
    }

    protected void parseMembers() {
        this.tokens.pop(); // Pop open brace
        boolean first = true;
        while (!this.tokens.peek().equals(")")) {
            if (!first) {
                if (this.tokens.checkNext(",")) {
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
            //set flag for list
            if (member.type.contains("List")) {
                this.doesHaveList = true;
            }
            this.members.add(member);
            first = false;
        }
        this.tokens.pop(); // Pop close brace
    }

    private StringBuilder buildGetSetSb(MEMBER mem, String getOrSet, boolean isOverride) {
        StringBuilder sb = new StringBuilder("\t");

        if (isOverride) {
            sb.append("@Override");
            sb.append("\n\t");
        }

        sb.append("public ").append(mem.type).append(" ")
                .append(getOrSet).append(mem.name.substring(0, 1).toUpperCase()).append(mem.name.substring(1));

        if (getOrSet.equals("get")) {
            sb.append("() {").append("\n\t\t").append("return this.").append(mem.name).append(";");
        } else {
            sb.append("(" + mem.type + " " + mem.name + ") {").append("\n\t\t").append("this.").append(mem.name).append(" = ").append(mem.name).append(";");
        }

        sb.append("\n\t}");
        return sb;
    }

    protected void buildMembers(List<MEMBER> members, PrintWriter out, List<MEMBER> getterOrSetterMems) {
        for(MEMBER mem : members) {
            StringBuilder sb = new StringBuilder();
            if (!mem.isInSuper) {
                sb.append("\t");
                if (mem.isProtect) {
                    sb.append("protected");
                } else {
                    sb.append("private");
                }

                sb.append(" ").append(mem.type).append(" ").append(mem.name).append(";");
            }

            if(!sb.toString().equals("")) {
                out.println(sb.toString());
            }

            if(mem.get || mem.set) {
                getterOrSetterMems.add(mem);
            }
        }

        out.println();
    }

    protected String buildInheritanceSignature(PrintWriter out) {
        String inheritanceSignature = "";
        if(this._extends != null) {
            String parentClassDir = this._extends.substring(0, this._extends.lastIndexOf("/"));
            String classNameWithNoPath = this._extends.substring(this._extends.lastIndexOf("/") + 1);
            if(!dirPath.equals(parentClassDir)) {
                int rootDirLocation = parentClassDir.indexOf(Main.ROOT_DIR) + 1;
                String parentImport = parentClassDir.substring(rootDirLocation + Main.ROOT_DIR.length()).replace("/", ".");
                out.println("import " + parentImport + "." + classNameWithNoPath + ";");
                out.println();
            }
            inheritanceSignature = " extends " + classNameWithNoPath;

        }

        return inheritanceSignature;
    }

    protected void buildGettersAndSetters(PrintWriter out, List<MEMBER> getterOrSetterMems) {
        for(MEMBER mem : getterOrSetterMems) {
            if(mem.get) {
                out.println(this.buildGetSetSb(mem, "get", mem.pHasGet).toString());
            }

            if (mem.set) {
                out.println(this.buildGetSetSb(mem,"set", mem.pHasSet).toString());
            }
        }
    }

    protected void buildPackageStatement(PrintWriter out) {
        int rootDirLocation = this.dirPath.indexOf(Main.ROOT_DIR) + 1;
        String packageName = this.dirPath.substring(rootDirLocation + Main.ROOT_DIR.length()).replace("/", ".");
        out.println("package " + packageName + ";");
        out.println();
    }

    protected void buildConstructor(PrintWriter out) {
        StringBuilder sb = new StringBuilder();
        sb.append("\t").append("public " + this.fullPath.substring(this.fullPath.lastIndexOf("/") + 1) + "() {")
                .append("\n\t").append("}");
        out.println(sb.toString());
        out.println();
    }
}

