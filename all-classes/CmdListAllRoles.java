public class CmdListAllRoles implements Command{
    @Override
    public void execute(String[] cmdParts){
        Company company = Company.getInstance();
        company.listAllRoles(cmdParts[1]);
    }
}
