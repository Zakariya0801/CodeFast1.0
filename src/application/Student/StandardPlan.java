package application.Student;

public class StandardPlan extends Plan {
	public StandardPlan()
	{
		super(5);
	}
	public boolean isPremium()
	{
		return false;
	}
	public String getStudyLink() {
		return "/fxmlFiles/studentfxml/StudyMaterialNotFound.fxml";
	}
}
