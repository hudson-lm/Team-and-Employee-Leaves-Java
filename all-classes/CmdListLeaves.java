public class CmdListLeaves implements Command {
    @Override
    public void execute(String[] cmdParts){
        Company company = Company.getInstance();
        if(cmdParts.length==2){
            company.listLeaves(cmdParts[1]);
        }else if(cmdParts.length == 1){
            company.listLeaves();
        }
    }
}
