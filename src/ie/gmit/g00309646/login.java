package ie.gmit.g00309646;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.JPasswordField;
import java.awt.Font;

public class login extends JFrame {

	// Declaring all variables for this class
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField inputUsername;
	private static JButton btnNewButton;
	private JPasswordField passwordField;
	
	// Variables for database connection
	private static String dbHost = "sql8.freemysqlhosting.net";
	private static String dbPort = "3306";
	private static String dbNameCon = "sql8160217";
	private static String dbUsername = "sql8160217";
	private static String dbPassword = "XRN5N6f6BG";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				// BeautyEye API to make the application look better
				try {
					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
					UIManager.put("RootPane.setupButtonVisible", false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				// Create the frame
				try {
					login frame = new login();
					// Make the frame appear in the middle of the screen
				    frame.setLocationRelativeTo(null);
				    // Set the frame visible
					frame.setVisible(true);
					
					// Try to connect to database
					try (Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbNameCon+"?useSSL=false", dbUsername, dbPassword); Statement stmt = conn.createStatement();){
						// Enable the login button only if access to database has been successfull
						btnNewButton.setEnabled(true);
						
						// Close connection
						stmt.close();
						conn.close();
						
				      } catch(SQLException ex) {
				         ex.printStackTrace();
				         btnNewButton.setEnabled(false);
				         JOptionPane.showMessageDialog(null, ex);
				      }
					
				} catch (Exception e) {
					e.printStackTrace();
					// Popup message to inform user that the connection cannot be aquired 
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public login() {
		// Set the default font for this frame
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setResizable(false);
		setTitle("IrishBusApp - Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 292, 187);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// New button and listener
		btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Variables that will hold information that will later be user to collect from database
				String guiUser, guiPass, dbUser = null, dbPass = null, dbName = null;
				char[] pass;
				boolean is_admin;
				int userID;
				
				// Variables from the fields to compare with the ones from database
				guiUser = inputUsername.getText();;
				pass = passwordField.getPassword();
				guiPass = String.valueOf(pass);;
				
				//System.out.println(guiUser+" "+guiPass);
				
				// Connect to database
				try (Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbNameCon+"?useSSL=false", dbUsername, dbPassword); Statement stmt = conn.createStatement();){

					 // Query String
					 String strSelect = "select id, name, username, password, is_admin from users";
			 
					 // Execute the statement
			         ResultSet rset = stmt.executeQuery(strSelect);
			 
			         // Step 4: Process the ResultSet by scrolling the cursor forward via next().
			         // For each row, retrieve the contents of the cells with getXxx(columnName).
			         while(rset.next()) {   // Move the cursor to the next row, return false if no more row
			        	userID = rset.getInt("id");
			        	dbName = rset.getString("name");
			            dbUser = rset.getString("username");
			            dbPass = rset.getString("password");
			            is_admin = rset.getBoolean("is_admin");
			            //System.out.println(dbUser);
			            //System.out.println(dbPass);
			            
			            // Compare the username and password from the text fields to the username and password from the database
			            if(guiUser.equalsIgnoreCase(dbUser) && guiPass.equalsIgnoreCase(dbPass)){
			            	// if user has been successfully found in the database, check if the user is admin
			            	if(is_admin){
			            		// Welcome popup message to inform user that he has admin privileges
			            		JOptionPane.showMessageDialog(null, "Hi, " + dbName + "." + "You have admin privileges.");
			            		// Hide the login frame
			            		hideLogin();
			            		// Create and show the main window frame in the middle of the screen
				            	mainWindow f = new mainWindow(true, userID);
				            	f.setLocationRelativeTo(null);
				            	f.setVisible(true);
				            	break;
			            	}
			            	else{
			            		// Welcome popup message displaying users Name
			            		JOptionPane.showMessageDialog(null, "Hi, " + dbName + ".");
			            		// Hide the login frame
			            		hideLogin();
			            		// Create and show the main window frame in the middle of the screen
				            	mainWindow f = new mainWindow(false, userID);
				            	f.setLocationRelativeTo(null);
				            	f.setVisible(true);
				            	break;
			            	}
			            	
			            	
			            }
			         }
			         // If the username and/or password has not been found in the database
			         if(!(guiUser.equalsIgnoreCase(dbUser) && guiPass.equalsIgnoreCase(dbPass))){
			        	 // popup message for user to inform that he entered wrong details
			        	 JOptionPane.showMessageDialog(null, "Wrong details, try again..");
			         }
			         
			         // close the connection
			        rset.close();
			        stmt.close();
					conn.close();
					
			      } catch(SQLException ex) {
			         ex.printStackTrace();
			      }
				
			}
		});
		btnNewButton.setBounds(10, 70, 100, 24);
		contentPane.add(btnNewButton);
		
		// Exit button and its listener
		JButton btnNewButton_1 = new JButton("Exit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// popup message to thank user for using this software
				JOptionPane.showMessageDialog(null, "Thank you for using IrishBusApp.");
				System.exit(0);
			}
		});
		
		
		btnNewButton_1.setBounds(120, 70, 100, 24);
		contentPane.add(btnNewButton_1);
		
		inputUsername = new JTextField();
		inputUsername.setBounds(76, 11, 144, 20);
		contentPane.add(inputUsername);
		inputUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setBounds(10, 14, 66, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(10, 45, 66, 14);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(76, 42, 144, 20);
		contentPane.add(passwordField);
	}
	
	// function to dispose this frame
	public void hideLogin(){
		this.dispose();
	}
}