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
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.JPasswordField;
import java.awt.Font;

public class login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField inputUsername;
	private static JButton btnNewButton;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
				    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				        if ("Nimbus".equals(info.getName())) {
				            UIManager.setLookAndFeel(info.getClassName());
				            break;
				        }
				    }
				} catch (Exception e) {
				    // If Nimbus is not available, you can set the GUI to another look and feel.
				}
				
				try {
					login frame = new login();
				    frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					
					try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?useSSL=false", "root", ""); Statement stmt = conn.createStatement();){
						btnNewButton.setEnabled(true);
						
				      } catch(SQLException ex) {
				         ex.printStackTrace();
				         btnNewButton.setEnabled(false);
				         JOptionPane.showMessageDialog(null, ex);
				      }
					
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public login() {
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setResizable(false);
		setTitle("IrishBusApp - Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 252, 142);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String guiUser, guiPass, dbUser = null, dbPass = null, dbName = null;
				char[] pass;
				boolean is_admin;
				int userID;
				
				guiUser = inputUsername.getText();;
				pass = passwordField.getPassword();
				guiPass = String.valueOf(pass);;
				
				//System.out.println(guiUser+" "+guiPass);
				
				try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?useSSL=false", "root", ""); Statement stmt = conn.createStatement();){

					 String strSelect = "select id, name, username, password, is_admin from users";
			 
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
			            
			            if(guiUser.equalsIgnoreCase(dbUser) && guiPass.equalsIgnoreCase(dbPass)){
			            	if(is_admin){
			            		JOptionPane.showMessageDialog(null, "Hi, " + dbName + "." + "You have admin privileges.");
			            		hideLogin();
				            	mainWindow f = new mainWindow(true, userID);
				            	f.setLocationRelativeTo(null);
				            	f.setVisible(true);
				            	break;
			            	}
			            	else{
			            		JOptionPane.showMessageDialog(null, "Hi, " + dbName + ".");
			            		hideLogin();
				            	mainWindow f = new mainWindow(false, userID);
				            	f.setLocationRelativeTo(null);
				            	f.setVisible(true);
				            	break;
			            	}
			            	
			            	
			            }
			         }
			         
			         if(!(guiUser.equalsIgnoreCase(dbUser) && guiPass.equalsIgnoreCase(dbPass))){
			        	 JOptionPane.showMessageDialog(null, "Wrong details, try again..");
			         }
					
			      } catch(SQLException ex) {
			         ex.printStackTrace();
			      }
				
			}
		});
		btnNewButton.setBounds(10, 70, 100, 24);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Exit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Thank you for using this software.");
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
	
	public void hideLogin(){
		this.dispose();
	}
}
