package application.Student;

public class PremiumPlan extends Plan {
	public PremiumPlan()
	{
		super(10);
	}
	public boolean isPremium()
	{
		return true;
	}
	public String getStudyLink() {
		return "/fxmlFiles/studentfxml/StudyMaterial.fxml";
	}
}
