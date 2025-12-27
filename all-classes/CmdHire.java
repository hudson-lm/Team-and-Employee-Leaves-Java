public class CmdHire extends RecordedCommand {
    private Employee e;
    private int annualLeaves;
    @Override
    public void execute(String[] cmdParts){
        if(cmdParts.length<3){
            throw new ExInsufficientCommandArguments();
        }
        annualLeaves = Integer.parseInt(cmdParts[2]);
        if(annualLeaves>300){
            throw new ExOutOfRange();
        }
        e = Company.getInstance().createEmployee(cmdParts[1], annualLeaves);

        addUndoCommand(this);
        clearRedoList();

        System.out.println("Done. ");
    }

    @Override
    public void undoMe(){
        Company company = Company.getInstance();
        company.removeEmployee(e);

        addRedoCommand(this);
    }

    @Override
    public void redoMe(){
        Company company = Company.getInstance();
        company.createEmployee(e.getName(), annualLeaves);
        addUndoCommand(this);
    }
}
