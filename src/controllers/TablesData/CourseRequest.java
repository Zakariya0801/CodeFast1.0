package controllers.TablesData;

public class CourseRequest {
	public String Student_id;
	public String Student_name;
	public String Course_id;
	public String Course_name;
	public CourseRequest(String student_id, String student_name, String course_id, String course_name) {
		Student_id = student_id;
		Student_name = student_name;
		Course_id = course_id;
		Course_name = course_name;
	}
	public String getStudent_id() {
		return Student_id;
	}
	public void setStudent_id(String student_id) {
		Student_id = student_id;
	}
	public String getStudent_name() {
		return Student_name;
	}
	public void setStudent_name(String student_name) {
		Student_name = student_name;
	}
	public String getCourse_id() {
		return Course_id;
	}
	public void setCourse_id(String course_id) {
		Course_id = course_id;
	}
	public String getCourse_name() {
		return Course_name;
	}
	public void setCourse_name(String course_name) {
		Course_name = course_name;
	}
	
}
