```
JAVA ::= DEC+
DEC ::= FILEDEC | FOLDERDEC
FOLDERDEC ::= “Folder” FNAME DEC* 
FNAME ::= STRING
FILEDEC ::= CLASSTYPE CNAME (“>” INHERIT)? (DATA)?
CLASSTYPE ::= “Class” | “Abstract”
DATA ::= “(” MEMBERDEC (", " MEMBERDEC)* ")"
MEMBERDEC ::=  MTYPE MNAME GETSET?
GETSET ::= "[" "get" | "set" | "get/set" "]"
MTYPE ::= OBJECT | ”List[”OBJECT”]” | PRIMITIVE
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
```

# Example 2
```
Folder root
    Folder Human
        Class Person (String name, int age)
        Class Teacher > Person (int yearsOfExperience [get/set], String subject [get])
        Class Student > Person (int grade [set])
    Class Robot (List[String] components [get/set])
Folder tests
    Class UnitTest
```

