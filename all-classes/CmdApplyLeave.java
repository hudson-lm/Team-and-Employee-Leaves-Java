import java.util.ArrayList;

public class CmdApplyLeave extends RecordedCommand{
    private String typeLeave;
    private Day start;
    private Day end;
    private String eName;
    private LeaveRecord leave;
    private Day dateOfEntry;
    private ArrayList<String> teamNames;
    private ArrayList<String> actingHeadNames;


    @Override
    public void execute(String[] cmdParts){
        dateOfEntry = SystemDate.getInstance().clone();
        Company company = Company.getInstance();
        start = new Day(cmdParts[3]);
        if(start.compareTo(dateOfEntry)<0){
            throw new ExEarlierLeaveDate(String.format("Wrong Date. Leave start date must not be earlier than the system date (%s)!",dateOfEntry));
        }
        end = new Day(cmdParts[4]);
        typeLeave = cmdParts[2];
        eName = cmdParts[1];

        teamNames = new ArrayList<>();
        actingHeadNames = new ArrayList<>();

        if(cmdParts.length>5){
            for(int i=5;i<cmdParts.length;i+=2){
                String teamName = cmdParts[i];
                String actingHeadName = cmdParts[i+1];
                teamNames.add(teamName);
                actingHeadNames.add(actingHeadName);
            }

            company.AreAllHavingActingHead(eName,teamNames);

            for(int i=0;i<teamNames.size(); i++){
                company.replaceHead(start, end, eName, teamNames.get(i), actingHeadNames.get(i));
            }
        }

        leave = company.applyLeave(eName, start, end, typeLeave);


        addUndoCommand(this);
        clearRedoList();

        System.out.println("Done. ");
    }

    @Override
    public void undoMe(){
        Company company = Company.getInstance();
        company.unapplyLeave(eName, leave);
        for(int i = 0; i < teamNames.size(); i++) {
            company.unreplaceHead(start, end, eName, teamNames.get(i), actingHeadNames.get(i));
        }
        addRedoCommand(this);
    }

    @Override
    public void redoMe(){
        Company company = Company.getInstance();
        company.applyLeave(eName, start, end, typeLeave);
        for(int i = 0; i < teamNames.size(); i++) {
            company.replaceHead(start, end, eName, teamNames.get(i), actingHeadNames.get(i));
        }
        addUndoCommand(this);
    }
}
