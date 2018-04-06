package FrontEnd;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionListener;

public class ProfessorView extends JFrame {
	private String profffirstname;
	private String profflastname;
	private int proffid;
	
	private CardLayout mainCards =  new CardLayout();
	private JScrollPane searchresults;
	private Container container;
	
	private JPanel homePanel;
	private JButton createCourses;
	private JButton viewCourses;
	
	private JPanel coursePanel;
	private JButton setCourseactive;
	private JButton viewAssigns;
	private JButton viewStudents;
	private JButton returnHome;
	private JPanel courseInnerPanel;
	private CardLayout courseInnerCards;
	private DefaultListModel<String> studentListModel = new DefaultListModel<String>();
	private JList<String> studentJList = new JList<String>();
	private DefaultListModel<String> courseListModel = new DefaultListModel<String>();
	private JList<String> courseJList = new JList<String>();
	private JButton emailStudents;
	private JButton searchStudents;
	private JButton enrollment;
	private JTextField findStudents;
	private JButton uploadAssign;
	private JButton setAssignActive;
	private JButton viewSubmissions;
	
	
	
	
	public ProfessorView(int proffid, String profffirstname, String profflastname) {
		super("Professor Learning Platform");
		this.proffid=proffid;
		this.profffirstname=profffirstname;
		this.profflastname=profflastname;
		container=getContentPane();
		container.setLayout(mainCards);
		
		setSize(700, 500);
		setResizable(false);
		makeWindowListener();
		createHomeDisplay();
		createCourseDisplay();
	}
	
	public int getProffID()
	{
		return proffid;
	}
	
	public DefaultListModel<String> getListModel()
	{
		return studentListModel;
	}
	 
	private void createBanner(JPanel bannerPanel, String topMessage)
	{
		JLabel banner=new JLabel(topMessage);
		banner.setFont(new Font("Times New Roman", Font.BOLD,20));
		banner.setForeground(Color.white);
		bannerPanel.setOpaque(true);
		bannerPanel.add(banner, JLabel.CENTER);
		bannerPanel.setBackground(new Color(255, 209, 175));
	}
	
	public void createHomeDisplay() {
		homePanel = new JPanel(new BorderLayout());
		container.add(homePanel, "Home");
		createHomeTopPanel();
		createHomeCenterPanel();
	}

	
	private void createHomeTopPanel()
	{	
		JPanel grandPanel=new JPanel(new GridLayout(2, 1));
		
		JPanel bannerPanel = new JPanel();
		createBanner(bannerPanel, "Professor Learning Platform");
		
		JPanel buttonPanel=new JPanel();
		createCourses=new JButton("CREATE A COURSE");
		viewCourses=new JButton("VIEW COURSES");
		
		viewCourses.addActionListener(new ActionListener() {			
			@Override													
			public void actionPerformed(ActionEvent e) {				
				mainCards.show(container, "Courses");					
			}															
		});																
		
		buttonPanel.add(createCourses);
		buttonPanel.add(viewCourses);
		grandPanel.add(bannerPanel);
		grandPanel.add(buttonPanel);
		homePanel.add(grandPanel, BorderLayout.NORTH);
	}
	
	
	private void createHomeCenterPanel()
	{
		JPanel grandPanel = new JPanel();
		
		String welcome = "Welcome to your home page, Professor " +
						 profffirstname + " " + profflastname + " ("+proffid+")" + 
						 "\n" + "What would you like to do?";
		
//		String[] argument= {"Welcome Professor "+profffirstname+" "+profflastname+" ("+proffid+")", "You are currently in no course or student page"};
//		listmodel=new DefaultListModel<String>();
//		listmodel.addElement(argument[0]);
//		listmodel.addElement(argument[1]);
//		displaylist=new JList<String>();
//		displaylist.setModel(listmodel);
		JTextArea welcomePane = new JTextArea(welcome);
		welcomePane.setFont(new Font("Courier New", Font.PLAIN, 14));
//		welcomePane.setColumns(30);
		welcomePane.setRows(20);
		
		
//		System.out.println("In constructor: " + displaylist.getModel().getSize());
//		displaylist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		displaylist.setVisibleRowCount(15);
//		displaylist.setFont(new Font("Courier New", Font.BOLD, 11));
//		JTextPane displayarea=new JTextPane();
//		displayarea.setText("Welcome Professor "+profffirstname+" "+profflastname+" ("+proffid+")\nYou are currently in no course or student page");
//		displayarea.setFont(new Font("Courier New", Font.BOLD, 11) );
//		displayarea.setEditable(false);
//		searchresults=new JScrollPane(displaylist);
//		searchresults.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		searchresults.setPreferredSize(new Dimension(600,325));
//		temp[0][0].add(searchresults);
		grandPanel.add(welcomePane);
		homePanel.add(grandPanel, BorderLayout.CENTER);
	}
	
//	public void switchToHomePage() {
//		container.add(homePanel);
//	}
	
	public void createCourseDisplay() {
		coursePanel = new JPanel(new BorderLayout());
		container.add(coursePanel, "Courses");
		createCourseTopPanel();
		createCourseInnerPanel();
		createCourseBottomPanel();
	}
	
