package libs;
import ast.ABSTRACTDEC;
import ast.CLASSDEC;
import ast.DEC;
import ast.FOLDERDEC;

public class DecFactory {

    public static DEC getDec(TokenizedLine tokens) {
        DEC toReturn = null;
        String Check = tokens.pop() ;
        switch(Check.toLowerCase()) {
            case "folder":
                toReturn = new FOLDERDEC(tokens);
                break;
            case "abstract":
                toReturn = new ABSTRACTDEC(tokens);
                break;
            case "class":
                toReturn = new CLASSDEC(tokens);
                break;
            default:
                System.out.print("Invalid Declaration : " + Check) ;
                System.exit(1); // Probably move this to caller + tokenizer
        }
        return toReturn;
    }
}
