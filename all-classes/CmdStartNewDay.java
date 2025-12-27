public class CmdStartNewDay extends RecordedCommand{
    private Day oldDateSetup;
    private String newDateSetup;
    @Override
    public void execute(String[] cmdParts){
        newDateSetup = cmdParts[1];
        oldDateSetup = SystemDate.getInstance().clone();
        SystemDate.getInstance().set(newDateSetup);

        addUndoCommand(this);
        clearRedoList();

        System.out.println("Done. ");
    }

    @Override
    public void undoMe(){
        SystemDate.getInstance().set(oldDateSetup.toString());

        addRedoCommand(this);
    }

    @Override
    public void redoMe(){
        SystemDate.getInstance().set(newDateSetup);
        addUndoCommand(this);
    }
}
