import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
    @author alexchristopher
 */

public class StudentQueries {
    private static Connection connection;
    private static ResultSet resultSet;
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    
    public static void addStudent(StudentEntry student){
        connection = DBConnection.getConnection();
        
        try {
            addStudent = connection.prepareStatement(
                    "INSERT INTO app.StudentEntry " +
                    "(StudentID, FirstName, LastName) " +
                    "VALUES (?, ?, ?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            
            addStudent.executeUpdate();
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<StudentEntry> getAllStudents(){
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> allStudents = new ArrayList<StudentEntry>();
        
        try{
            getAllStudents = connection.prepareStatement(
                    "SELECT * FROM app.StudentEntry ORDER BY LastName, FirstName, StudentID");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next()){
                allStudents.add(new StudentEntry(
                    resultSet.getString("StudentID"),
                    resultSet.getString("FirstName"), 
                    resultSet.getString("LastName")
                ));
            }
            
            return allStudents;
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }
    
    /*
    public static StudentEntry getStudent(String studentID){
        
    }
    
    public static void dropStudent(String studentID){
        
    }
    */
}
