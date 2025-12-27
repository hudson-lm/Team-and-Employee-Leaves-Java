public class LeaveRecord_BL extends LeaveRecord{
    public LeaveRecord_BL(Day start, Day end){
        super(start,end);
    }

    @Override
    public String getTypeLeaves(){
        return "BL";
    }
}
