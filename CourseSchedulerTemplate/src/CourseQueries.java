import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
   @author alexchristopher
 */
public class CourseQueries {
    private static Connection connection;
    //private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement getAllCourses;
    private static PreparedStatement addCourse;
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement getCourseSeats;
    private static PreparedStatement getDropCourse;
    private static ResultSet resultSet;
    
    public static ArrayList<CourseEntry> getAllCourses(String semester){
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> allCourses = new ArrayList<CourseEntry>();
        
        try{      
            getAllCourses = connection.prepareStatement(
                "SELECT * FROM app.CourseEntry WHERE Semester = ? ORDER BY Semester, CourseCode");
            getAllCourses.setString(1, semester);
            resultSet = getAllCourses.executeQuery();
            while(resultSet.next()){
                allCourses.add(new CourseEntry(
                        resultSet.getString("Semester"),
                        resultSet.getString("CourseCode"),
                        resultSet.getString("Description"),
                        resultSet.getInt("Seats")
                ));
            }
            return allCourses;
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }
    
    public static void addCourse(CourseEntry course){
        connection = DBConnection.getConnection();
        
        try{
            addCourse = connection.prepareStatement(
                    "INSERT INTO app.CourseEntry " +
                    "(Semester, CourseCode, Description, Seats) " + 
                    "VALUES (?, ?, ?, ?)");
            
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getDescription());
            addCourse.setInt(4, course.getSeats());
            
            addCourse.executeUpdate();
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester){
        connection = DBConnection.getConnection();
        ArrayList<String> allCourseCodes = new ArrayList<String>();
        
        try {
            getAllCourseCodes = connection.prepareStatement(
                    "SELECT * FROM app.CourseEntry WHERE Semester = ? ORDER By CourseCode");
            
            getAllCourseCodes.setString(1, semester);
            resultSet = getAllCourseCodes.executeQuery();
            
            while(resultSet.next()){
                allCourseCodes.add(resultSet.getString(0));
            }
            
            return allCourseCodes;
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }
    
    public static int getCourseSeats(String semester, String courseCode){
        connection = DBConnection.getConnection();
        
        try {
            getCourseSeats = connection.prepareStatement(
                    "SELECT Seats FROM app.CourseEntry " + 
                    "WHERE Semester = ? AND CourseCode = ? ");
            
            getCourseSeats.setString(1, semester);
            getCourseSeats.setString(2, courseCode);
            resultSet = getCourseSeats.executeQuery();
            
            /*while (resultSet.next()){
                return resultSet.getInt("Seats");
            }*/
            resultSet.next();
            return resultSet.getInt("Seats");
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
            return 0;
        }
    }
    
    /*
    public static void dropCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        
        try {
            getDropCourse = connection.prepareStatement(
                    "SELECT * FROM app.CourseEntry " + 
                    "WHERE Semester == ? AND CourseCode == ? " + 
                    "ORDER By CourseCode");
            
            getDropCourse.setString(1, semester);
            getDropCourse.setString(2, courseCode);
            resultSet = getDropCourse.executeQuery();
            
            resultSet.deleteRow();
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    */
}
