public class LeaveRecord_NL extends LeaveRecord{
    public LeaveRecord_NL(Day start, Day end){
        super(start,end);
    }

    @Override
    public String getTypeLeaves(){
        return "NL";
    }
}
