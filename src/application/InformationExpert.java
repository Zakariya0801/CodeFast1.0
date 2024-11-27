package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import application.Admin.Admin;
import application.Industry.Industry;
import application.Industry.IndustryInformation;
import application.Student.Evaluation;
import application.Student.Student;
import application.Student.StudentInformation;
import application.Student.Contest.Contest;
import application.Student.Contest.ContestDescription;
import application.Student.Contest.Problem;
import application.Student.Course.Course;
import application.Student.Course.CourseDescription;
import application.Student.Course.MCQ;
import application.Student.Course.Quiz;
import application.Student.Course.StudyMaterial;
import databasepackage.*;

public class InformationExpert {
	private HashMap<String,Student> students;
	private HashMap<String,StudentInformation> studentInformation;
	private HashMap<String,Industry> industries;
	private HashMap<String,IndustryInformation> industryInformations;
	private Admin admin;
	private UserInformation adminInformation;
	private HashMap<String,Contest> contests;
	private HashMap<String,ContestDescription> contestDescriptions;
	private HashMap<String,Course> courses;
	private HashMap<String,CourseDescription> courseDescriptions;
	private HashMap<String,Problem> problems;
	private HashMap<String,Quiz> allQuizes;
	private HashMap<String, HashMap<String,Evaluation>> studentEvaluations;
	private static InformationExpert instance = null;
	private InformationExpert() throws SQLException {
		students = new HashMap<>();//done
		studentInformation = new HashMap<>();//done
		industries  = new HashMap<>();//done
		industryInformations = new HashMap<>();//done
		this.admin = new Admin("AD_1");//done
		contests = new HashMap<>();
		contestDescriptions = new HashMap<>();
		courses = new HashMap<>();//done
		courseDescriptions = new HashMap<>();//done
		studentEvaluations = new HashMap<>();
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		ResultSet rs = null;
		
		rs = handle.ExecuteQuery("Select * FROM Admin;", null, null);
		if(rs != null && rs.next()) {
			adminInformation = new UserInformation(
					rs.getString("Admin_id"),rs.getString("Name"),
					rs.getString("email"),rs.getString("Password")
					);
		}
		HashMap<String,HashMap<String,Course>> registeredC = new HashMap<>();
		HashMap<String,HashMap<String,Course>> pendingregisteredC = new HashMap<>();
		
		rs = handle.ExecuteQuery("Select * from Students;", null, null);
		while(rs != null && rs.next()) {
			registeredC.put(rs.getString(1), new HashMap<>());
			pendingregisteredC.put(rs.getString(1), new HashMap<>());
			studentEvaluations.put(rs.getString(1), new HashMap<>());
			studentInformation.put(rs.getString(1),new StudentInformation(
					rs.getString("Student_id"),rs.getString("Name"),
					rs.getString("Email"),rs.getString("Password"),
					rs.getFloat("CGPA"),rs.getFloat("SPerformance"),
					PersistantFactory.getInstance().getUserPlan(rs.getInt("SubscribedPlan")),
					rs.getString("degree"),rs.getInt("Status") == 1,registeredC.get(rs.getString(1)),
					studentEvaluations.get(rs.getString(1)),pendingregisteredC.get(rs.getString(1))
					));
			studentInformation.get(rs.getString(1)).getPlan().setRequested(rs.getInt("SubscribedPlan") == 0);
			students.put(rs.getString(1),new Student( rs.getString(1)));
			
		}
		HashMap<String,HashMap<String,Student>> EnrolledStudents,PendingStudents,RejectedStudents,RequestedStudents;
		EnrolledStudents = new HashMap<>();
		PendingStudents = new HashMap<>();
		RejectedStudents = new HashMap<>();
		RequestedStudents = new HashMap<>();
		rs = handle.ExecuteQuery("Select * from EvaluateStudentQuiz;", null, null);
		while(rs != null && rs.next()) {
			studentEvaluations.get(rs.getString("S_id")).put(rs.getString("Quiz_id"), 
									new Evaluation(rs.getString("Quiz_id"),rs.getInt("score")));
		}
		rs = handle.ExecuteQuery("Select * from EvaluateStudentContest;", null, null);
		while(rs != null && rs.next()) {
			studentEvaluations.get(rs.getString("S_id")).put(rs.getString("Contest_Id"), 
									new Evaluation(rs.getString("Contest_Id"),rs.getInt("score")));
		}
		rs = handle.ExecuteQuery("Select * from Industry;", null, null);
		while(rs != null && rs.next()) {
			industries.put(rs.getString(1),new Industry(rs.getString(1)));
			EnrolledStudents.put(rs.getString(1), new HashMap<>());
			PendingStudents.put(rs.getString(1), new HashMap<>());
			RejectedStudents.put(rs.getString(1), new HashMap<>());
			RequestedStudents.put(rs.getString(1), new HashMap<>());
			industryInformations.put(rs.getString(1), new IndustryInformation(
					rs.getString("Industry_id"),rs.getString("Name"),
					rs.getString("Email"),rs.getString("Password"),
					rs.getString("Description"),EnrolledStudents.get(rs.getString(1)),
					PendingStudents.get(rs.getString(1)),RejectedStudents.get(rs.getString(1)),
					RequestedStudents.get(rs.getString(1))
					));
		}
		rs = handle.ExecuteQuery("Select * from StudentIndustry;", null, null);
		while(rs!=null && rs.next()) {
			if(rs.getInt("status") == 1) 
				EnrolledStudents.get(rs.getString("I_id")).put(rs.getString("S_id"), students.get(rs.getString("S_id")));
			else if(rs.getInt("status") == -1) 
				RejectedStudents.get(rs.getString("I_id")).put(rs.getString("S_id"), students.get(rs.getString("S_id")));
			else {
				if(rs.getInt("request") == 0) 
					PendingStudents.get(rs.getString("I_id")).put(rs.getString("S_id"), students.get(rs.getString("S_id")));
				else if(rs.getInt("request") == 1)
					RequestedStudents.get(rs.getString("I_id")).put(rs.getString("S_id"), students.get(rs.getString("S_id")));
			}
		}
		// Study Materials
		List<StudyMaterial> studymaterialsData = new ArrayList<>();
		studymaterialsData.add(new StudyMaterial("Data Structures Notes","https://www.cet.edu.in/noticefiles/280_DS%20Complete.pdf"));
		studymaterialsData.add(new StudyMaterial("Data Structures Youtube Channel","https://www.youtube.com/watch?v=0IAPZzGSbME&list=PLAXnLdrLnQpRcveZTtD644gM9uzYqJCwr"));
		List<StudyMaterial> studymaterialsAlgo = new ArrayList<>();
		studymaterialsAlgo.add(new StudyMaterial("Algorithms Notes","https://vssut.ac.in/lecture_notes/lecture1428551222.pdf"));
		studymaterialsAlgo.add(new StudyMaterial("Algorithms Youtube Channel","https://www.youtube.com/watch?v=u8JZ9gU5o4g&list=PLxCzCOWd7aiHcmS4i14bI0VrMbZTUvlTa"));
		HashMap<String,HashMap<String,Quiz>> hashmap = new HashMap<>();
		allQuizes = new HashMap<>();
		hashmap.put("C_1", new HashMap<>());
		hashmap.put("C_2", new HashMap<>());
		courses.put("C_1",new Course("C_1"));
		courses.put("C_2",new Course("C_2"));
		rs = handle.ExecuteQuery("Select * from CourseRegistration;", null, null);
		while(rs != null && rs.next()) {
			if(rs.getInt("Status") == 1)
				registeredC.get(rs.getString("S_id")).put(rs.getString("C_id"),courses.get(rs.getString("C_id")));
			else
				pendingregisteredC.get(rs.getString("S_id")).put(rs.getString("C_id"),courses.get(rs.getString("C_id")));
		}
		HashMap<String, List<MCQ>> hashmapmcq = new HashMap<>();
		hashmapmcq.put("Quiz_1", new ArrayList<>());
		hashmapmcq.put("Quiz_2", new ArrayList<>());
		hashmapmcq.put("Quiz_3", new ArrayList<>());
		hashmapmcq.put("Quiz_4", new ArrayList<>());
		rs = handle.ExecuteQuery("Select * from Questions;", null, null);
		List<MCQ> currentList = null;
		while(rs != null && rs.next()) {
			currentList = hashmapmcq.get(rs.getString("Q_Id"));
			currentList.add(new MCQ(rs.getInt("QuestionNo")
					,null,null,rs.getString("Solution")
					));
		}
		rs = handle.ExecuteQuery("SELECT * from Quiz;", null, null);
		while(rs != null && rs.next()) {
			currentList = hashmapmcq.get(rs.getString("Quiz_Id"));
			HashMap<String,Quiz> temp = hashmap.get(rs.getString("C_id"));
			Quiz q = new Quiz(rs.getString("Quiz_id"),currentList);
			allQuizes.put(q.getQuizID(), q);
			temp.put(rs.getString("Quiz_Id"),q);
		}
		courseDescriptions.put("C_1", new CourseDescription("C_1",studymaterialsData,hashmap.get("C_1"),"Data Structure"));		
		courseDescriptions.put("C_2", new CourseDescription("C_2",studymaterialsAlgo,hashmap.get("C_2"),"Design & Analysis of Algorithm"));
		
		problems = new HashMap<>();
		rs = handle.ExecuteQuery("SELECT * from Problem;", null, null);
		while(rs != null && rs.next()) {
			problems.put(rs.getString("Contest_id"),new Problem(rs.getString("P_id"),rs.getString("Statement"),rs.getString("TestCaseInput")
						,rs.getString("TestCaseOutput"),rs.getInt("TimeLimit"),rs.getInt("MemoryLimit")));
		}
		rs = handle.ExecuteQuery("SELECT * from Contest;", null, null);
		while(rs != null && rs.next()) {
			contests.put(rs.getString("Contest_id"),new Contest(rs.getString("Contest_id")));
			contestDescriptions.put(rs.getString("Contest_id"), new ContestDescription(
					rs.getString("Contest_id"),problems.get(rs.getString("Contest_id")),
					rs.getString("Name"),rs.getString("StartDate"),rs.getString("EndDate"),
					rs.getInt("TotalMarks"),rs.getInt("isLive") == 0
					));
		}
	}
	public static synchronized InformationExpert getInstance() throws SQLException {
		if(instance == null) instance = new InformationExpert();
		return instance;
	}
	public void removeStudent(String StudentID) {
		if(students.containsKey(StudentID)) students.remove(StudentID);
		if(studentInformation.containsKey(StudentID)) studentInformation.remove(StudentID);
		if(studentEvaluations.containsKey(StudentID)) studentEvaluations.remove(StudentID);
		for (Map.Entry<String, IndustryInformation> entry : industryInformations.entrySet())
		{
			if(entry.getValue().getEnrolledStudents().containsKey(StudentID))
				entry.getValue().getEnrolledStudents().remove(StudentID);
			if(entry.getValue().getRejectedStudents().containsKey(StudentID))
				entry.getValue().getRejectedStudents().remove(StudentID);
			if(entry.getValue().getRequestedStudents().containsKey(StudentID))
				entry.getValue().getRequestedStudents().remove(StudentID);
			if(entry.getValue().getPendingStudents().containsKey(StudentID))
				entry.getValue().getPendingStudents().remove(StudentID);
		}
	}
	public int getNumberofQuizEvaluations() {
		int ans = 0;
		for (Map.Entry<String, HashMap<String,Evaluation>> entry : studentEvaluations.entrySet())
			for(Map.Entry<String, Evaluation> entry1 : entry.getValue().entrySet()) {
				ans += (entry1.getValue().getEvalID().substring(0,4).equalsIgnoreCase("quiz")) ? 1 : 0;
			}
		return ans;
	}
	public int getNumberofContestEvaluations() {
		int ans = 0;
		for (Map.Entry<String, HashMap<String,Evaluation>> entry : studentEvaluations.entrySet())
			for(Map.Entry<String, Evaluation> entry1 : entry.getValue().entrySet()) {
				ans += (entry1.getValue().getEvalID().substring(0,4).equalsIgnoreCase("cont")) ? 1 : 0;
			}
		return ans;
	}
	public void addEvaluation(String S_id, String evalID, int score) {
		HashMap<String,Evaluation> eval = studentEvaluations.get(S_id);
		if(eval.containsKey(evalID)) {
			System.out.println("Error");
			return;
		}
		eval.put(evalID, new Evaluation(evalID,score));
	}
	public void addStudentInformation(String StudentID, String Name,String email,String pass, float cgpa, String degree) throws SQLException {
		studentEvaluations.put(StudentID, new HashMap<>());
		studentInformation.put(StudentID,new StudentInformation(
				StudentID,Name,email,pass,cgpa,0,
				PersistantFactory.getInstance().getUserPlan(0),
				degree,false,new HashMap<>(),
				studentEvaluations.get(StudentID),new HashMap<>()
				));
	}
	public void setAsPending(String IndustryID, Student s) {
		this.getIndustryInformation(IndustryID).setAsPending(s.ID, s);
	}
	public void removeStudent(String IndustryID, String StudentID) {
		this.getIndustryInformation(IndustryID).getEnrolledStudents().remove(StudentID);
	}
	public void setAsRequested(String IndustryID, String StudentID) {
		this.getIndustryInformation(IndustryID).setAsRequested(StudentID, this.getStudent(StudentID));
	}
	public void setAsRejected(String IndustryID, String StudentID) {
		this.getIndustryInformation(IndustryID).setAsRejected(StudentID, this.getStudent(StudentID));
	}
	public void setAsAccepted(String IndustryID, String StudentID) {
		this.getIndustryInformation(IndustryID).setAsAccepted(StudentID, this.getStudent(StudentID));
	}
	public int NumberOfStudents() {return students.size();}
	public int NumberOfindustry() {return industries.size();}
	public int NumberOfCourses() {return courses.size();}
	public int NumberOfQuiz() {return allQuizes.size();}
	public int NumberOfContest() {return contests.size();}
	public UserInformation getUser(String ID) {
		if(studentInformation.containsKey(ID)) 
			return studentInformation.get(ID);
		if(industryInformations.containsKey(ID)) 
			return industryInformations.get(ID);
		if(adminInformation.ID.equals(ID)) 
			return adminInformation;
		return null;
	}
	public List<Student> getRegisteredStudents(){
		List<Student> ls = new ArrayList<>();
		studentInformation.forEach((key, value) -> {
			if(value.isRegistered()) {
				ls.add(students.get(key));
			}
		});
		return ls;
	}
	public Student getStudent(String StudentID) {
		return students.get(StudentID);
	}
	public Industry getIndustry(String IndusID) {
		return industries.get(IndusID);
	}
	public Admin getAdmin() {
		return admin;
	}
	public UserInformation getAdminInfo() {
		return adminInformation;
	}
	public StudentInformation getStudentInformation(String StudentID) {
		return studentInformation.get(StudentID);
	}
	public IndustryInformation getIndustryInformation(String IndusID) {
		return industryInformations.get(IndusID);
	}
	public void print() {
		students.forEach((key, value) -> {
			System.out.println("Student = " + value.ID);
		});
		studentInformation.forEach((key, value) -> {
	            System.out.println("Key: " + key);
	            value.print();
	    });
	}
	public Contest getContest(String ContestID) {
		return contests.get(ContestID);
	}
	public ContestDescription getContestDescription(String ContestID) {
		return contestDescriptions.get(ContestID);
	}
	public Course getCourse(String CourseID) {
		return courses.get(CourseID);
	}
	public CourseDescription getCourseDescription(String CourseID) {
		return courseDescriptions.get(CourseID);
	}
	public void addStudent(Student s, StudentInformation si) {
		if(s == null || si == null) return;
		if(s.ID != si.ID) return;
		if(students.containsKey(s.ID)) return;
		students.put(s.ID, s);
	}
	public void addContest(Contest c, Problem P) throws SQLException {
		if(c == null || P == null) return;
		if(contests.containsKey(c.contestId)) return;
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		List<String> values = new ArrayList<>();
		List<String> types = new ArrayList<>();
		values.add(c.contestId);types.add("string");
		ResultSet rs = handle.ExecuteQuery("Select * from Contest where Contest_id = ?", values, types);
		addToLocal(c,P,rs);
	}
	public void addToLocal(Contest c, Problem P,ResultSet rs) throws SQLException {
		if(rs!= null && rs.next()) {			
			contests.put(c.contestId, c);
			problems.put(c.contestId, P);
			contestDescriptions.put(c.contestId, new ContestDescription(
					rs.getString("Contest_id"),P,
					rs.getString("Name"),rs.getString("StartDate"),rs.getString("EndDate"),
					rs.getInt("TotalMarks"),rs.getInt("isLive") == 0
					));
		}
	}
}
