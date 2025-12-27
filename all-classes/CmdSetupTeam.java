public class CmdSetupTeam extends RecordedCommand{
    private String teamName;
    private String eName;
    private Team t;
    @Override
    public void execute(String[] cmdParts){
        if(cmdParts.length < 3){
            throw new ExInsufficientCommandArguments();
        }
        teamName = cmdParts[1];
        eName = cmdParts[2];
        Company company = Company.getInstance();
        t = company.createTeam(teamName, eName);

        addUndoCommand(this);
        clearRedoList();

        System.out.println("Done. ");
    }

    @Override
    public void undoMe(){
        Company company = Company.getInstance();
        company.removeTeam(t);

        addRedoCommand(this);
    }

    @Override
    public void redoMe(){
        Company company = Company.getInstance();
        company.createTeam(teamName, eName);
        addUndoCommand(this);
    }
}
