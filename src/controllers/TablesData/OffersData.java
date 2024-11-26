package controllers.TablesData;

public class OffersData {
	public String Student_ID;
	public String Student_Name;
	public String Student_Email;
	public String Student_cgpa;
	public String Student_Performance;
	
	public OffersData(String student_id, String student_Name, String email, String gpa, String performance) {
		Student_ID = student_id;
		Student_Name = student_Name;
		Student_Email = email;
		Student_cgpa  = gpa;
		Student_Performance = performance;
	}

	public String getStudent_ID() {
		return Student_ID;
	}

	public void setStudent_ID(String student_ID) {
		Student_ID = student_ID;
	}

	public String getStudent_Name() {
		return Student_Name;
	}

	public void setStudent_Name(String student_Name) {
		Student_Name = student_Name;
	}

	public String getStudent_Email() {
		return Student_Email;
	}

	public void setStudent_Email(String student_Email) {
		Student_Email = student_Email;
	}

	public String getStudent_cgpa() {
		return Student_cgpa;
	}

	public void setStudent_cgpa(String student_cgpa) {
		Student_cgpa = student_cgpa;
	}

	public String getStudent_Performance() {
		return Student_Performance;
	}

	public void setStudent_Performance(String student_Performance) {
		Student_Performance = student_Performance;
	}
	
}
 