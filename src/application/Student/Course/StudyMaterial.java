package application.Student.Course;
import java.io.*;
import java.util.List;

public class StudyMaterial {
	private String Name;
	private String Link;
	public StudyMaterial(String name, String link) {
		Name = name;
		Link = link;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getLink() {
		return Link;
	}
	public void setLink(String link) {
		Link = link;
	}
}
