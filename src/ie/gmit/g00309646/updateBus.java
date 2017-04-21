package ie.gmit.g00309646;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//SuppressWarnings to get rid of warnings
@SuppressWarnings({ "unchecked", "rawtypes" })
public class updateBus extends JFrame {

	// Declaring all variables for this class
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField busIDField;
	private JTextField sourceField;
	private JTextField destinationField;
	private static JComboBox comboBox = new JComboBox();
	private static JComboBox comboBox_1 = new JComboBox();
	private static JComboBox comboBox_2 = new JComboBox();
	private String tempTime;
	
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
					updateBus frame = new updateBus(false, 1);
					// Make the frame appear in the middle of the screen
					frame.setLocationRelativeTo(null);
					// Make frame visible
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
	public updateBus(boolean if_admin, int key) {
		// Create an array of integers for hours
		ArrayList<Integer> hours = new ArrayList<Integer>();
		for (int i = 1; i <= 12; i++){
		   hours.add(i);
		}
		
		// Create an array of integers for minutes
		ArrayList<Integer> mins = new ArrayList<Integer>();
		for (int i = 0; i <= 59; i++){
		   mins.add(i);
		}
		
		// Create an array of Strings to select either AM or PM when selecting time from dropdown boxes
		String[] choice = {"AM", "PM"};
		// Make the frame fixed size, not re-sizable
		setResizable(false);
		// Set the default font for this frame
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		// Set the title of the frame
		setTitle("Update bus - "+key);
		// Set frame foreground color
		setForeground(Color.WHITE);
		// Default close operation - DISPOSE
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Frame bounds
		setBounds(100, 100, 360, 260);
		// New content pane
		contentPane = new JPanel();
		// Content pane background color
		contentPane.setBackground(Color.DARK_GRAY);
		// Content pane borders
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		// Apply the pane
		setContentPane(contentPane);
		// Layout to be null, so I can drag and drop troughout the whole frame
		contentPane.setLayout(null);
		
		// Back button listener
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// When closing this frame refresh the buses and the timetable, so the information there is accurate and up-to-date
				mainWindow.refreshBuses();
				mainWindow.refreshTimetable();
				// Method to dispose the frame
				finish();
			}
		});
		
		btnBack.setBounds(200, 135, 80, 23);
		contentPane.add(btnBack);
		
		JLabel lblBusIdname = new JLabel("Bus ID/Name");
		lblBusIdname.setForeground(Color.WHITE);
		lblBusIdname.setBounds(10, 13, 86, 17);
		contentPane.add(lblBusIdname);
		
		JLabel lblSourceStation = new JLabel("Source station");
		lblSourceStation.setForeground(Color.WHITE);
		lblSourceStation.setBounds(10, 44, 86, 17);
		contentPane.add(lblSourceStation);
		
		JLabel lblDestination = new JLabel("Destination");
		lblDestination.setForeground(Color.WHITE);
		lblDestination.setBounds(10, 75, 86, 17);
		contentPane.add(lblDestination);
		
		JLabel lblTime = new JLabel("Time (HH:MM)");
		lblTime.setForeground(Color.WHITE);
		lblTime.setBounds(10, 106, 86, 17);
		contentPane.add(lblTime);
		
		// Update button listener
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Method variables
				String bus_number = busIDField.getText();
				String depart_from = sourceField.getText();
				String going_to = destinationField.getText();
				String bus_time = null;
				// Just to format the time into one String, if hour is between 0-9 I am adding .0 to it
				if(comboBox_1.getSelectedItem().equals(0) || comboBox_1.getSelectedItem().equals(1) || comboBox_1.getSelectedItem().equals(2) || comboBox_1.getSelectedItem().equals(3)
						|| comboBox_1.getSelectedItem().equals(4) || comboBox_1.getSelectedItem().equals(5) || comboBox_1.getSelectedItem().equals(6)
						|| comboBox_1.getSelectedItem().equals(7) || comboBox_1.getSelectedItem().equals(8) || comboBox_1.getSelectedItem().equals(9)) {
					bus_time = comboBox.getSelectedItem() + ".0" + comboBox_1.getSelectedItem() + comboBox_2.getSelectedItem().toString();
				}
				// If time is above 9 I am only adding . in between hours and minutes
				else{
					bus_time = comboBox.getSelectedItem() + "." + comboBox_1.getSelectedItem() + comboBox_2.getSelectedItem().toString();
				}
				
				// Inserting new bus into database
				try {
					// Setting up connection
					Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbNameCon+"?useSSL=false", dbUsername, dbPassword); 
					Statement stmt = conn.createStatement();
					
					// Formatting the query 
					String strSelect = "update bus_table set bus_number='"+bus_number+"', depart_from='"+depart_from+"', going_to='"+going_to+"', bus_time='"+bus_time+"' where bus_id="+key;
			 
					// Executing statement
			        stmt.executeUpdate(strSelect);
			        
			        // Refreshing all tables containing information about buses, so data is accurate and up-to-date
			        mainWindow.refreshBuses();
			        mainWindow.refreshTimetable();
			        mainWindow.populate();
			        finish();
			        
			        // Close the connection
			        stmt.close();
					conn.close();

			      } catch(SQLException ex) {
			    	  ex.printStackTrace();
			      } // end of try/catch
			} //actionPerformed
		});
		btnUpdate.setBounds(110, 135, 80, 23);
		contentPane.add(btnUpdate);
		
		sourceField = new JTextField();
		sourceField.setColumns(10);
		sourceField.setBounds(106, 42, 185, 23);
		contentPane.add(sourceField);
		
		destinationField = new JTextField();
		destinationField.setColumns(10);
		destinationField.setBounds(106, 73, 185, 23);
		contentPane.add(destinationField);
		
		busIDField = new JTextField("");
		busIDField.setBounds(106, 11, 185, 23);
		contentPane.add(busIDField);
		busIDField.setColumns(10);
		
		comboBox.setModel(new DefaultComboBoxModel(hours.toArray()));
		comboBox.setBounds(106, 104, 58, 20);
		contentPane.add(comboBox);
		
		comboBox_1.setModel(new DefaultComboBoxModel(mins.toArray()));
		comboBox_1.setBounds(170, 104, 58, 20);
		contentPane.add(comboBox_1);
		
		comboBox_2 = new JComboBox(choice);
		comboBox_2.setBounds(233, 104, 58, 20);
		contentPane.add(comboBox_2);
		
		// Connect to database to select the bus details and fill information into form
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbNameCon+"?useSSL=false", dbUsername, dbPassword); 
			Statement stmt = conn.createStatement();
			
			String strSelect = "select * from bus_table where bus_id="+key;
	        ResultSet rset = stmt.executeQuery(strSelect);
	 
        	while(rset.next()) {
	        	
	        	//int tempID = rset.getInt("bus_id");
	        	busIDField.setText(rset.getString("bus_number"));
	    		sourceField.setText(rset.getString("depart_from"));
	    		destinationField.setText(rset.getString("going_to"));
	    		tempTime = rset.getString("bus_time");
	    			
	    		String min;
	    		String[] time = tempTime.split("\\.");
	    		//time[0] <-- this is out hour
	    		String timeChoice = time[0].substring(Math.max(time[0].length() - 2, 0)); // <-- This is AM / PM depending on whatever is in database for given bus
	
	    		// If the time is 4 chars in length i split it by 2 so for example if time is 11:20 - minutes will be whatever is after the colon :
	    		if(time[1].length() == 4){
	    			min = time[1].substring(0, 2); 
	    		}
	    		// If time is different in length then it must be 3 chars long so for example 9:30 - minutes will be whatever is after colon :
	    		else{
	    			min = time[1].substring(0, 1);
	    		}
	    		
	    		// Set the combobox to whatever the time we have got from database for this bus
	    		comboBox.setSelectedItem(Integer.parseInt(time[0]));
	    		comboBox_1.setSelectedItem(Integer.parseInt(min));
	    		if(timeChoice.equalsIgnoreCase("am")){
	    			comboBox_2.setSelectedItem("AM");
	    		}
	    		else{
	    			comboBox_2.setSelectedItem("PM");
	    		}
	        }  
        	// Close the connection
        	rset.close();
	        stmt.close();
			conn.close();
			
	      } catch(SQLException ex) {
	    	  ex.printStackTrace();
	      }
	}
	
	// Method to dispose the frame
	public void finish(){
		this.dispose();
	}
}