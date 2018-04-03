package FrontEnd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.Border;

import SharedObjects.Course;


public class ProfessorView extends JFrame {
	private String proffname;
	private int proffid;
	private JButton createcourses;
	private JButton browsecourses;
	private JButton setactive;
	private JButton searchstudents;
	private JButton enrollment;
	private JButton uploadassignment;
	private JButton setassignmentactive;
	private JButton viewgrades;
	private JScrollPane searchresults;
	private DefaultListModel<String> listmodel;
	private JList<String> displaylist;
	private JTextPane displayarea;
	private Container container;
	
	public ProfessorView(String s, int proffid, String proffname)
	{
		super(s);
		this.proffid=proffid;
		this.proffname=proffname;
		container=this.getContentPane();
		this.setLayout(new BorderLayout());
		this.setSize(700, 500);
		this.setResizable(false);
		createTopDisplayPanel();
		createCenterDisplay();
		createBottomDisplayPanel();
		makeWindowListener();
	}
	
	private void createBanner(JPanel[][] temp)
	{
		JLabel banner=new JLabel("Professor Learning Platforms");
		banner.setFont(new Font("Times New Roman", Font.BOLD,20));
		banner.setForeground(Color.white);
		temp[0][0].setOpaque(true);
		temp[0][0].add(banner, JLabel.CENTER);
		temp[0][0].setBackground(Color.darkGray);
	}
	
	private void createTopDisplayPanel()
	{
		JPanel grandpanel=new JPanel(new GridLayout(2, 1,0,0));
		JPanel[][] temp=new JPanel[2][1];
		for (int i=0; i<2; i++) {
			for (int j=0; j<1; j++) {
				temp[i][j]=new JPanel();
				grandpanel.add(temp[i][j]);
			}
		}
		
		JPanel northpanel=new JPanel();
		createcourses=new JButton("Create Course");
		browsecourses=new JButton("Browse Courses");
		setactive=new JButton("Set Course Active");
		searchstudents=new JButton("Search Students");
		northpanel.add(createcourses);
		northpanel.add(browsecourses);
		northpanel.add(setactive);
		northpanel.add(searchstudents);
		temp[1][0].add(northpanel);
		createBanner(temp);
		container.add(grandpanel, BorderLayout.NORTH);
	}
	
	private void createBottomDisplayPanel()
	{
		JPanel southpanel=new JPanel();
		enrollment=new JButton("Enroll Student");
		uploadassignment=new JButton("Upload Assignment");
		setassignmentactive=new JButton("Set Assignment Active");
		viewgrades=new JButton("View Grades");
		southpanel.add(enrollment);
		southpanel.add(uploadassignment);
		southpanel.add(setassignmentactive);
		southpanel.add(viewgrades);
		container.add(southpanel, BorderLayout.SOUTH);
	}
	
	private void createCenterDisplay()
	{
		JPanel centerpanel=new JPanel(new GridLayout(1, 1,0,0));
		JPanel[][] temp=new JPanel[1][1];
		for (int i=0; i<1; i++) {
			for (int j=0; j<1; j++) {
				temp[i][j]=new JPanel();
				centerpanel.add(temp[i][j]);
			}
		}
		
//		String[] argument= {"Welcome Professor "+proffname+" ("+proffid+")", "You are currently in no course or student page"};
//		listmodel=new DefaultListModel<String>();
//		listmodel.addElement(argument[0]);
//		listmodel.addElement(argument[1]);
//		displaylist=new JList<String>(listmodel);
//		displaylist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		displaylist.setVisibleRowCount(15);
//		displaylist.setFont(new Font("Courier New", Font.BOLD, 11));
		displayarea=new JTextPane();
		displayarea.setText("Welcome Professor "+proffname+" ("+proffid+")\nYou are currently in no course or student page");
		displayarea.setFont(new Font("Courier New", Font.BOLD, 11) );
		searchresults=new JScrollPane(displayarea);
		searchresults.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		searchresults.setPreferredSize(new Dimension(600,325));
		temp[0][0].add(searchresults);
		container.add(centerpanel, BorderLayout.CENTER);
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
		createcourses.addActionListener(a);
	}
	
	public void addBrowseCourseListener(ActionListener a)
	{
		browsecourses.addActionListener(a);
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
	
	
}
