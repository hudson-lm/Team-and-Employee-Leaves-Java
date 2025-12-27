public class LeaveRecord_AL extends LeaveRecord {
    public LeaveRecord_AL(Day start, Day end){
        super(start,end);
    }

    @Override
    public String getTypeLeaves(){
        return "AL";
    }
}
