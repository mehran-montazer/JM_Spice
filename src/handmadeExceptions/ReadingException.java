package handmadeExceptions;

public class ReadingException extends Exception{
    public ReadingException (String lineNumber){
        super("check line " + lineNumber);
    }
}
