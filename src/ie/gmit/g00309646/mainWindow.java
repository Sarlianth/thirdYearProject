package ie.gmit.g00309646;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.Font;

public class mainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static ArrayList<String> sourceStations = new ArrayList<String>();
	private static ArrayList<String> destinationStations = new ArrayList<String>();
	private JComboBox comboBox = new JComboBox();
	private JComboBox comboBox_1 = new JComboBox();
	private static JLabel lblAvailableBus = new JLabel("Available bus: ");
	private static ArrayList<String> availableBuses = new ArrayList<String>();
	private static JComboBox comboBox_2 = new JComboBox();
	private static JTabbedPane tabbedPane;
	private static JPanel panel_2 = new JPanel();
	private static JPanel panel_1 = new JPanel();
	private static JPanel panel_3 = new JPanel();
	private static JPanel panel = new JPanel();
	private JButton btnSubmit = new JButton("Search");
	private JButton button = new JButton("Logout");
	private JButton btnSelect = new JButton("Select");
	private JLabel lblNewLabel = new JLabel("Source station:");
	private	JLabel lblDestination = new JLabel("Destination:");
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindow frame = new mainWindow(false);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}//main
	

	/**
	 * Create the frame.
	 */
	public mainWindow(boolean if_admin) {
		setResizable(false);
		setBackground(Color.DARK_GRAY);
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setTitle("IrishBusApp");
		setForeground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 576, 339);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//////////////////////////////////////////////
		///////SETUP TABBED PANE AND PANELS//////////
		/////////////////////////////////////////////
		UIManager.put("TabbedPane.selected", Color.getHSBColor(300,150,200));
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setForeground(Color.WHITE);
		tabbedPane.setBackground(Color.DARK_GRAY);
		tabbedPane.setBounds(10, 11, 447, 284);
		contentPane.add(tabbedPane);
		
		tabbedPane.addTab("Reservation", panel);
		tabbedPane.addTab("Bus management", panel_1);
		tabbedPane.addTab("Admin panel", panel_2);
		tabbedPane.addTab("Tickets", panel_3);
		
		panel.setLayout(null);
		panel_1.setLayout(null);
		panel_2.setLayout(null);
		panel_3.setLayout(null);
		
		panel.setBackground(Color.DARK_GRAY);
		panel_1.setBackground(Color.DARK_GRAY);
		panel_2.setBackground(Color.DARK_GRAY);
		panel_3.setBackground(Color.DARK_GRAY);
		
		//if current user does not have administrator privileges disable the admin panel tab 
		if(!if_admin){
			tabbedPane.setEnabledAt(2, false);
		}
		
		//get the bus stations from database and populate them into array lists
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?useSSL=false", "root", ""); 
			Statement stmt = conn.createStatement();
			
			String strSelect = "select depart_from, going_to from bus_table";
	 
	        ResultSet rset = stmt.executeQuery(strSelect);
	 
	        while(rset.next()) {
	        	if(!sourceStations.contains(rset.getString("depart_from"))){
	        		sourceStations.add(rset.getString("depart_from"));
	        	}
	        	if(!destinationStations.contains(rset.getString("going_to"))){
	        		destinationStations.add(rset.getString("going_to"));
	        	}
	        }
	      } catch(SQLException ex) {
	    	  ex.printStackTrace();
	      }
		
		
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 26, 91, 19);
		panel.add(lblNewLabel);
	
		lblDestination.setForeground(Color.WHITE);
		lblDestination.setBounds(239, 28, 79, 14);
		panel.add(lblDestination);
		
		comboBox.setBounds(111, 25, 118, 20);
		panel.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(sourceStations.toArray()));
		
		comboBox_1.setBounds(313, 25, 118, 20);
		panel.add(comboBox_1);
		comboBox_1.setModel(new DefaultComboBoxModel(destinationStations.toArray()));
		
		btnSubmit.setBounds(114, 61, 317, 23);
		panel.add(btnSubmit);
		lblAvailableBus.setForeground(Color.WHITE);
		
		lblAvailableBus.setBounds(10, 96, 91, 19);
		panel.add(lblAvailableBus);
		
		comboBox_2.setBounds(114, 95, 317, 20);
		panel.add(comboBox_2);

		btnSelect.setBounds(114, 123, 317, 23);
		panel.add(btnSelect);
		
		button.setBounds(467, 35, 88, 261);
		contentPane.add(button);
		
		//////////////////////////////////////////////
		///////LOGOUT BUTTON ACTION LISTENER/////////
		/////////////////////////////////////////////
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				finish();
				login frame = new login();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	
		//////////////////////////////////////////////
		///////SUBMIT BUTTON ACTION LISTENER/////////
		/////////////////////////////////////////////
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				availableBuses.clear();
				
				if(!comboBox.getSelectedItem().equals(comboBox_1.getSelectedItem())){
					System.out.println(comboBox.getSelectedItem() + "  " + comboBox_1.getSelectedItem());
					
					try {
						Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?useSSL=false", "root", ""); 
						Statement stmt = conn.createStatement();
						
						String strSelect = "select * from bus_table where depart_from LIKE '" + comboBox.getSelectedItem() + "' and going_to LIKE '" + comboBox_1.getSelectedItem() + "'";
				 
				        ResultSet rset = stmt.executeQuery(strSelect);
				 
			        	while(rset.next()) {
				        	
				        	int tempID = rset.getInt("bus_id");
				        	String tempBusNo = rset.getString("bus_number");
				        	String tempDepartFrom = rset.getString("depart_from");
				        	String tempGoingTo = rset.getString("going_to");
				        	String tempBusTime = rset.getString("bus_time");
				        	
				        	String availableBus = tempBusNo + ": " + tempDepartFrom + " to " + tempGoingTo + " @ " + tempBusTime;
				        	availableBuses.add(availableBus);

				        	comboBox_2.setModel(new DefaultComboBoxModel(availableBuses.toArray()));
				        }  
			        	

				      } catch(SQLException ex) {
				    	  ex.printStackTrace();
				      }
					
				}
				else{
					
					System.out.println("Wrong! Try again..");
					availableBuses.clear();
					comboBox_2.setModel(new DefaultComboBoxModel(availableBuses.toArray()));
					
				}
				
			}
		});
    }
	
	
	
	public void finish(){
		this.dispose();
	}
}
