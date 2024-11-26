package application.Student.Course;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.InformationExpert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class Course {
	String courseID;
	public Course(String Id ) {
		// TODO Auto-generated constructor stub
		courseID = Id;
	}
	public Course(Course c) {
		this.courseID = c.courseID;
	}
	//Accesors and Mutators
	public String getCourseID() {
		return courseID;
	}
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	
	public CourseDescription getDescription() throws SQLException {
		//apply DB actions if any
		return InformationExpert.getInstance().getCourseDescription(courseID);
	}
	List<String> SelectedOptions( List<ToggleGroup> radios){
		List<String> options = new ArrayList<>();
		for(ToggleGroup g : radios) {
			RadioButton selectedRadioButton = (RadioButton) g.getSelectedToggle();
			
		    if (selectedRadioButton != null) {
		        String selectedText = selectedRadioButton.getText(); // Get the text of the selected RadioButton
		        options.add(selectedText.substring(0, 1));
		    } else {
		        System.out.println("No option selected.");
		    }
		}
		return options;
	}
	public int calculateScore(String quiz,  List<ToggleGroup> radios) throws SQLException {
		List<MCQ> mcq = this.getDescription().getQuiz().get(quiz).getMcqs();
		List<String> options = SelectedOptions(radios);
		int score = 0;
		for(int i=0 ; i<5 ; i++) {
			String correct = mcq.get(i).getCorrectOption();
			score += (correct.equals(options.get(i)) ? 1 : 0);
		}
		return score;
	}
}
