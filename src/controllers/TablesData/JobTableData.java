package controllers.TablesData;

public class JobTableData {
	public String Company_ID;
	public String Company_Name;
	public String Description;
	public String JobType;
	public JobTableData(String Company_id, String company_Name, String description, String jobType) {
		Company_ID = Company_id;
		Company_Name = company_Name;
		Description = description;
		JobType = jobType;
	}
	
	public String getCompany_ID() {
		return Company_ID;
	}

	public void setCompany_ID(String company_ID) {
		Company_ID = company_ID;
	}

	public String getCompany_Name() {
		return Company_Name;
	}
	public void setCompany_Name(String company_Name) {
		Company_Name = company_Name;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getJobType() {
		return JobType;
	}
	public void setJobType(String jobType) {
		JobType = jobType;
	}
	
}
