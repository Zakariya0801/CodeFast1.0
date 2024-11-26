package controllers.TablesData;

public class StudentData {
	String name; 
	String email; 
	String pass; 
	String degree; 
	float cgpa;
	public StudentData(String name, String email, String pass, String degree, float cgpa) {
		this.name = name;
		this.email = email;
		this.pass = pass;
		this.degree = degree;
		this.cgpa = cgpa;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public float getCgpa() {
		return cgpa;
	}
	public void setCgpa(float cgpa) {
		this.cgpa = cgpa;
	}

}