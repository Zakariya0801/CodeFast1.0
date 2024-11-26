package application.Industry;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import application.PersistantFactory;
import application.UserInformation;
import application.Student.Student;
import databasepackage.DBHandler;
import databasepackage.MySqlHandler;
import javafx.scene.control.Label;

public class IndustryInformation extends UserInformation{
	public String Description;
	private HashMap<String,Student> EnrolledStudents;
	private HashMap<String,Student> PendingStudents;
	private HashMap<String,Student> RejectedStudents;
	private HashMap<String,Student> RequestedStudents;
	public IndustryInformation(String id) throws NumberFormatException, SQLException {
		super(id);
		//DB Operations
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		
		String type = id.substring(0, 2);
		if(type.equalsIgnoreCase("id")) {
			rs = handle.ExecuteQuery("Industry", "Industry_Id = \""+id+"\"");
		}
		else {
			System.out.println("asfdad");
		}
		if(rs.next())
		{
			Description = rs.getString("Description");
		}
	}
	
	public IndustryInformation(String iD, String name, String email, String password, String description,HashMap<String,Student> EnrolledStudents
								,HashMap<String,Student> PendingStudents,HashMap<String,Student> RejectedStudents,HashMap<String,Student> RequestedStudents) throws SQLException {
		super(iD,name,email,password);
		Description = description;
		this.EnrolledStudents = EnrolledStudents;
		this.PendingStudents = PendingStudents;
		this.RejectedStudents = RejectedStudents;
		this.RequestedStudents = RequestedStudents;
	}
	public int getStatus(String StudentID) {
		if(isEnrolled(StudentID)) return 0;
		if(isPending(StudentID)) return 1;
		if(isRejected(StudentID)) return 2;
		if(isRequested(StudentID)) return 3;
		return -1;
	}
	public boolean isEnrolled(String StudentID) {
		return EnrolledStudents.containsKey(StudentID);
	}
	public boolean isPending(String StudentID) {
		return PendingStudents.containsKey(StudentID);
	}
	public boolean isRejected(String StudentID) {
		return RejectedStudents.containsKey(StudentID);
	}
	public boolean isRequested(String StudentID) {
		return RequestedStudents.containsKey(StudentID);
	}
	public void setAsPending(String StudentID, Student s) {
		PendingStudents.put(StudentID, s);
	}
	public void setAsRejected(String StudentID, Student s) {
		PendingStudents.remove(StudentID);
		RejectedStudents.put(StudentID, s);
	}
	public void setAsAccepted(String StudentID, Student s) {
		PendingStudents.remove(StudentID);
		EnrolledStudents.put(StudentID, s);
	}
	public void setAsRequested(String StudentID, Student s) {
		RequestedStudents.put(StudentID, s);
	}
	@Override
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	
	public HashMap<String, Student> getEnrolledStudents() {
		return EnrolledStudents;
	}

	public void setEnrolledStudents(HashMap<String, Student> enrolledStudents) {
		EnrolledStudents = enrolledStudents;
	}

	public HashMap<String, Student> getPendingStudents() {
		return PendingStudents;
	}

	public void setPendingStudents(HashMap<String, Student> pendingStudents) {
		PendingStudents = pendingStudents;
	}

	public HashMap<String, Student> getRejectedStudents() {
		return RejectedStudents;
	}

	public void setRejectedStudents(HashMap<String, Student> rejectedStudents) {
		RejectedStudents = rejectedStudents;
	}

	public HashMap<String, Student> getRequestedStudents() {
		return RequestedStudents;
	}

	public void setRequestedStudents(HashMap<String, Student> requestedStudents) {
		RequestedStudents = requestedStudents;
	}

	@Override
	public void setAccount(List<Label> labels) {
		labels.get(0).setText(Name);
		labels.get(1).setText(Name);
		labels.get(2).setText(getDescription());
		labels.get(3).setText(getEmail());
		labels.get(4).setText(getDescription());
		labels.get(5).setText(ID);
	}
}
