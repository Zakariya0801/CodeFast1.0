package application.Admin;
import java.sql.ResultSet;

import application.PersistantFactory;
import controllers.TablesData.CourseRequest;
import databasepackage.DBHandler;
import databasepackage.MySqlHandler;
public class CourseRegistrationRequest extends Request<CourseRequest> {
    @Override
    protected void loadData() {
        try {
            DBHandler handle = PersistantFactory.getInstance().getDatabase();
            ResultSet rs = handle.ExecuteQuery(
                    "SELECT S_id, Students.Name, C_id, CName " +
                            "FROM CourseRegistration " +
                            "INNER JOIN Course ON Course_id = C_id " +
                            "INNER JOIN Students ON S_id = Student_id " +
                            "WHERE CourseRegistration.Status = 0;", 
                    null, null);
            while (rs != null && rs.next()) {
                requestData.add(new CourseRequest(
                        rs.getString("S_id"),
                        rs.getString("Name"),
                        rs.getString("C_id"),
                        rs.getString("CName")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
