package BackEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.NonWritableChannelException;
import java.security.spec.ECPrivateKeySpec;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;

import SharedObjects.Assignment;
import SharedObjects.Course;
import SharedObjects.InfoExchange;
import SharedObjects.StudentEnrollment;
import SharedObjects.User;

public class DataBaseManager implements Runnable {
	private ObjectOutputStream writeobject;
	private ObjectInputStream readobject;
	private PreparedStatement statement;
	private Connection jdbc_connection;
	public static String CONNECTIONINFO = "jdbc:mysql://localhost:3306/project",  
						 LOGIN          = "root",
						 PASSWORD       = "huzaifa147";
	public static String COURSETABLE = "Courses";
	public static String USERTABLE = "Users";
	public static String STUDENTENROLLMENTTABLE = "Student_Enrollment";
	public static String ASSIGNMENTTABLE = "Assignment_Table";
	private int id;
	private User user;
	
	public DataBaseManager(Socket socket) throws IOException, SQLException, ClassNotFoundException {
		writeobject=new ObjectOutputStream(socket.getOutputStream());
		readobject=new ObjectInputStream(socket.getInputStream());
		Class.forName("com.mysql.jdbc.Driver");
		jdbc_connection = DriverManager.getConnection(CONNECTIONINFO, LOGIN, PASSWORD);
		id=3000;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				InfoExchange infoExchange=(InfoExchange) readobject.readObject();
				System.out.println("Recieved object of infoexchange");
				String string=infoExchange.getOpcode();
				System.out.println("Now in checkOperation");
				
				if (string.equals("Login Attempt"))
				{
					System.out.println("in login attempt databasemangaer");
					User user= (User) readobject.readObject();
					String[] listOfUsers=user.findUser(USERTABLE, jdbc_connection, statement);
					infoExchange.setInfo(listOfUsers);
					writeobject.writeObject(infoExchange);
					flushAndReset(writeobject);
				}
				
				else if (string.equals("View Courses Proff")){
					Course course=(Course)readobject.readObject();
					System.out.println("course object read");
					//infoExchange=new InfoExchange(course.browseCourses(COURSETABLE, jdbc_connection, statement));
					infoExchange.setInfo(course.browseCourses(COURSETABLE, jdbc_connection, statement));
					writeobject.writeObject(infoExchange);
					flushAndReset(writeobject);
				}
				
				else if (string.equals("Create Course Proff"))
				{
					Course course=(Course)readobject.readObject();
					course.createCourse(COURSETABLE, jdbc_connection, statement, id);
				}
				
				else if (string.equals("Course Activation Status"))
				{
					Course course=(Course)readobject.readObject();
					course.courseActivationStatus(COURSETABLE, jdbc_connection, statement);
				}
				
				else if(string.equals("Login Attempt")) {
					User user = (User)readobject.readObject(); 
				}
				
				else if (string.equals("Search Students Proff"))
				{
					User user = (User)readobject.readObject(); 
					String[] listOfStudents=new String[5];
					listOfStudents=user.searchStudents(USERTABLE, jdbc_connection, statement);
					System.out.println(listOfStudents[0]);
					infoExchange.setInfo(listOfStudents);
					writeobject.writeObject(infoExchange);
					flushAndReset(writeobject);
				}
				
				else if (string.equals("Student Enrollment Proff"))
				{
					StudentEnrollment enrollments= (StudentEnrollment) readobject.readObject();
					String[] listOfEnrollments=enrollments.viewStudentsEnrolled(STUDENTENROLLMENTTABLE, jdbc_connection, statement);
					infoExchange.setInfo(listOfEnrollments);
					writeobject.writeObject(infoExchange);
					flushAndReset(writeobject);
				}
				
				else if (string.equals("View Students Proff"))
				{
					StudentEnrollment enrollments= (StudentEnrollment) readobject.readObject();
					int[] studentIDs=enrollments.viewStudents(STUDENTENROLLMENTTABLE, jdbc_connection, statement);
					ArrayList<String> list=new ArrayList<String>();
					for (int i=0; i<studentIDs.length; i++) {
						User user=new User(studentIDs[i], null, null, null, null, "S");
						String[] listOfStudents=user.searchStudentsUserId(USERTABLE, jdbc_connection, statement);
						//System.out.println(strings[2]);
						list.add(listOfStudents[2]);
					}
					
					String[] temp=new String[list.size()];
					for (int i=0; i<list.size(); i++) {
						temp[i]=list.get(i);
					}
					infoExchange.setInfo(temp);
					writeobject.writeObject(infoExchange);
					flushAndReset(writeobject);
				}
				
				else if (string.equals("View Assignment Proff"))
				{
					//DOWNLOAD ASSIGNMENTS AS WELL
					Assignment assignment= (Assignment) readobject.readObject();
					String[] result=assignment.searchAssignment(ASSIGNMENTTABLE, jdbc_connection, statement);
					infoExchange.setInfo(result);
					writeobject.writeObject(infoExchange);
					flushAndReset(writeobject);
				}
				
				else if (string.equals("Upload Assignment Proff"))
				{
					Assignment assignment = (Assignment) readobject.readObject();
					id+=10;
					assignment.addAssignments(ASSIGNMENTTABLE, jdbc_connection, statement, id);
				}
				
				else if (string.equals("Student Downloading Assignment"))
				{
					System.out.println("Download assignmenets and studd in database manger");
					Assignment assignment=(Assignment) readobject.readObject();
					String[] fileinfo=assignment.downloadAssignment(ASSIGNMENTTABLE, jdbc_connection, statement);
					FileHandler fileHandler=new FileHandler(fileinfo[0], fileinfo[1]);
					fileHandler.downloadAssignmentToStudent();
				}
				
				else if (string.equals("Student Uploading Assignment"))
				{
					
				}
				
				else if (string.equals("Get List of Courses Student"))
				{
					StudentEnrollment studentEnrollment=(StudentEnrollment) readobject.readObject();
					int[] courses_student=studentEnrollment.viewCoursesForStudent(STUDENTENROLLMENTTABLE, jdbc_connection, statement);
					Course course=null;
					ArrayList<String> listofcourses=new ArrayList<String>();
					for (int i=0; i<courses_student.length; i++) {
						course=new Course(-1, null, -1, courses_student[i]);
						String coursename=course.courseofStudent(COURSETABLE, jdbc_connection, statement);
						if (coursename!=null)
							listofcourses.add(coursename);
					}
					String[] result=new String[listofcourses.size()];
					for (int i=0; i<listofcourses.size(); i++) {
						System.out.println(listofcourses.get(i));
						result[i]=listofcourses.get(i);
					}
					infoExchange.setInfo(result);
					writeobject.writeObject(infoExchange);
					flushAndReset(writeobject);
				}
				
				else if (string.equals("Send an Email to the Proff"))
				{
					//String[] email=infoExchange.getInfo()
					User user=new User(1000, null, null, null, null, "S"); //userid pulled from joptionpane
					String[] emailinfostudent=user.getEmailInfoStudent(USERTABLE, jdbc_connection, statement);
					Course course=new Course(-1, null, 1, 1070); //course id from infoexchange string array
					int proffid=course.getProffID(COURSETABLE, jdbc_connection, statement);
					user=new User(proffid, null, null, null, null, "P");
					String[] proffemail=user.getEmailInfoProff(USERTABLE, jdbc_connection, statement);
					EmailHandler emailHandler=new EmailHandler("amarhuzaifa@gmail.com", "Huzaifa@147", "daniel.guieb2@gmail.com", "SubjectLine", "The email itself is: ohyeah");
					emailHandler.createEmail();
					//above first 3 parameters are emailinfostudet[0] and emailinfostudent[1] and proffsemail[0]
				}
				
				else if (string.equals("Send Email to all Students Enrolled in Course"))
				{
					System.out.println("Now sending some email");
					StudentEnrollment studentEnrollment=new StudentEnrollment(-1, -1, 1070);
					int[] studentids=studentEnrollment.viewStudents(STUDENTENROLLMENTTABLE, jdbc_connection, statement);
					//below 1030 is proffs (winstons) id
					User user=new User(1030, null, null, null, null, "P");
					String[] emailinfoproff=user.getEmailInfoProff(USERTABLE, jdbc_connection, statement);
					for (int i=0; i<studentids.length; i++) {
						user=new User(studentids[i], null, null, null, null, "S");
						String[] emailofstudent=user.getEmailInfoStudent(USERTABLE, jdbc_connection, statement);
						//below: first 2 are proff info, so emailinfoproff[0] and [1]
						//last 3 are emailofstudent[0] and from infoExchange's string array
						EmailHandler emailHandler=new EmailHandler("qazfugioshi@gmail.com", "DanielGee", "amarhuzaifa@gmail.com", "SubjectLine", "OOGA BOOGA");
						emailHandler.createEmail();
					}
				}
				id+=10;
			} 
			catch (ClassNotFoundException | IOException e) {
				System.out.println("Client has left");
				break;
			}
		}
	}
	
	private void flushAndReset(ObjectOutputStream sendObject) throws IOException {
		sendObject.flush();
		sendObject.reset();
	}
	
}	
