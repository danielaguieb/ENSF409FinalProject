package FrontEnd;


import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;


public class Client {
	private LoginGUI loginView;
	private LoginModel loginModel;
	private Socket socket;
	private ObjectOutputStream sendObject;
	private ObjectInputStream readObject;
	
	public Client(String host, int portnumber) throws UnknownHostException, IOException
	{
		socket=new Socket(host, portnumber);
		sendObject=new ObjectOutputStream(socket.getOutputStream());
		readObject=new ObjectInputStream(socket.getInputStream());
	}
	
	public void makeLoginGUI()
	{
		loginView = new LoginGUI();
		loginView.addSignInActionListener(new SignInListener());
		loginModel=new LoginModel(readObject, sendObject);
		loginView.setVisible(true); 
	}
	
	private void makeProfessorGUI(String profffirstname, String profflastname, int proffid) throws IOException 
	{
		ProfessorModel proffmodel=new ProfessorModel(sendObject, readObject);
		ProfessorView proffview=new ProfessorView(proffid, profffirstname, profflastname);
		ProfessorControl proffcontrol=new ProfessorControl(proffmodel, proffview);
	}
	
	private void makeStudentGUI(String studentfirstname, String studentlastname, int studenid) throws IOException
	{
		StudentModel studentmodel=new StudentModel(sendObject, readObject);
		StudentView studenview=new StudentView(studenid, studentfirstname, studentlastname);
		StudentControl studentcontrol=new StudentControl(studentmodel, studenview);
	}
	
	public static void main(String[] args)
	{
		try { 
			Client client=new Client("localhost", 9090);
			client.makeLoginGUI();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public class SignInListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("sign in button presed");
			String[] string=loginModel.loginAttempt(loginView.getUser(), loginView.getPass());
			if (string!=null) {
				loginView.setVisible(false);
				try {
					if (string[0].equals("PROFF"))
						makeProfessorGUI(string[1], string[2], Integer.parseInt(string[3]));
					else
						makeStudentGUI(string[1], string[2], Integer.parseInt(string[3]));
				} 
				catch (NumberFormatException | IOException e) {
					e.printStackTrace();
				}
			}
			else {
				loginView.simpleError("Incorrect Login Information: User does not exist in database");
			}
		}
	}
}
