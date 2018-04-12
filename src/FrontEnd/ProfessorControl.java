package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ProfessorControl {
	private ProfessorModel pModel;
	private ProfessorView pView;
	
	
	public ProfessorControl(ProfessorModel model, ProfessorView view) {
		pModel=model; 
		pView=view;
		pView.addHomeListeners(new CreateCourseListener(), new ViewCourseListener(), new CourseListListener());
		pView.setVisible(true);
	}
	
	class CreateCourseListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] strings=pView.createCourse();
			if (strings!=null) {
				pModel.createCourse(pView.getProffID(), strings);
			}
		}
	}
	
	class EmailStudentListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] emailInfo=pView.sendingMail();
			pModel.sendEmailToStudents(emailInfo);
		}
	}
	
	class ViewAssignmentsListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Browsing all assingments in course");
			String[] assignments=pModel.viewAssign(pView.getCourseID());
			pView.updateAssignListDisplay(assignments);
			pView.viewAssignsPage();
		}
	}
	
	class ViewStudentsListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] courseInfo = pView.getCourseInfo();
			int courseid = Integer.parseInt(courseInfo[0]);
			String[] students=pModel.viewStudents(courseid);
			pView.updateStudentListDisplay(students);
			pView.viewStudentsPage();
		}
	}
	
	class UploadAssignmentListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			File file=pView.chooseFile();
			System.out.println(file.getPath());
			pModel.addAssignment(pView.getCourseID(), file.getName(), file.getPath());
		}
		
	}
	
	class AssignmentActiveStatusListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class ViewSubmissionsListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	class ReturnHomeListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("PRESSED HOME");
			pView.viewHomePage();
			
		}
		
	}
	
	class ViewCourseListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] courselist=pModel.viewCourse(pView.getProffID());
//			pView.seeCourses();
			pView.createCourseJList(courselist);
		}
		
	}
	
	class CourseListListener implements ListSelectionListener
	{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			pView.createCourseDisplay(pView.getCourseInfo());
			pView.viewCoursesPage();
			pView.addCourseListeners(new CourseActiveStatus(), new ViewStudentsListener(),new ViewAssignmentsListener(), 
					 new ReturnHomeListener(), new EmailStudentListener(), new SearchStudentsListener(), 
					 new StudentEnrollmentListener(), new UploadAssignmentListener(), new AssignmentActiveStatusListener(), 
					 new ViewSubmissionsListener());
		}
		
	}
	
	class CourseActiveStatus implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("courseactivestatus button hit in control class");
			String[] courseInfo = pView.getCourseInfo();
			pModel.courseActive(courseInfo);
			pView.simpleMessage("Course Activation Status Sucessfully Changed. "
								+ "Please hit browse courses to see changes");
		}
	}
	
//	class ListListener implements ListSelectionListener
//	{
//		@Override
//		public void valueChanged(ListSelectionEvent e) {
//			System.out.println("in list acton listener");
//			pView.setCoursePage();
//		}

//	class CourseActiveStatus implements ActionListener
//	{
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			System.out.println("courseactivestatus button hit in control class");
////			String[] course=pView.getSelectedList();
////			pModel.courseActive(course);
////			pView.displayCourseActiveUpdateMessage();
//		}
//	}
	
	class ListListener implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e) {
			System.out.println("in list acton listener");
//			pView.setCoursePage();
		}
	}

	
	class SearchStudentsListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			//add to search students button
			System.out.println("Searching for McCree");
			//some JOptionPane gives  student id and course id
			String[] courseInfo = pView.getCourseInfo();
			int courseid=Integer.parseInt(courseInfo[0]);
			String string=null;
			System.out.println(string);
			System.out.println("This is the result in search parametter "+pView.getSearchParam().length());
			String[] strings=pModel.searchStudents(pView.getSearchParam(), courseid);
			//display information in gui 
			System.out.println(strings[0]);
			pView.updateStudentListDisplay(strings);
		}
	}
	
	class StudentEnrollmentListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			//picks up what student is hit
//			String[] strings=pView.EnrollStudent();							error in code
			String[] string=new String[1];
			string[0]=pModel.studentEnrollment(1000,1070);
			pView.updateStudentListDisplay(string);
		}
	}
	
	class SetGradesForAssignment implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			//get assignment name from view
			//parameter should be file name, course id, studentid, and the grade
			pModel.setGradesForAssignment("Assignment1.txt", 1070, 1000, 87);
		}
	}
}
