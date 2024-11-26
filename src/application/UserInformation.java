package application;
import databasepackage.DBHandler;
import databasepackage.MySqlHandler;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import application.Student.Plan;
import application.Student.StudentInformation;
public class UserInformation {
	public String ID;
	public String Name;
	public String Email;
	public String Password;
	public ResultSet rs;
	public Connection connection = null;
	public void print() {
		System.out.print("User = " + ID + " " + Name + " " + Email + " " + Password);
	}
	public UserInformation(String id) throws SQLException {
		ID = id;
		
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		String type = id.substring(0, 2);
		if(type.equalsIgnoreCase("st")) {
			
			rs = handle.ExecuteQuery("Students", "Student_Id = \""+id + "\" and Status=1");
		}
		else if(type.equalsIgnoreCase("ad")) {
			rs = handle.ExecuteQuery("Admin", "Admin_Id = \""+id + "\"");
			
		}
		else if(type.equalsIgnoreCase("id")) {
			rs = handle.ExecuteQuery("Industry", "Industry_Id = \""+id+"\"");
		}
		else {
			System.out.println("asfdad");
		}
		if(rs != null &&  rs.next())
		{
			Name=rs.getString("Name");
			Email=rs.getString("Email");
			Password=rs.getString("Password");
		}
		else {
			Name = null;
		}
		handle.CloseConnection();
		///DB Operations
	}
	
	public UserInformation(String iD, String name, String email, String password) {
		ID = iD;
		Name = name;
		Email = email;
		Password = password;
	}

	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public void getPersonalInfo(StudentInformation st) {
	
	}
	public void setPerformance(float performance) throws SQLException {}
	public void UpdatePerformance(Label remarks,float performance) {}
	public String getDescription() {
		return null;
	}
	public Plan getPlan() {
		return null;
	}
	public String getDegree() {
		return null;
	}
	public float getCgpa() {
		return 0;
	}
	public float getPerformance() {
		return 0;
	}
	public void setAccount(List<Label> labels) {}
}
