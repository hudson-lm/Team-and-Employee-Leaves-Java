import java.util.*;

public class Employee implements Comparable<Employee> {
    private String name;
    private int annualLeaves;
    private int remainingLeaves;
    private int remainingSickLeaves = 135;
    private ArrayList<LeaveRecord> listLeaves;

    public Employee(String n, int yle){
        this.name = n;
        this.annualLeaves = yle;
        this.remainingLeaves = yle;
        listLeaves = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public ArrayList<LeaveRecord> getLeaveRecord(){
        return listLeaves;
    }

    public LeaveRecord addLeave(Day start, Day end, String typeLeave){
        boolean isLeaveOverlapped = false;
        LeaveRecord overlappedLeave = null;
        LeaveRecord newLeave = null;

        switch(typeLeave){
            case "BL":
                newLeave = new LeaveRecord_BL(start, end);
                break;
            case "AL":
                newLeave = new LeaveRecord_AL(start, end);
                break;
            case "SL":
                newLeave = new LeaveRecord_SL(start, end);
                break;
            case "NL":
                newLeave = new LeaveRecord_NL(start, end);
                break;
            default:
                throw new IllegalArgumentException("Invalid leave type: " + typeLeave);
        }
        
        for(LeaveRecord l2:listLeaves){
            isLeaveOverlapped = newLeave.isOverlapped(l2);
            if(isLeaveOverlapped){
                overlappedLeave = l2;
                isLeaveOverlapped = true;
                break;
            }
        }
        
        if(isLeaveOverlapped){
            throw new ExLeaveOverlapped("Leave overlapped: The leave period "+ overlappedLeave +" is found!");
        }

        int durationOfLeaves = Day.calcDuration(start, end);

        if("BL".equals(typeLeave)){
            if(durationOfLeaves<7){
                throw new ExInvalidLeaveType("To apply annual leave for 6 days or less, you should use the normal Annual Leave (AL) type.");
            }

            if(remainingLeaves < durationOfLeaves){
                throw new ExInsufficientBalanceLeaveDay("Insufficient balance of annual leaves. " + remainingLeaves + " days left only!");
            }else{
                remainingLeaves = remainingLeaves - durationOfLeaves;
            }
        }
        else if("AL".equals(typeLeave)){
            if(durationOfLeaves>6){
                throw new ExInvalidLeaveType("To apply annual leave for 7 days or more, please use the Block Leave (BL) type.");
            }
            if(!isBLTaken()){
                int tmpLeaves = remainingLeaves - durationOfLeaves;
                if(tmpLeaves<7){
                    int remainingDays = remainingLeaves-7;
                    if(remainingDays<0){
                        remainingDays = 0;
                    }

                    throw new ExInvalidLeaveType("The annual leave is invalid.\n" + 
                    "The current balance is " + remainingLeaves + " days only.\n" +
                    "The employee has not taken any block leave yet.\n" + 
                    "The employee can take at most " + remainingDays + " days of non-block annual leave\n" +
                    "because of the need to reserve 7 days for a block leave.");
                }
            }

            if(remainingLeaves < durationOfLeaves){
                throw new ExInsufficientBalanceLeaveDay("Insufficient balance of annual leaves. " + remainingLeaves + " days left only!");
            }else{
                remainingLeaves = remainingLeaves - durationOfLeaves;
            }
        }
        else if("SL".equals(typeLeave)){
            if(remainingSickLeaves < durationOfLeaves){
                throw new ExInsufficientSickLeave("Insufficient balance of sick leaves. " + remainingSickLeaves + " days left only!");
            } else{
                remainingSickLeaves = remainingSickLeaves - durationOfLeaves;
            }
        }

        listLeaves.add(newLeave);
        Collections.sort(listLeaves);
        return newLeave;
    }

    public void removeLeave(LeaveRecord l){
        String leaveType = l.getTypeLeaves();
        int durationOfLeaves= Day.calcDuration(l.getStart(), l.getEnd());

        if("AL".equals(leaveType) || "BL".equals(leaveType)){
            remainingLeaves += durationOfLeaves;
        }else if("SL".equals(leaveType)){
            remainingSickLeaves+=durationOfLeaves;
        }

        listLeaves.remove(l);

    }

    public boolean isBLTaken(){
        for (LeaveRecord l: listLeaves){
            if("BL".equals(l.getTypeLeaves())){
                return true;
            }
        }
        return false;
    }

    public static Employee searchEmployee(ArrayList<Employee> list, String nameToSearch){
        for(Employee e:list){
            if(e.getName().equals(nameToSearch)){
                return e;
            }
        }
        return null;
    }

    public void listingLeaves(){
        boolean isLeaveExist = false;
        for(LeaveRecord l:listLeaves){
            isLeaveExist = true;
            System.out.println(l);
        }
        if(!isLeaveExist){
            System.out.println("No leave record");
        }
    }

    public int compareTo(Employee another){
        if(this.name.equals(another.name)) return 0;
        else if(this.name.compareTo(another.name)>0) return 1;
        else return -1;
    }

    @Override
    public String toString(){
        return String.format("%s (Entitled Annual Leaves: %d days)", name,annualLeaves);
    }
}
