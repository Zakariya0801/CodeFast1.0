package application.Student.Course;

import java.util.HashMap;
import java.util.List;

public class CourseDescription {
	String courseID;
	String CourseName;
	List<StudyMaterial> studymaterial;
	HashMap<String,Quiz> quiz;
	public CourseDescription(String courseID, List<StudyMaterial> studymaterial, HashMap<String,Quiz> quiz, String name) {
		super();
		this.courseID = courseID;
		this.studymaterial = studymaterial;
		this.quiz = quiz;
		this.CourseName = name;
	}
	
	
	public String getCourseName() {
		return CourseName;
	}


	public void setCourseName(String courseName) {
		CourseName = courseName;
	}


	public String getCourseID() {
		return courseID;
	}
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	public List<StudyMaterial> getStudymaterial() {
		return studymaterial;
	}
	public void setStudymaterial(List<StudyMaterial> studymaterial) {
		this.studymaterial = studymaterial;
	}


	public HashMap<String, Quiz> getQuiz() {
		return quiz;
	}


	public void setQuiz(HashMap<String, Quiz> quiz) {
		this.quiz = quiz;
	}
	
	
}
