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
import java.awt.Color;
import javax.swing.JComboBox;

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindow frame = new mainWindow();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the frame.
	 */
	public mainWindow() {
		
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
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 576, 467);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.GRAY);
		tabbedPane.setBounds(10, 11, 447, 406);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Reservation", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Source station:");
		lblNewLabel.setBounds(10, 26, 91, 19);
		panel.add(lblNewLabel);
		
		JLabel lblDestination = new JLabel("Destination:");
		lblDestination.setBounds(239, 28, 79, 14);
		panel.add(lblDestination);
		
		
		comboBox.setBounds(96, 25, 118, 20);
		panel.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(sourceStations.toArray()));
		
		comboBox_1.setBounds(313, 25, 118, 20);
		panel.add(comboBox_1);
		comboBox_1.setModel(new DefaultComboBoxModel(destinationStations.toArray()));
		
		JButton btnSubmit = new JButton("Search");
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
		btnSubmit.setBounds(96, 61, 335, 23);
		panel.add(btnSubmit);
		
		lblAvailableBus.setBounds(10, 96, 91, 19);
		panel.add(lblAvailableBus);
		
		comboBox_2.setBounds(96, 95, 335, 20);
		panel.add(comboBox_2);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.setBounds(96, 123, 335, 23);
		panel.add(btnSelect);
		
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Bus management", null, panel_1, null);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Admin panel", null, panel_2, null);
		panel_2.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Tickets", null, panel_3, null);
		panel_3.setLayout(null);
		
		JButton button = new JButton("Logout");
		button.setBounds(467, 35, 88, 382);
		contentPane.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				finish();
				login frame = new login();
				frame.setVisible(true);
			}
		});
	
    }
	

	
	public void finish(){
		this.dispose();
	}
}
