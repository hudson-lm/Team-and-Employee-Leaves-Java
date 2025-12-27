public class CmdAddTeamMember extends RecordedCommand {
    private String teamName;
    private String eName;
    @Override
    public void execute(String[] cmdParts){
        if(cmdParts.length != 3){
            throw new ExInsufficientCommandArguments();
        }
        Company company = Company.getInstance();
        teamName = cmdParts[1];
        eName = cmdParts[2];
        company.addTeamMember(teamName,eName);

        addUndoCommand(this);
        clearRedoList();

        System.out.println("Done. ");
    }

    @Override
    public void undoMe(){
        Company company = Company.getInstance();
        company.removeTeamMember(teamName, eName);

        addRedoCommand(this);
    }

    @Override
    public void redoMe(){
        Company company = Company.getInstance();
        company.addTeamMember(teamName,eName);
        addUndoCommand(this);
    }
}
