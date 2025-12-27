public class ExInsufficientCommandArguments extends RuntimeException{
    public ExInsufficientCommandArguments(){
        super("Insufficient command arguments!");
    };
    public ExInsufficientCommandArguments(String msg){
        super(msg);
    }
}