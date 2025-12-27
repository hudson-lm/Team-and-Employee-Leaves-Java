public abstract class LeaveRecord implements Comparable<LeaveRecord> {
    private Day start;
    private Day end;

    public abstract String getTypeLeaves();

    public LeaveRecord(Day start, Day end){
        this.start = start;
        this.end = end;
    }

    public Day getStart(){
        return start;
    }

    public Day getEnd(){
        return end;
    }

    @Override
    public int compareTo(LeaveRecord another){
        return this.start.compareTo(another.start);
    }

    @Override
    public String toString(){
        return String.format("%s to %s [%s]",this.start,this.end,this.getTypeLeaves());
    }

    public boolean isOverlapped(LeaveRecord another){
        if(this.start.compareTo(another.end) > 0 || this.end.compareTo(another.start) < 0){
            return false;
        }
        return true;
    }
    public boolean isOverlapped(Day start, Day end){
        if(!(end.compareTo(this.start)<0 || this.end.compareTo(start) < 0)){
            return true;
        }
        return false;
    }
}
