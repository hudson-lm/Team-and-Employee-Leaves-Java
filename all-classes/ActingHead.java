public class ActingHead implements Comparable<ActingHead>{
    private Day start;
    private Day end;
    private Employee actingHead;
    private Employee oriHead;
    private Team team;

    public ActingHead(Day start, Day end, Employee actingHead, Employee oriHead, Team team){
        this.start = start;
        this.end = end;
        this.actingHead = actingHead;
        this.oriHead = oriHead;
        this.team = team;
    }

    public Day getStart() { 
        return start; 
    }
    public Day getEnd() { 
        return end; 
    }
    public Employee getActingHead() { 
        return actingHead; 
    }
    public Employee getOriHead() { 
        return oriHead; 
    }
    public Team getTeam(){
        return team;
    }

    @Override
    public int compareTo(ActingHead another) {
        int startCompare = this.start.compareTo(another.start); //first compare the startDate
        if (startCompare != 0) { // if same
            return startCompare;
        }
        return this.end.compareTo(another.end); // compare the endDate
    }
}
