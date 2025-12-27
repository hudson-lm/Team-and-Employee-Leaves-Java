import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner in = new Scanner(System.in);

		System.out.print("Please input the file pathname: ");
		String filepathname = in.nextLine();
		
		Scanner inFile = new Scanner(new File(filepathname));
		
		//The first command in the file must be to set the system date 
		//(eg. "startNewDay|01-Jan-2025"); and it cannot be undone
		String cmdLine1 = inFile.nextLine();
		String[] cmdLine1Parts = cmdLine1.split("\\|");
		System.out.println("\n> "+cmdLine1);
		SystemDate.createTheInstance(cmdLine1Parts[1]);
		
		while (inFile.hasNext())		
		{
			String cmdLine = inFile.nextLine().trim();
			
			//Blank lines exist in data file as separators.  Skip them.
			if (cmdLine.equals("")) continue;  

			System.out.println("\n> "+cmdLine);
			
			String[] cmdParts = cmdLine.split("\\|"); 
			
			try {
				switch(cmdParts[0]){
					case "hire":
						(new CmdHire()).execute(cmdParts);
						break;
					case "listEmployees":
						(new CmdListEmployees()).execute(cmdParts);
						break;
					case "undo":
						RecordedCommand.undoOneCommand();
						break;
					case "redo":
                        RecordedCommand.redoOneCommand();
                        break;
                    case "setupTeam":
                        (new CmdSetupTeam()).execute(cmdParts);
                        break;
					case "startNewDay":
                        (new CmdStartNewDay()).execute(cmdParts);
                        break;
                    case "listTeams":
                        (new CmdListsTeam()).execute(cmdParts);
                        break;
					case "listAllRoles":
						(new CmdListAllRoles()).execute(cmdParts);
						break;
					case "addTeamMember":
						(new CmdAddTeamMember()).execute(cmdParts);
						break;
					case "listTeamMembers":
						(new CmdListTeamMembers()).execute(cmdParts);
						break;
					case "applyLeave":
						(new CmdApplyLeave()).execute(cmdParts);
					case "listLeaves":
						(new CmdListLeaves()).execute(cmdParts);
				}
			} catch (ExInsufficientCommandArguments e){
				System.out.println(e.getMessage());
			} catch (ExOutOfRange e){
				System.out.println(e.getMessage());
			} catch (ExLeaveOverlapped e){
				System.out.println(e.getMessage());
			} catch (ExEarlierLeaveDate e){
				System.out.println(e.getMessage());
			} catch (ExEmployeeAlreadyExist e){
				System.out.println(e.getMessage());
			} catch (ExEmployeeHasJoined e){
				System.out.println(e.getMessage());
			} catch (ExEmployeeNotFound e){
				System.out.println(e.getMessage());
			} catch (ExTeamAlreadyExist e){
				System.out.println(e.getMessage());
			} catch (ExTeamNotFound e){
				System.out.println(e.getMessage());
			} catch (ExInvalidLeaveType e){
				System.out.println(e.getMessage());
			} catch (ExInsufficientBalanceLeaveDay e){
				System.out.println(e.getMessage());
			} catch (ExInsufficientSickLeave e){
				System.out.println(e.getMessage());
			} catch (ExActingHeadIsOnLeave e){
				System.out.println(e.getMessage());
			} catch (ExMissingInputActingHead e){
				System.out.println(e.getMessage());
			} catch (ExEmployeeNotFoundInTeam e){
				System.out.println(e.getMessage());
			} catch (IllegalArgumentException e){
				System.out.println(e.getMessage());
			} 
		} 
		inFile.close();
		in.close();
	}
}