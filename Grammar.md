```
JAVA ::= DEC+
DEC ::= FILEDEC | FOLDERDEC
FOLDERDEC ::= “Folder” FNAME DEC* “End” FNAME
FNAME ::= STRING
FILEDEC ::= CLASSTYPE CNAME (“>” INHERIT)? (DATA)?
CLASSTYPE ::= “Class” | “Abstract”
DATA ::= “(” MEMBERDEC (", " MEMBERDEC)* ")"
MEMBERDEC ::=  MTYPE MNAME ("[" "get”? "/"? ”set”? "]")?
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
        Abstract Vehicle (String brand, int value, int year, int seats)
        Class Car > Vehicle (int engineSize [get/set])
        Class Bicycle > Vehicle (int wheelsSize [get/set])
    End classes
End src
```

# Example 2
```
Folder root
    Folder Humans
        Class Person (String name, int age)
        Class Teacher > Person (int yearsOfExperience [get/set], String subject [get])
        Class Student > Person (int grade [set])
    End Humans
    Class Robots, (List<String> components [get/set])
End root
Folder tests
    Class UnitTest
End tests
```

# Other things we talked about:
- No get / set  => make the field public otherwise, private
- We can make files without folders 
- Empty folders are not allowed