	private void createCourseTopPanel() {
		JPanel grandPanel = new JPanel(new GridLayout(2, 1));
		JPanel bannerPanel = new JPanel();
		createBanner(bannerPanel, "COURSE NAME - NOT COMPLETE");
		JPanel topButtons = new JPanel();
		setCourseactive = new JButton("CHANGE COURSE ACTIVE STATUS");
		
		viewStudents = new JButton("STUDENTS");
		viewStudents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				courseInnerCards.show(courseInnerPanel, "Students");
			}
		});
		
		viewAssigns = new JButton("ASSIGNMENTS");
		viewAssigns.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				courseInnerCards.show(courseInnerPanel, "Assignments");
			}
		});
		
		returnHome = new JButton("HOME");
		returnHome.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainCards.show(container, "Home");
			}
		});
		
		topButtons.add(setCourseactive);
		topButtons.add(viewStudents);
		topButtons.add(viewAssigns);
		topButtons.add(returnHome);
		
		grandPanel.add(bannerPanel);
		grandPanel.add(topButtons);
		coursePanel.add(grandPanel, BorderLayout.NORTH);
	}
	
	
	private void createCourseInnerPanel() {
		courseInnerCards = new CardLayout();
		courseInnerPanel = new JPanel(courseInnerCards);
		JPanel courseStudentPanel = new JPanel(new BorderLayout());
		JPanel courseAssignPanel = new JPanel(new BorderLayout());
		courseInnerPanel.add(courseStudentPanel, "Students");
		courseInnerPanel.add(courseAssignPanel, "Assignments");
		
		
		/*
		 * ADD JLIST STUFF FOR RESULTS
		 */
		
		
		JPanel studentButtonsPanel = new JPanel();
		addStudentButtons(studentButtonsPanel);
		JPanel assignButtonsPanel = new JPanel();
		addAssignButtons(assignButtonsPanel);
		courseStudentPanel.add(studentButtonsPanel, BorderLayout.SOUTH);
		courseAssignPanel.add(assignButtonsPanel, BorderLayout.SOUTH);
		
		coursePanel.add(courseInnerPanel, BorderLayout.CENTER);
	}
	
	private void createCourseBottomPanel() {

	}
	
	private void addStudentButtons(JPanel buttonsPanel) {
		enrollment =  new JButton("ENROLL/UNENROLL");
		emailStudents = new JButton("EMAIL STUDENTS");
		searchStudents = new JButton("SEARCH A STUDENT");
		findStudents = new JTextField();
		findStudents.setColumns(15);
		buttonsPanel.add(enrollment);
		buttonsPanel.add(emailStudents);
		buttonsPanel.add(searchStudents);
		buttonsPanel.add(findStudents);
	}
	
	private void addAssignButtons(JPanel buttonsPanel) {
		setAssignActive = new JButton("CHANGE ACTIVE STATUS");
		uploadAssign = new JButton("UPLOAD ASSIGNMENT");
		viewSubmissions = new JButton("VIEW SUBMISSIONS");
		buttonsPanel.add(setAssignActive);
		buttonsPanel.add(uploadAssign);
		buttonsPanel.add(viewSubmissions);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void makeWindowListener()
	{
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog((JFrame) e.getSource(), "Are you sure you want to exit the application?",
						"Exit Application", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
				if (result==JOptionPane.NO_OPTION)
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
			
		});
	}
	
	
	public void addCreateCourseListener(ActionListener a)
	{
		createCourses.addActionListener(a);
	}
	
	public String[] createCourse()
	{
		Object[] objects={ "create course",  "cancel"};
		JPanel panel=new JPanel();
		JTextField coursename=new JTextField("Enter the Course Name");
		JTextField active=new JTextField("Active?");
		panel.add(coursename);
		panel.add(active);
		int result = JOptionPane.showOptionDialog(null, panel, "Enter Info of new Course to Create",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, objects, null);
		
		if (result==JOptionPane.YES_OPTION){
			if (!active.getText().equals("1") && !active.getText().equals("0")) {
				JOptionPane.showMessageDialog(null, "Active field should either have a 1"
						+ " (course active) or O (course inactive)","Error",JOptionPane.PLAIN_MESSAGE);
			}
			else {
				String[] strings= {coursename.getText(), active.getText()};
				JOptionPane.showMessageDialog(null, "Course succesfully entered","Success",JOptionPane.PLAIN_MESSAGE);
				return strings;
			}
		}	
			
		if (result==JOptionPane.NO_OPTION){
			JOptionPane.getRootFrame().dispose();
		}
		return null;
	}
	
	public void addBrowseCourseListener(ActionListener a)
	{
		viewCourses.addActionListener(a);
	}
	
	
	public void updateDisplay(String[] list)
	{
		System.out.println("updating display");
		while (!studentListModel.isEmpty())
		{
			studentListModel.removeElementAt(0);
		}
		
		for (int i=0; i<list.length; i++) {
			studentListModel.addElement(list[i]);
		}
	}
	
	public void addCourseActiveListener(ActionListener a)
	{
		setCourseactive.addActionListener(a);
	}
	
	public void addListListener(ListSelectionListener a)
	{
		studentJList.addListSelectionListener(a);
	}

	public ArrayList<String> getSelectedList()
	{
		System.out.println("In selected list method");
		System.out.println(studentJList.getSelectedValue());
		System.out.println(studentJList.getModel().getElementAt(0));
		//String string=displaylist.getSelectedValue();
//		int index=0;
		String string=new String(studentJList.getSelectedValue());
		System.out.println(string);
		//String[] strings=string.split(";");
		ArrayList<String> strings=new ArrayList<String>();
		strings.add(string);
		return strings;
	}
	
	public void displayCourseActiveUpdateMessage()
	{
		JOptionPane.showMessageDialog(null, "Course Activation Status Sucessfully Changed. Please hit browse courses to "
				+ "see changes","Success",JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void main(String[] args) {
		ProfessorView pView = new ProfessorView(69420, "Daniel", "Guieb");
		pView.setVisible(true);
	}
}
