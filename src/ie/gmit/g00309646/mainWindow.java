package ie.gmit.g00309646;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

//SuppressWarnings to get rid of warnings
@SuppressWarnings({ "unchecked", "rawtypes" })
public class mainWindow extends JFrame {

	// Declaring all class variables
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static ArrayList<String> sourceStations = new ArrayList<String>();
	private static ArrayList<String> destinationStations = new ArrayList<String>();
	private static JComboBox comboBox = new JComboBox();
	private static JComboBox comboBox_1 = new JComboBox();
	private static JLabel lblAvailableBus = new JLabel("Available buses:  ");
	private static ArrayList<String> availableBuses = new ArrayList<String>();
	private static JComboBox comboBox_2 = new JComboBox();
	private static JTabbedPane tabbedPane;
	private static JPanel panel_2 = new JPanel();
	private static JPanel panel_1 = new JPanel();
	private static JPanel panel_3 = new JPanel();
	private static JPanel panel = new JPanel();
	private JButton btnSearch = new JButton("Search");
	private JButton button = new JButton("Logout");
	private JButton btnSelect = new JButton("Choose bus");
	private JLabel lblNewLabel = new JLabel("Source station:  ");
	private	JLabel lblDestination = new JLabel("Destination:");
	private final static JScrollPane scrollPane = new JScrollPane();
	private final static JScrollPane scrollPane_1 = new JScrollPane();
	private static JTable table;
	private static JTable table_1;
	private final JLabel lblSelectDate = new JLabel("Select date:");
	private final JButton btnClear = new JButton("Clear");
	private static JDatePickerImpl datePicker;
	private static JPanel panel_4 = new JPanel();
	private final JLabel logoTimetable = new JLabel("");
	private static JLabel lblStudent_1 = new JLabel("Student: \u20AC");
	private static JLabel lblAdult_1 = new JLabel("Adult: \u20AC");
	private static JLabel studentPrice = new JLabel("0.0");
	private static JLabel adultPrice = new JLabel("0.0");
	private static JLabel lblTotal = new JLabel("Total: \u20AC");
	private static JLabel totalPriceLbl = new JLabel("0.0");
	private static JComboBox comboBox_adult = new JComboBox();
	private static JComboBox comboBox_student = new JComboBox();
	private static JLabel lblAdult = new JLabel("Adult:");
	private static JLabel lblStudent = new JLabel("Student:");
	private JTextField textField;
	
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
				
