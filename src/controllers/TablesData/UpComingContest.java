package controllers.TablesData;

public class UpComingContest {
	public String Contest_ID;
	public String Contest_Name;
	public String StartDate;
	public String EndDate;
	
	public UpComingContest(String contest_ID, String contest_Name, String startDate, String endDate) {
		super();
		Contest_ID = contest_ID;
		Contest_Name = contest_Name;
		StartDate = startDate;
		EndDate = endDate;
	}
	public String getContest_ID() {
		return Contest_ID;
	}
	public void setContest_ID(String contest_ID) {
		Contest_ID = contest_ID;
	}
	public String getContest_Name() {
		return Contest_Name;
	}
	public void setContest_Name(String contest_Name) {
		Contest_Name = contest_Name;
	}
	public String getStartDate() {
		return StartDate;
	}
	public void setStartDate(String startDate) {
		StartDate = startDate;
	}
	public String getEndDate() {
		return EndDate;
	}
	public void setEndDate(String endDate) {
		EndDate = endDate;
	}
	
}
