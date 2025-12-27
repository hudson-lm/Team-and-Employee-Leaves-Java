import java.util.*;

public class Team implements Comparable<Team> {
    private String teamName;
    private Employee head;
    private Day dateSetup;
    private ArrayList<Employee> teamMembers;
    private ArrayList<ActingHead> listActingHead;

    public Team(String n, Employee hd){
        this.teamName = n;
        this.head = hd;
        this.dateSetup = SystemDate.getInstance().clone();
        this.teamMembers = new ArrayList<>();
        this.listActingHead = new ArrayList<>();
        this.teamMembers.add(hd);
    }

    public void addMember(Employee e){
        boolean isContained = this.teamMembers.contains(e);
        if(!isContained){
            this.teamMembers.add(e);
            Collections.sort(teamMembers);
        }else{
            throw new ExEmployeeHasJoined("The employee has joined the team already!");
        }
    }

    public void removeMember(Employee e){
        if(this.teamMembers.contains(e)){
            this.teamMembers.remove(e);
        }
    }

    public static void listTeamMember(ArrayList<Team> list){
        Collections.sort(list);
        for(Team t:list){
            System.out.println(t.teamName+":");
            Collections.sort(t.teamMembers);
            for(Employee e:t.teamMembers){
                if(e==t.head){
                    System.out.println(e.getName()+" (Head of Team)");
                }else{
                    System.out.println(e.getName());
                }
            }

            if(!t.listActingHead.isEmpty()){
                System.out.println("Acting heads:");
                Collections.sort(t.listActingHead);
                for(ActingHead AH: t.listActingHead){
                    System.out.println(AH.getStart() + " to " + AH.getEnd() + ": " + AH.getActingHead().getName());
                }
            }

            System.out.println();
        }
    }

    public static void list(ArrayList<Team> list){
        System.out.printf("%-30s%-10s%-13s\n","Team Name", "Leader", "Setup Date");
        for(Team t:list){
            System.out.printf("%-30s%-10s%-13s\n",t.teamName, t.head.getName(), t.dateSetup);
        }
    }

    public int compareTo(Team another){
        return this.teamName.compareTo(another.teamName);
    }

    public void listRole(Employee e){
        if(teamMembers.contains(e)){
            if(e==this.head){
                System.out.println(this.teamName+" (Head of Team)");
            }else{
                System.out.println(this.teamName);
            }
        }
    }

    public void addActingHead(Day start, Day end, Employee newHead){
        if(!teamMembers.contains(newHead)){
            String msg = "Employee (" + newHead.getName() + ") not found for " + teamName + "!";
            throw new ExEmployeeNotFoundInTeam(msg);
        }
        for(LeaveRecord l: newHead.getLeaveRecord()){
            if(l.isOverlapped(start,end)){
                String msg = newHead.getName() + " is on leave during " + l.getStart() + " to " + 
                l.getEnd() + " [" + l.getTypeLeaves() + "]!";
                throw new ExActingHeadIsOnLeave(msg);
            }
        }

        ActingHead AH = new ActingHead(start, end, newHead, this.head, this);
        listActingHead.add(AH);
    }

    public void removeActingHead(Day start, Day end, Employee currHead, Employee newHead){
        for(int i=0;i<listActingHead.size();i++){
            ActingHead AH = listActingHead.get(i);
            if(AH.getStart().equals(start) && AH.getEnd().equals(end) && AH.getOriHead() == currHead && AH.getActingHead() == newHead){
                listActingHead.remove(i);
                break;
            }
        }
    }

    public String getTeamName(){
        return teamName;
    }

    public ArrayList<Employee> getTeamMembers(){
        return teamMembers;
    }

    public Employee getHead(){
        return head;
    }
}
