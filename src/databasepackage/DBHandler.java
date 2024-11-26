package databasepackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import application.Student.Student;
import controllers.TablesData.StudentData;

public interface DBHandler {
	
    ResultSet ExecuteQuery(String Table,String Condition);
    ResultSet ExecuteQuery(String Query,List<String> values, List<String> type);
    void ExecuteUpdate(String Query,List<String> values, List<String> type);
    void ExecuteCreate(String Table, String id, StudentData st);
    void CloseConnection() throws SQLException;
}
