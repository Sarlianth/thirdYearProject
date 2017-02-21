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

@SuppressWarnings({ "unchecked", "rawtypes" })
public class mainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static ArrayList<String> sourceStations = new ArrayList<String>();
	private static ArrayList<String> destinationStations = new ArrayList<String>();
	private static JComboBox comboBox = new JComboBox();
	private static JComboBox comboBox_1 = new JComboBox();
	private static JLabel lblAvailableBus = new JLabel("Available buses: ");
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
	private JLabel lblNewLabel = new JLabel("Source station:");
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
				
				try {
					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
					UIManager.put("RootPane.setupButtonVisible", false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					mainWindow frame = new mainWindow(false, 0);
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
		
		JButton btnAddBus = new JButton("Add");
		btnAddBus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addBus frame = new addBus(if_admin);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		btnAddBus.setBounds(10, 11, 89, 23);
		panel_1.add(btnAddBus);
		
		JButton btnDeleteBus = new JButton("Delete");
		btnDeleteBus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int row = table.getSelectedRow();

				if(row == -1){
					JOptionPane.showMessageDialog(null, "Select row from the table below first!");
				}// if row not selected
				else{
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the bus? \n ","Warning", dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){

						try {
							Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbNameCon+"?useSSL=false", dbUsername, dbPassword); 
							Statement stmt = conn.createStatement();
							
							String strSelect = "delete from bus_table where bus_id like " + table.getValueAt(table.getSelectedRow(), 0);
					 
					        stmt.executeUpdate(strSelect);
					        
					        refreshBuses();
					        refreshTimetable();
					        
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
		
		JButton btnUpdateBus = new JButton("Update");
		btnUpdateBus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int row = table.getSelectedRow();

				if(row == -1){
					JOptionPane.showMessageDialog(null, "Select row from the table below first!");
				}// if row not selected
				else{
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to update this bus? \n ","Warning", dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){

						int key = (int) table.getValueAt(table.getSelectedRow(), 0);
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
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
		lblNewLabel.setBounds(7, 26, 105, 19);
		panel.add(lblNewLabel);
		lblDestination.setFont(new Font("Arial", Font.PLAIN, 13));
		lblDestination.setHorizontalAlignment(SwingConstants.RIGHT);
	
		lblDestination.setForeground(Color.WHITE);
		lblDestination.setBounds(238, 28, 71, 14);
		panel.add(lblDestination);
		
		comboBox.setBounds(114, 25, 118, 20);
		panel.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(sourceStations.toArray()));
		
		comboBox_1.setBounds(313, 25, 118, 20);
		panel.add(comboBox_1);
		comboBox_1.setModel(new DefaultComboBoxModel(destinationStations.toArray()));
		btnSearch.setBackground(Color.WHITE);
		
		btnSearch.setBounds(114, 61, 317, 23);
		panel.add(btnSearch);
		lblAvailableBus.setFont(new Font("Arial", Font.PLAIN, 13));
		lblAvailableBus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAvailableBus.setForeground(Color.WHITE);
		
		lblAvailableBus.setBounds(10, 96, 105, 19);
		panel.add(lblAvailableBus);
		
		comboBox_2.setBounds(114, 95, 317, 20);
		panel.add(comboBox_2);
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enablePanel();
			}
		});

		btnSelect.setBounds(114, 123, 317, 23);
		panel.add(btnSelect);
		btnSelect.setEnabled(false);
		
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
				
				if(!comboBox.getSelectedItem().equals(comboBox_1.getSelectedItem())){
					System.out.println(comboBox.getSelectedItem() + "  " + comboBox_1.getSelectedItem());
					
					
					try {
						Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbNameCon+"?useSSL=false", dbUsername, dbPassword); 
						Statement stmt = conn.createStatement();
						
						String strSelect = "select * from bus_table where depart_from LIKE '" + comboBox.getSelectedItem() + "' and going_to LIKE '" + comboBox_1.getSelectedItem() + "'";
				 
				        ResultSet rset = stmt.executeQuery(strSelect);
				 
			        	while(rset.next()) {
				        	
				        	int tempID = rset.getInt("bus_id");
				        	String tempBusNo = rset.getString("bus_number");
				        	String tempDepartFrom = rset.getString("depart_from");
				        	String tempGoingTo = rset.getString("going_to");
				        	String tempBusTime = rset.getString("bus_time");
				        	
				        	String availableBus = tempID + " : " + tempBusNo + " : " + tempDepartFrom + " - " + tempGoingTo + " @ " + tempBusTime;
				        	
				        	availableBuses.add(availableBus);
				        	btnSelect.setEnabled(true);

				        	comboBox_2.setModel(new DefaultComboBoxModel(availableBuses.toArray()));
				        }  
			        	
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
		
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		panel_4.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		
		panel_4.setBackground(Color.DARK_GRAY);
		panel_4.setForeground(Color.WHITE);
		panel_4.setBounds(10, 157, 524, 80);
		panel.add(panel_4);
		panel_4.setLayout(null);
		
		comboBox_student.setBounds(222, 47, 45, 20);
		panel_4.add(comboBox_student);
		lblStudent.setFont(new Font("Arial", Font.PLAIN, 13));
		
		lblStudent.setBounds(163, 49, 54, 14);
		panel_4.add(lblStudent);
		lblStudent.setForeground(Color.WHITE);
	
		
		comboBox_adult.setBounds(97, 47, 45, 20);
		panel_4.add(comboBox_adult);
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
		lblAdult.setFont(new Font("Arial", Font.PLAIN, 13));
		
		lblAdult.setBounds(54, 49, 45, 14);
		panel_4.add(lblAdult);
		lblAdult.setForeground(Color.WHITE);
		lblSelectDate.setFont(new Font("Arial", Font.PLAIN, 13));
		lblSelectDate.setBounds(10, 15, 91, 14);
		panel_4.add(lblSelectDate);
		lblSelectDate.setForeground(Color.WHITE);
		datePicker = new JDatePickerImpl(datePanel);
		datePicker.setBounds(87, 9, 192, 25);
		panel_4.add(datePicker);
		datePicker.getJFormattedTextField().setBounds(0, 0, 229, 23);

		for(int i=0;i<=15;i++){
			comboBox_adult.addItem(new Integer(i));
			comboBox_student.addItem(new Integer(i));
		}
		comboBox_student.setSelectedItem(0);
		comboBox_adult.setSelectedItem(0);
		model.setDate(2017, 3, 24);
		model.setSelected(true);
		
		JButton btnNewButton = new JButton("Buy tickets");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(!comboBox_student.getSelectedItem().equals(0) || !comboBox_adult.getSelectedItem().equals(0)){
					String[] selected = comboBox_2.getSelectedItem().toString().split(" : "); // <-- selected[0] contains the bus id that we want to book the ticket for..
					
					int bus_id = 0;
					String bus_number = null;
					/*String depart_from = null;
					String going_to = null;
					String bus_time = null;*/
					
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
			        	try {
							Connection conn2 = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbNameCon+"?useSSL=false", dbUsername, dbPassword); 
							Statement stmt2 = conn2.createStatement();
							
							String strSelect2 = "insert into ticket (bus_id, bus_number, journey_date, adult_quantity, student_quantity, user_id, totalPrice, ticket_id) "
									+ "values ('"+dbBusID+"', '"+dbBusNumber+"', '"+dbJourneyDate+"', '"+dbAdult+"', '"
									+dbStudent+"', '"+dbUserID+"', '"+dbPrice+"', '"+tempTicketID+"')";

					        stmt2.executeUpdate(strSelect2);
					        
					        StringSelection selection = new StringSelection(String.valueOf(tempTicketID));
					        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					        clipboard.setContents(selection, selection);
					        
					        JOptionPane.showMessageDialog(null, "Thanks for buying the ticket \nMake sure to remember or save your ticket ID \nTicket ID - "
					        										+tempTicketID+"\nIt has been automatically copied to your clipboard (ctrl+v)");
					        displayTicket frame = new displayTicket(tempTicketID);
					        frame.setVisible(true);
					        frame.setLocationRelativeTo(null);

					        stmt2.close();
							conn2.close();
					        
					      } catch(SQLException ex) {
					    	  ex.printStackTrace();
					      } // end of try/catch
			        	

			        	rset.close();
				        stmt.close();
						conn.close();
			        	
				      } catch(SQLException ex) {
				    	  ex.printStackTrace();
				      }
				}
				else{
					lblStudent.setForeground(Color.cyan);
					lblAdult.setForeground(Color.cyan);
					JOptionPane.showMessageDialog(null, "Please select passengers amount");
				}
			}
		});
		btnNewButton.setBounds(409, 11, 105, 23);
		panel_4.add(btnNewButton);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				comboBox_adult.setSelectedItem(0);
				comboBox_student.setSelectedItem(0);
				model.setDate(2017, 3, 24);
			}
		});
		btnClear.setBounds(409, 44, 105, 23);
		
		panel_4.add(btnClear);
		lblStudent_1.setForeground(Color.WHITE);
		lblStudent_1.setBounds(294, 15, 59, 14);
		
		panel_4.add(lblStudent_1);
		lblAdult_1.setForeground(Color.WHITE);
		lblAdult_1.setBounds(309, 31, 44, 14);
		
		panel_4.add(lblAdult_1);
		studentPrice.setForeground(Color.WHITE);
		studentPrice.setBounds(353, 15, 46, 14);
		
		panel_4.add(studentPrice);
		adultPrice.setForeground(Color.WHITE);
		adultPrice.setBounds(353, 31, 46, 14);
		
		panel_4.add(adultPrice);
		lblTotal.setForeground(Color.WHITE);
		lblTotal.setBounds(310, 48, 44, 14);
		
		panel_4.add(lblTotal);
		totalPriceLbl.setForeground(Color.WHITE);
		totalPriceLbl.setBounds(353, 48, 46, 14);
		
		panel_4.add(totalPriceLbl);
		button.setBounds(445, 26, 89, 120);
		panel.add(button);
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
		
		JButton btnNewButton_2 = new JButton("Search");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int ticketID;
				
				ticketID = Integer.parseInt(textField.getText());
				
				try {
					Connection conn3 = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbNameCon+"?useSSL=false", dbUsername, dbPassword); 
					Statement stmt3 = conn3.createStatement();
					
					String strSelect3 = "select * from ticket where ticket_id="+ticketID;
			 
			        ResultSet rset3 = stmt3.executeQuery(strSelect3);
			 
		        	if(rset3.next()){
		        		displayTicket frame = new displayTicket(ticketID);
		        		frame.setVisible(true);
		        		frame.setLocationRelativeTo(null);
		        	}
		        	else{
		        		JOptionPane.showMessageDialog(null, "Sorry, ticket with ID "+ticketID+" doesn't exist.");
		        	}
		        	
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
		
		disablePanel();
    }
	
	public static DefaultTableModel buildTableModel(ResultSet rs)
	        throws SQLException {

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
	
	public void finish(){
		this.setVisible(false);
		this.dispose();
	}
	
	public static void populate(){
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbNameCon+"?useSSL=false", dbUsername, dbPassword); 
			Statement stmt = conn.createStatement();
			
			String strSelect = "select depart_from, going_to from bus_table";
	 
	        ResultSet rset = stmt.executeQuery(strSelect);
	 
	        sourceStations.clear();
	        destinationStations.clear();
	        
	        while(rset.next()) {
	        	if(!sourceStations.contains(rset.getString("depart_from"))){
	        		sourceStations.add(rset.getString("depart_from"));
	        	}
	        	if(!destinationStations.contains(rset.getString("going_to"))){
	        		destinationStations.add(rset.getString("going_to"));
	        	}
	        }
	        
	        comboBox.setModel(new DefaultComboBoxModel(sourceStations.toArray()));
			comboBox_1.setModel(new DefaultComboBoxModel(destinationStations.toArray()));
			
			rset.close();
	        stmt.close();
			conn.close();
			
	      } catch(SQLException ex) {
	    	  ex.printStackTrace();
	      }
	}
	
	public void disablePanel(){
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
	
	public void enablePanel(){
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

	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

	public void calculateTotal(){
        double totalPrice = 0.00;
        double student = 0.00;
        double adult = 0.00;
        
        student = Double.parseDouble(studentPrice.getText());
        adult = Double.parseDouble(adultPrice.getText());
        
        totalPrice = student + adult;
        
        totalPriceLbl.setText(""+totalPrice);
	}
}