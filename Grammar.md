```
JAVA ::= DEC+
DEC ::= FILEDEC | FOLDERDEC
FOLDERDEC ::= “Folder” FNAME DEC* “End” FNAME
FNAME ::= STRING
FILEDEC ::= CLASSTYPE CNAME (“>” INHERIT)? (“, “ DATA)?
CLASSTYPE ::= “Class” | “Abstract”
DATA ::= “Data:” MEMBERDEC (", " MEMBERDEC)*
MEMBERDEC ::=  MNAME MTYPE “g”?”s”?
MTYPE ::= OBJECT | ”List<”OBJECT”>” | PRIMITIVE
OBJECT ::= “String” | ”Integer” | ”Character” | ”Double”
PRIMITIVE ::= ”int” | ”double” | ”char”
INHERIT ::= STRING
MNAME ::= STRING
CNAME ::= STRING
```

# Example 1
```
Folder src
    Folder classes
        Abstract Vehicle, Data: brand String, value int, year int, seats int
        Class Car > Vehicle, Data: engineSize int gs
        Class Bicycle > Vehicle, Data: wheelsSize int gs
    End classes
End src
```

# Example 2
```
Folder root
    Folder Humans
        Class Person, Data: name String, age int
        Class Teacher > Person, Data: yearsOfExperience int gs, subject String g
        Class Student > Person, Data: grade int s
    End Humans
    Class Robots, Data: components List<String> gs
End root
Folder tests
    Class UnitTest
End tests
```

# Other things we talked about:
- No g / s  => make the field public otherwise, private
- We can make files without folders 
- Empty folders are not allowed