				// Create instance of main window
				try {
					// When creating frame directlyl, not through login frame, set user id to 0 and disable admin privileges 
					mainWindow frame = new mainWindow(false, 0);
					// Position the frame in center of screen
					frame.setLocationRelativeTo(null);
					// Set frame visible
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
	public mainWindow(boolean if_admin, int userID) {
		setResizable(false);
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setTitle("IrishBusApp");
		setForeground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 624, 385);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//////////////////////////////////////////////
		///////SETUP TABBED PANE AND PANELS//////////
		/////////////////////////////////////////////
		UIManager.put("TabbedPane.selected", Color.getHSBColor(300,150,200));
		button.setBounds(470, 11, 89, 19);
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
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setForeground(Color.DARK_GRAY);
		tabbedPane.setBackground(Color.DARK_GRAY);
		tabbedPane.setBounds(10, 11, 549, 285);
		contentPane.add(tabbedPane);
		
		//get the bus stations from database and populate them into array lists
		populate();
		
		tabbedPane.addTab("Reservation", panel);
		tabbedPane.addTab("Administration", panel_1);
		tabbedPane.addTab("Bus timetable", panel_2);
		
		panel.setLayout(null);
		panel_1.setLayout(null);
		panel_2.setLayout(null);
		
		panel.setBackground(Color.DARK_GRAY);
		panel_1.setBackground(Color.DARK_GRAY);
		scrollPane.setEnabled(false);
		scrollPane.setBounds(10, 45, 524, 211);
		
		panel_1.add(scrollPane);
		
		// Add button listener
		JButton btnAddBus = new JButton("Add");
		btnAddBus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Creane a new instance of addBus frame, set its position to the center of screen and make it visible
				addBus frame = new addBus(if_admin);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		btnAddBus.setBounds(10, 11, 89, 23);
		panel_1.add(btnAddBus);
		
		// Delete bus listener
		JButton btnDeleteBus = new JButton("Delete");
		btnDeleteBus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Get the row number from table of selected bus
				int row = table.getSelectedRow();

				// If user didnt select any bus from the table
				if(row == -1){
					// Display message to user
					JOptionPane.showMessageDialog(null, "Select row from the table below first!");
				}// if row not selected
				// if user selected some entry from the table do the following
				else{
					// Ask user if he wants to delese this particular bus, as a precaution 
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the bus? \n ","Warning", dialogButton);
					// If user selected yes from the pop up do the following
					if(dialogResult == JOptionPane.YES_OPTION){

						// Connect to database to delete bus from bus_table
						try {
							Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbNameCon+"?useSSL=false", dbUsername, dbPassword); 
							Statement stmt = conn.createStatement();
							
							// statement to delete bus from bus_table
							String strSelect = "delete from bus_table where bus_id like " + table.getValueAt(table.getSelectedRow(), 0);
					 
					        stmt.executeUpdate(strSelect);
					        
					        refreshBuses();
					        refreshTimetable();
					        
					        // Close the connection
					        stmt.close();
							conn.close();
							
					      } catch(SQLException ex) {
					    	  ex.printStackTrace();
					      } // end of try/catch
					}// if user confirmed popup box
				}// else (if row has been selected)
			}//action event
		});
		btnDeleteBus.setBounds(109, 11, 89, 23);
		panel_1.add(btnDeleteBus);
		
		// Update button listener
		JButton btnUpdateBus = new JButton("Update");
		btnUpdateBus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Get the row number from table of selected bus
				int row = table.getSelectedRow();

				// If user didnt select any bus from the table
				if(row == -1){
					JOptionPane.showMessageDialog(null, "Select row from the table below first!");
				}// if row not selected
				else{
					// Ask user if he wants to update this particular bus, as a precaution 
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to update this bus? \n ","Warning", dialogButton);
					// If user selected yes
					if(dialogResult == JOptionPane.YES_OPTION){

						// Key is the bus id, which we pass as a parameter in overloaded constructor while creating updateBus instance
						int key = (int) table.getValueAt(table.getSelectedRow(), 0);
						// create instance of updateBus, and pass the key which is bus id to be updated
						updateBus frame = new updateBus(if_admin, key);
						frame.setVisible(true);
						frame.setLocationRelativeTo(null);
						frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						
					}// if confirmed
				} // if row has been selected
			}// action event
		});
		btnUpdateBus.setBounds(208, 11, 89, 23);
		panel_1.add(btnUpdateBus);
		
		// Refresh button listener
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Method to refresh the buses for the admin panel
				refreshBuses();
			}
		});
		btnRefresh.setBounds(445, 11, 89, 23);
		panel_1.add(btnRefresh);
		panel_2.setBackground(Color.DARK_GRAY);
		
		scrollPane_1.setBounds(10, 41, 524, 205);
		panel_2.add(scrollPane_1);
		logoTimetable.setIcon(new ImageIcon(mainWindow.class.getResource("/ie/gmit/g00309646/images/text1.png")));
		logoTimetable.setBounds(10, 0, 357, 43);
		
		panel_2.add(logoTimetable);
		
		// Refresh button listener
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Method to refresh the buses from the timetable (from database)
				refreshTimetable();
			}
		});
		btnNewButton_1.setOpaque(false);
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setIcon(new ImageIcon(mainWindow.class.getResource("/ie/gmit/g00309646/images/refresh.png")));
		btnNewButton_1.setBounds(430, 7, 104, 36);
		panel_2.add(btnNewButton_1);
		refreshTimetable();
		
		//if current user does not have administrator privileges disable the admin panel tab 
		if(!if_admin){
			//tabbedPane.setEnabledAt(2, false);
			//Remove admin tab if user isn't authenticated as admin
			tabbedPane.removeTabAt(1);
		}
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 27, 105, 19);
		panel.add(lblNewLabel);
		lblDestination.setFont(new Font("Arial", Font.PLAIN, 13));
		lblDestination.setHorizontalAlignment(SwingConstants.RIGHT);
	
		lblDestination.setForeground(Color.WHITE);
		lblDestination.setBounds(274, 28, 100, 17);
		panel.add(lblDestination);
		
		comboBox.setBounds(114, 25, 150, 20);
		panel.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(sourceStations.toArray()));
		
		comboBox_1.setBounds(384, 26, 150, 20);
		panel.add(comboBox_1);
		comboBox_1.setModel(new DefaultComboBoxModel(destinationStations.toArray()));
		btnSearch.setBackground(Color.WHITE);
		
		btnSearch.setBounds(114, 61, 420, 23);
		panel.add(btnSearch);
		lblAvailableBus.setFont(new Font("Arial", Font.PLAIN, 13));
		lblAvailableBus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAvailableBus.setForeground(Color.WHITE);
		
		lblAvailableBus.setBounds(10, 96, 105, 19);
		panel.add(lblAvailableBus);
		
		comboBox_2.setBounds(114, 95, 420, 20);
		panel.add(comboBox_2);
		
		// Select button listener
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// enable the panel method if user selects/enters all required fields
				enablePanel();
			}
		});

		btnSelect.setBounds(114, 123, 420, 23);
		panel.add(btnSelect);
		btnSelect.setEnabled(false);
		
		// Refresh the buses from database on frame load
		refreshBuses();
		
		//////////////////////////////////////////////
		///////SEARCH BUTTON ACTION LISTENER/////////
		/////////////////////////////////////////////
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				availableBuses.clear();
				comboBox_2.setModel(new DefaultComboBoxModel(availableBuses.toArray()));
				btnSelect.setEnabled(false);
				disablePanel();
				
				// If the source station is not the same as destination station then proceed
				if(!comboBox.getSelectedItem().equals(comboBox_1.getSelectedItem())){
					System.out.println(comboBox.getSelectedItem() + "  " + comboBox_1.getSelectedItem());
					
					// Connect to database to look for connections between source and destination stations
					try {
						Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbNameCon+"?useSSL=false", dbUsername, dbPassword); 
						Statement stmt = conn.createStatement();
						
						//SQL query string
						String strSelect = "select * from bus_table where depart_from LIKE '" + comboBox.getSelectedItem() + "' and going_to LIKE '" + comboBox_1.getSelectedItem() + "'";
				 
						//Execure the query
				        ResultSet rset = stmt.executeQuery(strSelect);
				 
			        	while(rset.next()) {
				        	
				        	int tempID = rset.getInt("bus_id");
				        	String tempBusNo = rset.getString("bus_number");
				        	String tempDepartFrom = rset.getString("depart_from");
				        	String tempGoingTo = rset.getString("going_to");
				        	String tempBusTime = rset.getString("bus_time");
				        	
				        	String availableBus = tempID + " : " + tempBusNo + " : " + tempDepartFrom + " - " + tempGoingTo + " @ " + tempBusTime;
				        	
				        	// Add each bus to list of available buses
				        	availableBuses.add(availableBus);
				        	// Enable button select if buses have been found
				        	btnSelect.setEnabled(true);

				        	// Populate the combobox with available buses array 
				        	comboBox_2.setModel(new DefaultComboBoxModel(availableBuses.toArray()));
				        }  
			        	
			        	// Close the connection
			        	rset.close();
				        stmt.close();
						conn.close();

				      } catch(SQLException ex) {
				    	  ex.printStackTrace();
				      }
					
				}
				else{
					System.out.println("Wrong! Try again..");
					availableBuses.clear();
					comboBox_2.setModel(new DefaultComboBoxModel(availableBuses.toArray()));
					btnSelect.setEnabled(false);
					disablePanel();
				}	
			}
		});
		
		// Using the jDatePicker API creating model and date panel
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		panel_4.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		
		panel_4.setBackground(Color.DARK_GRAY);
		panel_4.setForeground(Color.WHITE);
		panel_4.setBounds(10, 157, 524, 80);
		panel.add(panel_4);
		panel_4.setLayout(null);
		
		comboBox_student.setBounds(222, 47, 57, 20);
		panel_4.add(comboBox_student);
		lblStudent.setFont(new Font("Arial", Font.PLAIN, 13));
		
		lblStudent.setBounds(158, 49, 54, 14);
		panel_4.add(lblStudent);
		lblStudent.setForeground(Color.WHITE);
	
		
		comboBox_adult.setBounds(85, 47, 57, 20);
		panel_4.add(comboBox_adult);
		// When user changes the selected item on adulyComboBox, calculate how much the ticket will cost automatically
		comboBox_adult.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        double total = 0.00;
		        
		        total = (Integer) comboBox_adult.getSelectedItem() * 12.45;

		        adultPrice.setText(""+round(total, 2));
		        
		        lblStudent.setForeground(Color.WHITE);
				lblAdult.setForeground(Color.WHITE);
		        
		        calculateTotal();
		    }
		});
		
		// When user changes the selected item on studentComboBox, calculate how much the ticket will cost automatically
		comboBox_student.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        double total = 0.00;
		        
		        total = (Integer) comboBox_student.getSelectedItem() * 9.25;

		        studentPrice.setText(""+round(total, 2));
		        
		        lblStudent.setForeground(Color.WHITE);
				lblAdult.setForeground(Color.WHITE);
		        
		        calculateTotal();
		    }
		});
		lblAdult.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAdult.setFont(new Font("Arial", Font.PLAIN, 13));
		
		lblAdult.setBounds(10, 49, 65, 14);
		panel_4.add(lblAdult);
		lblAdult.setForeground(Color.WHITE);
		lblSelectDate.setFont(new Font("Arial", Font.PLAIN, 13));
		lblSelectDate.setBounds(10, 15, 97, 14);
		panel_4.add(lblSelectDate);
		lblSelectDate.setForeground(Color.WHITE);
		datePicker = new JDatePickerImpl(datePanel);
		datePicker.setBounds(87, 9, 192, 25);
		panel_4.add(datePicker);
		datePicker.getJFormattedTextField().setBounds(0, 0, 229, 23);

		// Populate adult and student dropboxes with passengers quantity to be selected by users
		for(int i=0;i<=15;i++){
			comboBox_adult.addItem(new Integer(i));
			comboBox_student.addItem(new Integer(i));
		}
		comboBox_student.setSelectedItem(0);
		comboBox_adult.setSelectedItem(0);
		model.setDate(2017, 3, 24);
		model.setSelected(true);
		
		// Buy tickets listener
		JButton btnNewButton = new JButton("Buy tickets");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// If at least one passenger has been selected either adult or student proceed, if not display error
				if(!comboBox_student.getSelectedItem().equals(0) || !comboBox_adult.getSelectedItem().equals(0)){
					String[] selected = comboBox_2.getSelectedItem().toString().split(" : "); // <-- selected[0] contains the bus id that we want to book the ticket for..
					
					int bus_id = 0;
					String bus_number = null;
					/*String depart_from = null;
					String going_to = null;
					String bus_time = null;*/
					
					// Connect to database to get bus information
					try {
						Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbNameCon+"?useSSL=false", dbUsername, dbPassword); 
						Statement stmt = conn.createStatement();
						
						String strSelect = "select * from bus_table where bus_id="+selected[0];
				 
				        ResultSet rset = stmt.executeQuery(strSelect);
				 
			        	while(rset.next()) {
			        		bus_id = rset.getInt("bus_id");
			        		bus_number = rset.getString("bus_number");
			        		/*depart_from = rset.getString("depart_from");
			        		going_to = rset.getString("going_to");
			        		bus_time = rset.getString("bus_time");*/
				        }  
			        	
			        	/////////////////////////////////
			        	////checking all information/////
			        	/////////////////////////////////
			        	/*System.out.println("Bus ID - "+bus_id);
			        	System.out.println("Bus number - "+bus_number);
			        	System.out.println("Joutney date - "+model.getDay()+"/"+model.getMonth()+"/"+model.getYear());
			        	System.out.println("Adult quantity - "+comboBox_adult.getSelectedIndex()); 
			        	System.out.println("Student quantity - "+comboBox_student.getSelectedItem());
			        	System.out.println("User ID - "+userID);
			        	System.out.println("Total price - "+"€");*/
			        	
			        	int dbBusID = bus_id;
			        	String dbBusNumber = bus_number;
			        	String dbJourneyDate = model.getDay()+"/"+model.getMonth()+"/"+model.getYear();
			        	int dbAdult = (int) comboBox_adult.getSelectedItem();
			        	int dbStudent = (int) comboBox_student.getSelectedItem();
			        	int dbUserID = userID;
			        	double dbPrice = Double.parseDouble(totalPriceLbl.getText());
						//Generating random number using ThreadLocalRandom.current() method
						//This approach has the advantage of not needing to explicitly initialize a java.util.Random instance
						//which can be a source of confusion and error if used inappropriately
			        	int tempTicketID = ThreadLocalRandom.current().nextInt(44444444, 999999999 + 1);

			        	//System.out.println(dbBusID+"\n"+dbBusNumber+"\n"+dbJourneyDate+"\n"+dbAdult+"\n"+dbStudent+"\n"+dbUserID+"\n"+dbPrice);
			        	// Connect to database to insert new ticket into tickets table using all the details provided by user and aquired from another queries
			        	try {
							Connection conn2 = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbNameCon+"?useSSL=false", dbUsername, dbPassword); 
							Statement stmt2 = conn2.createStatement();
							
							String strSelect2 = "insert into ticket (bus_id, bus_number, journey_date, adult_quantity, student_quantity, user_id, totalPrice, ticket_id) "
									+ "values ('"+dbBusID+"', '"+dbBusNumber+"', '"+dbJourneyDate+"', '"+dbAdult+"', '"
									+dbStudent+"', '"+dbUserID+"', '"+dbPrice+"', '"+tempTicketID+"')";

					        stmt2.executeUpdate(strSelect2);
					        
					        // Using Clipboard class to manipulate users clipboard, so we can automatically save the ticket id for users clipboard
					        StringSelection selection = new StringSelection(String.valueOf(tempTicketID));
					        // Get users clipboard
					        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					        // Set users clipboard to out selection (which is the ticket id)
					        clipboard.setContents(selection, selection);
					        
					        // Message for user to show ticket id and inform about clipboard
					        JOptionPane.showMessageDialog(null, "Thanks for buying the ticket \nMake sure to remember or save your ticket ID \nTicket ID - "
					        										+tempTicketID+"\nIt has been automatically copied to your clipboard (ctrl+v)");
					        // Creating new instance of display ticket frame, passing ticket it as constructor param
					        displayTicket frame = new displayTicket(tempTicketID);
					        frame.setVisible(true);
					        frame.setLocationRelativeTo(null);
					        
					        // Closing connection
					        stmt2.close();
							conn2.close();
					        
					      } catch(SQLException ex) {
					    	  ex.printStackTrace();
					      } // end of try/catch
			        	
			        	// Closing connection
			        	rset.close();
				        stmt.close();
						conn.close();
			        	
				      } catch(SQLException ex) {
				    	  ex.printStackTrace();
				      }
				}
				else{
					// If user didn't select any passengers, highlight labels 
					lblStudent.setForeground(Color.cyan);
					lblAdult.setForeground(Color.cyan);
					// If user didn't select any passengers display message
					JOptionPane.showMessageDialog(null, "Please select passengers amount");
				}
			}
		});
		btnNewButton.setBounds(409, 11, 105, 23);
		panel_4.add(btnNewButton);
		// Clear button listener
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Set adult and student quantity to 0
				comboBox_adult.setSelectedItem(0);
				comboBox_student.setSelectedItem(0);
				// Set date on the jDatePicker to 24/03/2017
				model.setDate(2017, 3, 24);
			}
		});
		btnClear.setBounds(409, 44, 105, 23);
		
		panel_4.add(btnClear);
		lblStudent_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStudent_1.setForeground(Color.WHITE);
		lblStudent_1.setBounds(289, 15, 56, 14);
		
		panel_4.add(lblStudent_1);
		lblAdult_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAdult_1.setForeground(Color.WHITE);
		lblAdult_1.setBounds(289, 31, 56, 14);
		
		panel_4.add(lblAdult_1);
		studentPrice.setForeground(Color.WHITE);
		studentPrice.setBounds(353, 15, 46, 14);
		
		panel_4.add(studentPrice);
		adultPrice.setForeground(Color.WHITE);
		adultPrice.setBounds(353, 31, 46, 14);
		
		panel_4.add(adultPrice);
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setForeground(Color.WHITE);
		lblTotal.setBounds(289, 48, 56, 14);
		
		panel_4.add(lblTotal);
		totalPriceLbl.setForeground(Color.WHITE);
		totalPriceLbl.setBounds(353, 48, 46, 14);
		
		panel_4.add(totalPriceLbl);
		tabbedPane.addTab("Tickets", panel_3);
		panel_3.setLayout(null);
		
		panel_3.setBackground(Color.DARK_GRAY);
		
		textField = new JTextField();
		textField.setForeground(Color.DARK_GRAY);
		textField.setBackground(Color.WHITE);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Arial", Font.PLAIN, 25));
		textField.setBounds(115, 116, 226, 35);
		panel_3.add(textField);
		textField.setColumns(10);
		
		// Search button listener
		JButton btnNewButton_2 = new JButton("Search");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// Method variable declaration
				int ticketID;
				
				// Set the ticket it to whatever user typed in the textField
				ticketID = Integer.parseInt(textField.getText());
				
				// Connect to database to look for the ticket
				try {
					Connection conn3 = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbNameCon+"?useSSL=false", dbUsername, dbPassword); 
					Statement stmt3 = conn3.createStatement();
					
					// String query
					String strSelect3 = "select * from ticket where ticket_id="+ticketID;
			 
					// Execute statement
			        ResultSet rset3 = stmt3.executeQuery(strSelect3);
			 
			        // If the ticket has been found
		        	if(rset3.next()){
		        		// Create new instance of display ticket frame passing ticket it as constructor param
		        		displayTicket frame = new displayTicket(ticketID);
		        		frame.setVisible(true);
		        		frame.setLocationRelativeTo(null);
		        	}
		        	else{
		        		// If ticket has not been found display error message to user
		        		JOptionPane.showMessageDialog(null, "Sorry, ticket with ID "+ticketID+" doesn't exist.");
		        	}
		        	
		        	// Close connection
		        	rset3.close();
			        stmt3.close();
					conn3.close();
					
			      } catch(SQLException ex) {
			    	  ex.printStackTrace();
			      }
				
			}
		});
		btnNewButton_2.setBounds(351, 122, 89, 23);
		panel_3.add(btnNewButton_2);
		
		JLabel lblTicketId = new JLabel("Ticket ID");
		lblTicketId.setForeground(Color.LIGHT_GRAY);
		lblTicketId.setFont(new Font("Arial", Font.ITALIC, 24));
		lblTicketId.setHorizontalAlignment(SwingConstants.CENTER);
		lblTicketId.setBounds(115, 85, 226, 30);
		panel_3.add(lblTicketId);
		
		// disable the panel
		disablePanel();
    }
	
	// Method to populate the jTable with the database details from result set
	public static DefaultTableModel buildTableModel(ResultSet rs)
	        throws SQLException {

		// Get the metadata from the result set for column titles
	    ResultSetMetaData metaData = rs.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }
	    // Return built data model with column names and data from databases result set
	    return new DefaultTableModel(data, columnNames);
	}
	
	//////////////////////////////////////////////
	///////METHOD TO REFRESH THE BUSES TABLE//////
	/////////////////////////////////////////////
	public static void refreshBuses(){
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbNameCon+"?useSSL=false", dbUsername, dbPassword); 
			Statement stmt = conn.createStatement();
			
			String strSelect = "select * from bus_table";
	 
	        ResultSet rset = stmt.executeQuery(strSelect);
	 
        	table = new JTable(buildTableModel(rset));
	        scrollPane.setViewportView(table);
	        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        table.setForeground(Color.WHITE);
			table.setBorder(null);
			table.setBackground(Color.GRAY);
			table.setBounds(10, 11, 192, 63);
			table.setGridColor(Color.LIGHT_GRAY);

			rset.close();
	        stmt.close();
			conn.close();
			
	      } catch(SQLException ex) {
	    	  ex.printStackTrace();
	      }
	}
	
	//////////////////////////////////////////////
	///////METHOD TO REFRESH THE timetable////////
	/////////////////////////////////////////////
	public static void refreshTimetable(){
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbNameCon+"?useSSL=false", dbUsername, dbPassword); 
			Statement stmt = conn.createStatement();
			
			String strSelect = "select * from bus_table";
	 
	        ResultSet rset = stmt.executeQuery(strSelect);
	 
        	table_1 = new JTable(buildTableModel(rset));
	        scrollPane_1.setViewportView(table_1);
	        table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        table_1.setForeground(Color.WHITE);
			table_1.setBorder(null);
			table_1.setBackground(Color.GRAY);
			table_1.setBounds(10, 11, 192, 63);
			table_1.setGridColor(Color.LIGHT_GRAY);

			rset.close();
	        stmt.close();
			conn.close();
			
	      } catch(SQLException ex) {
	    	  ex.printStackTrace();
	      }
	}
	
	// Method to dispose the frame
	public void finish(){
		this.setVisible(false);
		this.dispose();
	}
	
	// Method to populate the combo boxes for source and destination stations from database
	public static void populate(){
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbNameCon+"?useSSL=false", dbUsername, dbPassword); 
			Statement stmt = conn.createStatement();
			
			String strSelect = "select depart_from, going_to from bus_table";
	 
	        ResultSet rset = stmt.executeQuery(strSelect);
	 
	        // <First make sure the combo boxes are empty
	        sourceStations.clear();
	        destinationStations.clear();
	        
	        // Get the data from database
	        while(rset.next()) {
	        	// Make sure not to have duplicates in source stations 
	        	if(!sourceStations.contains(rset.getString("depart_from"))){
	        		sourceStations.add(rset.getString("depart_from"));
	        	}
	        	// Make sure not to have duplicates in destination stations 
	        	if(!destinationStations.contains(rset.getString("going_to"))){
	        		destinationStations.add(rset.getString("going_to"));
	        	}
	        }
	        
	        // Populate the combo boxes with sourceStations and destinationStations arrays
	        comboBox.setModel(new DefaultComboBoxModel(sourceStations.toArray()));
			comboBox_1.setModel(new DefaultComboBoxModel(destinationStations.toArray()));
			
			// Close the connection
			rset.close();
	        stmt.close();
			conn.close();
			
	      } catch(SQLException ex) {
	    	  ex.printStackTrace();
	      }
	}
	
	// Disabling the lower panel on Reservation tab 
	public void disablePanel(){
		// Really handy function to iterate trough panels components and set them all disabled
		for (Component cp : panel_4.getComponents() ){
	        cp.setEnabled(false);
		}
		
		adultPrice.setText("0.0");
		studentPrice.setText("0.0");
		totalPriceLbl.setText("0.0");
		comboBox_student.setSelectedItem(0);
		comboBox_adult.setSelectedItem(0);
		datePicker.getJFormattedTextField().setEnabled(false);
	}
	
	// Enabling the lower panel on Reservation tab
	public void enablePanel(){
		// Really handy function to iterate trough panels components and set them all enabled
		for (Component cp : panel_4.getComponents() ){
	        cp.setEnabled(true);
		}
		adultPrice.setText("0.0");
		studentPrice.setText("0.0");
		totalPriceLbl.setText("0.0");
		comboBox_student.setSelectedItem(0);
		comboBox_adult.setSelectedItem(0);
		datePicker.getJFormattedTextField().setEnabled(true);
	}

	// method to round any double value to whatever decimal places
	public static double round(double value, int places) {
		// If places are less than 0 throw exception
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    // Return rounded value 
	    return (double) tmp / factor;
	}

	// Method to calculate total price of ticket based on passengers quantity
	public void calculateTotal(){
		// Method variables
        double totalPrice = 0.00;
        double student = 0.00;
        double adult = 0.00;
        
        // Parse to double from String from labels 
        student = Double.parseDouble(studentPrice.getText());
        adult = Double.parseDouble(adultPrice.getText());
        
        // Sum up the total price
        totalPrice = student + adult;
        
        // display total price for user
        totalPriceLbl.setText(""+totalPrice);
	}
}//end of class