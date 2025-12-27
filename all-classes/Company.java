import java.util.*;

public class Company {
    private ArrayList<Employee> allEmployees;
    private ArrayList<Team> allTeams;
    private static Company instance = new Company();
    private Company(){
        allEmployees = new ArrayList<>();
        allTeams = new ArrayList<>();
    }
    public static Company getInstance(){
        return instance;
    }

    public void listTeams(){
        Team.list(allTeams);
    }

    public void removeTeam(Team t){
        allTeams.remove(t);
    }

    public Employee createEmployee(String n, int yle){
        for(Employee e: allEmployees){
            if(e.getName().equals(n)){
                throw new ExEmployeeAlreadyExist("Employee already exists!");
            }
        }
        Employee e = new Employee(n, yle);
        allEmployees.add(e);
        Collections.sort(allEmployees);
        return e;
    }

    public Team createTeam(String tName, String eName){
        Employee e = Employee.searchEmployee(allEmployees, eName);
        if(e == null){
            throw new ExEmployeeNotFound("Employee not found!");
        }
        for(Team t2: allTeams){
            if(t2.getTeamName().equals(tName)){
                throw new ExTeamAlreadyExist("Team already exists!");
            }
        }
        Team t = new Team(tName, e);
        allTeams.add(t);
        Collections.sort(allTeams);
        return t;
    }

    public void listAllRoles(String eName){
        boolean hasAnyRole = false;
        Employee e = Employee.searchEmployee(allEmployees, eName);
        if(e==null){
            throw new ExEmployeeNotFound("Employee not found!");
        }
        Collections.sort(allTeams);
        for(Team t:allTeams){
            if(t.getTeamMembers().contains(e)){
                hasAnyRole = true;
                t.listRole(e);
            }
        }
        if(!hasAnyRole){
            System.out.println("No role");
        }
    }

    public void removeEmployee(Employee e){
        allEmployees.remove(e);
    }

    public void listEmployees(){
        for(Employee e:allEmployees){
            System.out.println(e);
        }
    }

    public void listTeamMembers(){
        Team.listTeamMember(allTeams);
    }

    public LeaveRecord applyLeave(String eName, Day start, Day end, String typeLeave){
        Employee e = Employee.searchEmployee(allEmployees, eName);
        LeaveRecord l = e.addLeave(start, end, typeLeave);
        return l;
    }

    public void listLeaves(){
        for(Employee e:allEmployees){
            System.out.println(e.getName()+":");
            e.listingLeaves();
        }
    }

    public void listLeaves(String eName){
        Employee e = Employee.searchEmployee(allEmployees, eName);
        e.listingLeaves();
    }

    public void unapplyLeave(String eName, LeaveRecord l){
        Employee e = Employee.searchEmployee(allEmployees, eName);
        e.removeLeave(l);
    }

    public void addTeamMember(String teamName, String eName){
        boolean isTeamFound = false;
        Employee e = Employee.searchEmployee(allEmployees, eName);
        if(e==null){
            throw new ExEmployeeNotFound("Employee not found!");
        }
        for (Team t: allTeams){
            if(teamName.equals(t.getTeamName())){
                isTeamFound = true;
                t.addMember(e);
                break;
            }
        }
        if(!isTeamFound){
            throw new ExTeamNotFound("Team not found!");
        }
    }

    public void removeTeamMember(String teamName, String eName){
        Employee e = Employee.searchEmployee(allEmployees, eName);
        for (Team t: allTeams){
            if(teamName.equals(t.getTeamName())){
                t.removeMember(e);
                break;
            }
        }
    }
    
    public void replaceHead(Day start, Day end, String eName, String teamName, String actingHeadName ){
        Employee currHead = Employee.searchEmployee(allEmployees, eName);
        Employee newHead = Employee.searchEmployee(allEmployees, actingHeadName);
        Team team = null;
        for(Team t:allTeams){
            if(t.getTeamName().equals(teamName)){
                team = t;
                break;
            }
        }
        if(team == null){
            throw new ExTeamNotFound("Team not found!");
        }
        if(currHead==null){
            String msg = "Employee (" + eName + ") not found for " + teamName + "!";
            throw new ExEmployeeNotFound(msg);
        }
        if(newHead==null){
            String msg = "Employee (" + actingHeadName + ") not found for " + teamName + "!";
            throw new ExEmployeeNotFound(msg);
        }
        if(team.getHead()!=currHead){
            String errorMsg = String.format("%s is not the head of %s!", eName, team.getTeamName());
            throw new ExNotHeadTeam(errorMsg);
        }

        team.addActingHead(start,end,newHead);
    }


    public void unreplaceHead(Day start, Day end, String eName, String teamName, String actingHeadName){
        Employee currHead = Employee.searchEmployee(allEmployees, eName);
        Employee newHead = Employee.searchEmployee(allEmployees, actingHeadName);
        Team team = null;
        for(Team t:allTeams){
            if(t.getTeamName().equals(teamName)){
                team = t;
                break;
            }
        }

        if(team == null){
            throw new ExTeamNotFound("Team not found!");
        }
        if(currHead==null){
            String msg = "Employee (" + eName + ") not found for " + teamName + "!";
            throw new ExEmployeeNotFound(msg);
        }
        if(newHead==null){
            String msg = "Employee (" + actingHeadName + ") not found for " + teamName + "!";
            throw new ExEmployeeNotFound(msg);
        }
        if(team.getHead()!=currHead){
            String errorMsg = String.format("%s is not the head of %s!", eName, team.getTeamName());
            throw new ExNotHeadTeam(errorMsg);
        }

        team.removeActingHead(start, end, currHead, newHead);
    }

    public void AreAllHavingActingHead(String headName, ArrayList<String> teamNames){
        Employee head = Employee.searchEmployee(allEmployees, headName);
        if(head == null) {
            throw new ExEmployeeNotFound("Employee not found!");
        }

        ArrayList<String> teamsBelongToHead = new ArrayList<>();
        for(Team t : allTeams) {
            if(t.getHead() == head) {
                teamsBelongToHead.add(t.getTeamName());
            }
        }

        for(String teamName: teamsBelongToHead){
            if(!teamNames.contains(teamName)){
                throw new ExMissingInputActingHead("Missing input: Please provide the acting head for " + teamName);
            }
        }
    }
}
