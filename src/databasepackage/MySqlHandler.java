package databasepackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import controllers.TablesData.StudentData;

public class MySqlHandler implements DBHandler {
	public String url="";
	public String username = "";
	public String password = "";
	public Connection connection = null;
	private static MySqlHandler instance  = null;
	private MySqlHandler()
	{
		url = "jdbc:mysql://localhost:3306/CodeFast";
		username = "root";
		password = "12345678";
//		url = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12746689"; // Corrected URL
//		username = "sql12746689";
//		password = "mFnf5d4hlt";

		try  {
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Database connected!");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static MySqlHandler getHandler() {
		if(instance == null) instance = new MySqlHandler();
		return instance;
	}
	
	@Override
	public void ExecuteCreate(String Table,String id, StudentData st) {
		String sql = "INSERT INTO " + Table +  " (Student_Id, Name, Password, CGPA, Degree, Email, SPerformance)\n"
				+ "VALUES (?, ?, ?, ?, ?, ?, 0.0);\n";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			statement.setString(2, st.getName());
			statement.setString(3, st.getPass());
			statement.setFloat(4, st.getCgpa());
			statement.setString(5, st.getDegree());
			statement.setString(6, st.getEmail());
			int x = statement.executeUpdate();
			if(x == 1) 
				System.out.println("Successfully Added");
			else System.out.println("Unsuccessful"); 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public ResultSet ExecuteQuery(String Query,List<String> values, List<String> type) {
		try {
			PreparedStatement statement = connection.prepareStatement(Query);
			int i=1;
			if(values != null)
				for(String str: values) {
					if(type.get(i-1).equalsIgnoreCase("float")) {
						statement.setFloat(i, Float.parseFloat(str));
					}
					else if(type.get(i-1).equalsIgnoreCase("string")) {
						statement.setString(i, str);
					}
					else if(type.get(i-1).equalsIgnoreCase("int")) {
						statement.setInt(i, Integer.parseInt(str));
					}
					i++;
				}
			return statement.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void ExecuteUpdate(String Query,List<String> values, List<String> type) {
		try {
			PreparedStatement statement = connection.prepareStatement(Query);
			int i=1;
			if(values != null)
				for(String str: values) {
					if(type.get(i-1).equalsIgnoreCase("float")) {
						statement.setFloat(i, Float.parseFloat(str));
					}
					else if(type.get(i-1).equalsIgnoreCase("string")) {
						statement.setString(i, str);
					}
					else if(type.get(i-1).equalsIgnoreCase("int")) {
						statement.setInt(i, Integer.parseInt(str));
					}
					i++;
				}
			statement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public ResultSet ExecuteQuery(String Table, String Condition) {
		String sql = "Select * from " + Table + " where " + Condition;
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			return statement.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void CloseConnection() throws SQLException {
//		connection.close();
	}

}
