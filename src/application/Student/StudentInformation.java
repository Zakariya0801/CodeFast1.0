package application.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.InformationExpert;
import application.PersistantFactory;
import application.UserInformation;
import application.Student.Course.Course;
import databasepackage.DBHandler;
import databasepackage.MySqlHandler;
import javafx.scene.control.Label;

public class StudentInformation extends UserInformation{
	public float cgpa;
	public float performance;
	public Plan plan;
	public String degree;
	private boolean Registered;
	private HashMap<String,Course> registeredCourses;
	private HashMap<String,Course> PendingRegisterCourses;
	private HashMap<String,Evaluation> evaluations;
	@Override
	public void print() {
		super.print();
		System.out.println(" " + cgpa + " " + performance + " " + degree);
	}
	public StudentInformation(String id) throws NumberFormatException, SQLException {
		super(id);
		//DB Operations
			DBHandler handle = PersistantFactory.getInstance().getDatabase();
			rs = handle.ExecuteQuery("Students", "Student_Id = \""+id + "\"");						
			if(rs.next()) {
				cgpa = Float.parseFloat(rs.getString("CGPA"));
				performance = Float.parseFloat(rs.getString("SPerformance"));
				int isPlan = rs.getInt("SubscribedPlan");
				plan = PersistantFactory.getInstance().getUserPlan(isPlan);
				degree = rs.getString("Degree");
				Registered = rs.getInt("Status") == 1;
;			}
		handle.CloseConnection();
	}
	public StudentInformation(String iD, String name, String email, String password
			, float cgpa, float performance, Plan plan, String degree, boolean reg, HashMap<String,Course> regis
			,HashMap<String,Evaluation> evaluations, HashMap<String,Course> PendingRegisterCourses) throws SQLException {
		super(iD,name,email,password);
		this.cgpa = cgpa;
		this.performance = performance;
		this.plan = plan;
		this.degree = degree;
		this.Registered = reg;
		this.registeredCourses = regis;
		this.evaluations = evaluations;
		this.PendingRegisterCourses = PendingRegisterCourses;
	}
	public boolean isRegistered(String CourseID) {
		return registeredCourses.containsKey(CourseID);
	}
	public boolean isPending(String CourseID) {
		return PendingRegisterCourses.containsKey(CourseID);
	}
	public void setRegistered(String CourseID) {
		Course c = PendingRegisterCourses.get(CourseID);
		PendingRegisterCourses.remove(CourseID);
		registeredCourses.put(CourseID, c);
	}
	public void AddtoPending(String CourseID) throws SQLException {
		PendingRegisterCourses.put(CourseID, InformationExpert.getInstance().getCourse(CourseID));
	}
	public int getQuizEvaluationCount() {
		int ans = 0;
		for (Map.Entry<String, Evaluation> entry : evaluations.entrySet())
		{
			if(entry.getKey().substring(0,4).equalsIgnoreCase("quiz"))
				ans ++;
		}
		return ans;
	}
	public int getContestEvaluationCount() {
		int ans = 0;
		for (Map.Entry<String, Evaluation> entry : evaluations.entrySet())
			if(entry.getKey().substring(0,4).equalsIgnoreCase("cont"))
				ans++;
		return ans;
	}
	public HashMap<String, Evaluation> getEvaluations() {
		return evaluations;
	}
	public void setEvaluations(HashMap<String, Evaluation> evaluations) {
		this.evaluations = evaluations;
	}
	public HashMap<String, Course> getRegisteredCourses() {
		return registeredCourses;
	}
	public void setRegisteredCourses(HashMap<String, Course> registeredCourses) {
		this.registeredCourses = registeredCourses;
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
	public float getPerformance() {
		return performance;
	}
	public boolean isRegistered() {return Registered;}
	public void setAsRegistered() {
		Registered = true;
	}
	public void setPerformance(float performance) throws SQLException {
		this.performance = performance;
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		//Update Students
		//SET SPerformance = Performance 
		//WHERE Student_Id = 'ST_1';
		System.out.println("New Performance set to " + Float.toString(performance));
		values.add(Float.toString(performance));types.add("float");
		values.add(ID);types.add("string");
		handle.ExecuteUpdate("Update Students "
				+ "SET SPerformance = ? "
				+ "WHERE Student_Id = ?;", values, types);
		
		handle.CloseConnection();
	}
	public Plan getPlan() {
		return plan;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	public void setAccount(List<Label> labels) {
		labels.get(0).setText(Name);
		labels.get(1).setText(Name);
		labels.get(2).setText(Email);
		labels.get(3).setText(getPlan().isPremium() ? "Premium Plan" : "Standard Plan");
		labels.get(4).setText(getDegree());
		labels.get(5).setText(getDegree());
		labels.get(6).setText(Float.toString(getCgpa()));
		labels.get(7).setText(ID);
		labels.get(8).setText(Float.toString(getPerformance()));
	}
	@Override
	public void getPersonalInfo(StudentInformation st) {
		st.cgpa = this.cgpa;
		st.performance = this.performance;
		st.plan = this.plan;
		st.degree = this.degree;
	}
	public void UpdatePerformance(Label remarks,float performance)
	{
		if(performance>=4.0 && performance<=5.0)
			remarks.setText("Excellent! You are doing a great job");
		else if(performance>=3.0 && performance<4.0)
			remarks.setText("Keep Trying! Best Of Luck!");
		else if(performance>=2.0 && performance<3.0)
			remarks.setText("Participate in more Contests and Quizez");
		else if(performance>=1.0 && performance<2.0)
			remarks.setText("Try Hard! You can Do it");
		else
			remarks.setText("Good Luck! Participate in Quizes and Contest");
	}
}
