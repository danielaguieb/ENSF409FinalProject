package DatabaseConstruction;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DataBaseBuilder
{
	private Connection jdbc_connection;
	private PreparedStatement statement;
	private String databaseName = "project";
	private String connectionInfo = "jdbc:mysql://localhost:3306/project",  
				  login          = "root",
				  password       = "huzaifa147";
	public static int id=1000;
	
	public DataBaseBuilder()
	{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			jdbc_connection = DriverManager.getConnection(connectionInfo, login, password);
			System.out.println("Connected to: " + connectionInfo + "\n");
		}
		catch(SQLException e) { e.printStackTrace(); }
		catch(Exception e) { e.printStackTrace(); }
	}
	
	public void createUserTable(String tablename)
	{
		String sql = "CREATE TABLE " + tablename + "(" +
			     "USER_ID INT(8) NOT NULL, " +
			     "PASSWORD VARCHAR(20) NOT NULL, " + 
			     "EMAIL VARCHAR(50) NOT NULL, " + 
			     "FIRSTNAME VARCHAR(30) NOT NULL, " + 
			     "LASTNAME VARCHAR(30) NOT NULL, " + 
			     "TYPE CHAR(1) NOT NULL,"+
			     "PRIMARY KEY ( user_id ))";
		try {
			statement = jdbc_connection.prepareStatement(sql);
			statement.executeUpdate();
			System.out.println("Created Table " + tablename);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void fillUserTable(String filename)
	{
		try{
			Scanner sc = new Scanner(new FileReader(filename));
			while(sc.hasNext())
			{
				String string[] = sc.nextLine().split(";");
				String sql = "INSERT INTO USERS" +
						" VALUES ( ?, ?, ?, ?, ?, ?);";
				try{
					statement = jdbc_connection.prepareStatement(sql);
					statement.setInt(1, id);
					statement.setString(2, string[0]);
					statement.setString(3, string[1]);
					statement.setString(4, string[2]);
					statement.setString(5, string[3]);
					statement.setString(6, string[4]);
					statement.executeUpdate();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
				id+=10;
			}
			sc.close();
		}
		catch(FileNotFoundException e)
		{
			System.err.println("File " + filename + " Not Found!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
		
	public void createCourseTable(String tablename)
	{
		String sql = "CREATE TABLE " + tablename + "(" +
			     "COURSE_ID INT(8) NOT NULL, " +
			     "PROFF_ID INT(8) NOT NULL, " + 
			     "NAME VARCHAR(50) NOT NULL, " + 
			     "ACTIVE INT(1) NOT NULL,"+
			     "PRIMARY KEY ( course_id ))";
		try {
			statement = jdbc_connection.prepareStatement(sql);
			statement.executeUpdate();
			System.out.println("Created Table " + tablename);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void fillCourseTable(String filename)
	{
		try{
			Scanner sc = new Scanner(new FileReader(filename));
			while(sc.hasNext())
			{
				String string[] = sc.nextLine().split(";");
				String sql = "INSERT INTO COURSES" +
						" VALUES ( ?, ?, ?, ?)";
				try{
					statement = jdbc_connection.prepareStatement(sql);
					statement.setInt(1, id);
					statement.setInt(2, 1030);
					statement.setString(3, string[0]);
					statement.setString(4, string[1]);
					statement.executeUpdate();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
				id+=10;
			}
			sc.close();
		}
		catch(FileNotFoundException e)
		{
			System.err.println("File " + filename + " Not Found!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void createStudentEnrollmentTable(String tablename)
	{		
		String sql = "CREATE TABLE " + tablename + "(" +
			     "ENROLLMENT_ID INT(8) NOT NULL, " +
			     "STUDENT_ID INT(8) NOT NULL, " +  
			     "COURSE_ID INT(8) NOT NULL,"+
			     "PRIMARY KEY ( enrollment_id ))";
		try {
			statement = jdbc_connection.prepareStatement(sql);
			statement.executeUpdate();
			System.out.println("Created Table " + tablename);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void fillStudentEnrollmentTable(String filename)
	{
		try{
//			Scanner sc = new Scanner(new FileReader(filename));
//			while(sc.hasNext())
//			{
//				String string[] = sc.nextLine().split(";");
				String sql = "INSERT INTO Student_Enrollment" +
						" VALUES ( ?, ?, ?)";
				try{
					statement = jdbc_connection.prepareStatement(sql);
					statement.setInt(1, id);
					statement.setInt(2, 1000);
					statement.setInt(3, 1080);
					statement.executeUpdate();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
				id+=10;
			//}
			//sc.close();
		}
//		catch(FileNotFoundException e)
//		{
//			System.err.println("File " + filename + " Not Found!");
//		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException
	{
		DataBaseBuilder dataBaseBuilder=new DataBaseBuilder();
		dataBaseBuilder.createUserTable("Users");
		dataBaseBuilder.createCourseTable("Courses");
		dataBaseBuilder.createStudentEnrollmentTable("Student_Enrollment");
		dataBaseBuilder.fillUserTable("Users.txt");
		dataBaseBuilder.fillCourseTable("Courses.txt");
		dataBaseBuilder.fillStudentEnrollmentTable("Student Enrollment.txt");
	}
}