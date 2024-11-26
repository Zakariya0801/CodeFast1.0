package application;

import java.sql.SQLException;

import application.Student.Student;
import application.Student.Contest.Contest;
import application.Student.Contest.Problem;
import controllers.AlertSender;

public class Singleton {
	private static Singleton instance = null;
	private Singleton() {
		
	}
	public static synchronized Singleton getInstance() {
		if(instance == null) instance = new Singleton();
		return instance;
	}
	public Contest createNewContest(String name, String start, String end) throws SQLException {
		Contest c = new Contest(name,start,end);
		return c;
	}
	public Student getStudent(String name, String email, String pass, String degree, float cgpa) throws SQLException {
		Student s = new Student(name,email,pass,degree,cgpa);
		InformationExpert.getInstance().addStudentInformation(s.ID, name, email, pass, cgpa, degree);
		InformationExpert.getInstance().addStudent(s, s.getInformation());
		return s;
	}
	public Problem createProblem(Contest c,String Statement,String TestCaseInp,String TestCaseOut) throws SQLException {
		Problem p = new Problem(c.contestId,Statement,TestCaseInp,TestCaseOut);
		InformationExpert.getInstance().addContest(c, p);
		return p;
	}
}
