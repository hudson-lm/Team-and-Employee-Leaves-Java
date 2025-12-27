public class LeaveRecord_SL extends LeaveRecord {
    public LeaveRecord_SL(Day start, Day end){
        super(start,end);
    }

    @Override
    public String getTypeLeaves(){
        return "SL";
    }
}
