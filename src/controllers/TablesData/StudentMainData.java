package controllers.TablesData;

public class StudentMainData {
	public String StudentID;
	public String Name;
	public String Email;
	public String Degree;
	public String CGPA;
	public StudentMainData(String studentID, String name, String email, String degree, String cGPA) {
		StudentID = studentID;
		Name = name;
		Email = email;
		Degree = degree;
		CGPA = cGPA;
	}
	public String getStudentID() {
		return StudentID;
	}
	public void setStudentID(String studentID) {
		StudentID = studentID;
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
	public String getDegree() {
		return Degree;
	}
	public void setDegree(String degree) {
		Degree = degree;
	}
	public String getCGPA() {
		return CGPA;
	}
	public void setCGPA(String cGPA) {
		CGPA = cGPA;
	}
	
}
