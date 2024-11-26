package application.Student.Contest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.PersistantFactory;
import controllers.TablesData.UpComingContest;
import databasepackage.DBHandler;
import databasepackage.MySqlHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Contest {
	private static int IDCount = 0;
	public String contestId;
	public static void SetCount(int c) {
		IDCount = c;
	}
	public Contest(String name, String start, String end) throws SQLException { 
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		contestId = "Contest_" + Integer.toString(IDCount);
    	List<String> values = new ArrayList<>();
    	List<String> types = new ArrayList<>();
    	values.add(contestId);types.add("string");
    	IDCount++;
    	values.add(name);types.add("string");
    	values.add(start);types.add("string");
    	values.add(end);types.add("string");
    	handle.ExecuteUpdate("INSERT INTO Contest (Contest_Id, Name, TotalMarks, StartDate, EndDate,isLive) "
    			+ "VALUES (?, ?, 20, ?, ?,0);", values, types);
    	handle.CloseConnection();
	}
	public Contest(String id) {
		contestId = id;
	}
	public static void setContestsTable(List<TableColumn<UpComingContest, String>> columns, TableView<UpComingContest> ContestTable) throws SQLException {
		if(columns == null) return;
		columns.get(0).setCellValueFactory(new PropertyValueFactory<UpComingContest, String>("Contest_ID"));
		columns.get(1).setCellValueFactory(new PropertyValueFactory<UpComingContest, String>("Contest_Name"));
		columns.get(2).setCellValueFactory(new PropertyValueFactory<UpComingContest, String>("StartDate"));
		columns.get(3).setCellValueFactory(new PropertyValueFactory<UpComingContest, String>("EndDate"));
		DBHandler handle = PersistantFactory.getInstance().getDatabase();
		
		ResultSet rs = handle.ExecuteQuery("Select Contest_Id,Name,StartDate,EndDate from contest;", null,null);
		while(rs.next()) {
			ContestTable.getItems().add(new UpComingContest(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
		}
	}
}
