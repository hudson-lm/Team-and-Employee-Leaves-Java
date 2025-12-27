public class ExOutOfRange extends RuntimeException {
    public ExOutOfRange(){
       super("Out of range (Entitled Annual Leaves should be within 0-300)!"); 
    }
    public ExOutOfRange(String msg){
        super(msg);
    }
}
