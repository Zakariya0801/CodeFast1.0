package application.Admin;

import java.sql.ResultSet;

import application.PersistantFactory;
import controllers.TablesData.StudentMainData;
import databasepackage.*;

public class StudentRegistrationRequest extends Request<StudentMainData> {
    @Override
    protected void loadData() {
        try {
            DBHandler handle = PersistantFactory.getInstance().getDatabase();
            ResultSet rs = handle.ExecuteQuery("SELECT * from Students where Status=0;", null, null);
            while (rs != null && rs.next()) {
                requestData.add(new StudentMainData(
                        rs.getString("Student_id"),
                        rs.getString("Name"),
                        rs.getString("email"),
                        rs.getString("degree"),
                        Float.toString(rs.getFloat("CGPA"))
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
