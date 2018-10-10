package libs;
import ast.ABSTRACTDEC;
import ast.CLASSDEC;
import ast.DEC;
import ast.FOLDERDEC;

public class DecFactory {

    public static DEC getDec(TokenizedLine tokens) {
        DEC toReturn = null;
        switch(tokens.pop()) {
            case "Folder":
                toReturn = new FOLDERDEC(tokens);
                break;
            case "Abstract":
                toReturn = new ABSTRACTDEC(tokens);
                break;
            case "Class":
                toReturn = new CLASSDEC(tokens);
                break;
            default:
                System.exit(1); // Probably move this to caller + tokenizer
        }
        return toReturn;
    }
}
