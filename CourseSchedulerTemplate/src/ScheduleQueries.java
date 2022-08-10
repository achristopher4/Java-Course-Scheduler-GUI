import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
    @author alexchristopher
 */

public class ScheduleQueries {
    private static Connection connection;
    private static ResultSet resultSet;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    
    public static void addScheduleEntry(ScheduleEntry entry){
        connection = DBConnection.getConnection();
        
        try{
            addScheduleEntry = connection.prepareStatement(
                    "INSERT INTO app.ScheduleEntry " +
                    "(Semester, CourseCode, StudentID, Status, TimeStamp) " +
                    "VALUES (?, ?, ?, ?, ?)");
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getCourseCode());
            addScheduleEntry.setString(3, entry.getStudentID());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.getTimeStamp());
            
            addScheduleEntry.executeUpdate();
        } catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, 
            String studentID){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduleStudents = new ArrayList<ScheduleEntry>(); 
        
        try{
            getScheduleByStudent = connection.prepareStatement(
                    "SELECT * FROM app.ScheduleEntry " +
                    "WHERE StudentID = ? AND Semester = ? " +
                    "ORDER BY CourseCode");
            
            getScheduleByStudent.setString(1, studentID);
            getScheduleByStudent.setString(2, semester);
            
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next()){
                scheduleStudents.add(new ScheduleEntry(
                        resultSet.getString("Semester"),
                        resultSet.getString("CourseCode"),
                        resultSet.getString("StudentID"),
                        resultSet.getString("Status"),
                        resultSet.getTimestamp("TimeStamp")
                ));
            }
            
            return scheduleStudents;
        } catch(SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
    }
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode){
        connection = DBConnection.getConnection();
        
        try{
            getScheduledStudentCount = connection.prepareStatement(
                    "SELECT COUNT(*) FROM app.ScheduleEntry WHERE Semester = ? AND CourseCode = ?");
            getScheduledStudentCount.setString(1, currentSemester);
            getScheduledStudentCount.setString(2, courseCode);
            resultSet = getScheduledStudentCount.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
            return 0;
        }
    }
}
