public class CmdListTeamMembers implements Command {
    @Override
    public void execute(String[] cmdParts){
        Company company = Company.getInstance();
        company.listTeamMembers();
    }
}
