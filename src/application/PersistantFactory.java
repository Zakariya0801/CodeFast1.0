package application;

import java.sql.SQLException;

import application.Industry.Industry;
import application.Industry.IndustryInformation;
import application.Student.Plan;
import application.Student.PremiumPlan;
import application.Student.StandardPlan;
import application.Student.Student;
import application.Student.StudentInformation;
import controllers.AdminController;
import controllers.IndustryController;
import controllers.StudentController;
import controllers.UserController;
import databasepackage.DBHandler;
import databasepackage.MySqlHandler;

public class PersistantFactory {
	private static PersistantFactory instance = null;
	private PersistantFactory() {
		
	}
	public static synchronized PersistantFactory getInstance() {
		if(instance == null) instance = new PersistantFactory();
		return instance;
	}
	public DBHandler getDatabase() {
		return MySqlHandler.getHandler();
	}
	public User getUser(String ID) throws SQLException {
		String type = ID.substring(0, 2);// AD ST ID
		if(type.equalsIgnoreCase("st")) 
			return InformationExpert.getInstance().getStudent(ID);
		else if(type.equalsIgnoreCase("ad")) 
			return InformationExpert.getInstance().getAdmin();
		else if(type.equalsIgnoreCase("id"))
			return InformationExpert.getInstance().getIndustry(ID);
		return null;
	}
	public UserInformation getUserInformation(String ID) throws NumberFormatException, SQLException {
		String type = ID.substring(0, 2);// AD ST ID
		if(type.equalsIgnoreCase("st")) 
			return InformationExpert.getInstance().getStudentInformation(ID);
		else if(type.equalsIgnoreCase("ad")) 
			return InformationExpert.getInstance().getAdminInfo();
		else if(type.equalsIgnoreCase("id"))
			return InformationExpert.getInstance().getIndustryInformation(ID);
		return null;
	}
	public UserController getUserController(String ID) throws NumberFormatException, SQLException {
		String type = ID.substring(0, 2);// AD ST ID
		if(type.equalsIgnoreCase("st")) 
			return new StudentController();
		else if(type.equalsIgnoreCase("ad")) 
			return new AdminController();
		else if(type.equalsIgnoreCase("id"))
			return new IndustryController();
		return null;
	}
	public Plan getUserPlan(int isPlan) {
		if(isPlan == 1) 
			return new PremiumPlan();
		return new StandardPlan();
	}
	public DBHandler getDatabaseHandler() {
		return MySqlHandler.getHandler();
	}
}
