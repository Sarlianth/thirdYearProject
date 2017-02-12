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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;

public class login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField inputUsername;
	private JTextField inputPassword;
	private static JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
				    frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					
					try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?useSSL=false", "root", ""); Statement stmt = conn.createStatement();){
						btnNewButton.setEnabled(true);
						
				      } catch(SQLException ex) {
				         ex.printStackTrace();
				         btnNewButton.setEnabled(false);
				      }
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 298, 170);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String guiUser, guiPass, dbUser = null, dbPass = null;
				
				guiUser = inputUsername.getText();
				guiPass = inputPassword.getText();
				
				System.out.println(guiUser+" "+guiPass);
				
				try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?useSSL=false", "root", ""); Statement stmt = conn.createStatement();){

					 String strSelect = "select username, password from users";
			 
			         ResultSet rset = stmt.executeQuery(strSelect);
			 
			         // Step 4: Process the ResultSet by scrolling the cursor forward via next().
			         // For each row, retrieve the contents of the cells with getXxx(columnName).
			         while(rset.next()) {   // Move the cursor to the next row, return false if no more row
			            dbUser = rset.getString("username");
			            dbPass = rset.getString("password");
			            System.out.println(dbUser);
			            System.out.println(dbPass);
			            
			            if(guiUser.equalsIgnoreCase(dbUser) && guiPass.equalsIgnoreCase(dbPass)){
			            	JOptionPane.showMessageDialog(null, "Successful login..");
			            	hideLogin();
			            	mainWindow f = new mainWindow();
			            	f.setLocationRelativeTo(null);
			            	f.setVisible(true);
			            	break;
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
		btnNewButton.setBounds(33, 73, 100, 50);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Exit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		
		btnNewButton_1.setBounds(143, 73, 100, 50);
		contentPane.add(btnNewButton_1);
		
		inputUsername = new JTextField();
		inputUsername.setBounds(99, 11, 144, 20);
		contentPane.add(inputUsername);
		inputUsername.setColumns(10);
		
		inputPassword = new JTextField();
		inputPassword.setBounds(99, 42, 144, 20);
		contentPane.add(inputPassword);
		inputPassword.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setBounds(33, 14, 66, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(33, 45, 66, 14);
		contentPane.add(lblPassword);
	}
	
	public void hideLogin(){
		this.dispose();
	}
}
